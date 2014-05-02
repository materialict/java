import java.util.ArrayList;
/* java persistance */
import java.io.Serializable;
/* java file */
import java.io.File;
/* error handling */
import java.io.IOException;


public class uLines extends generalFunctions implements Serializable{


	/**
	Variable that shows is a piece of code is original (true) or a copy, for instance in
	the conceptual entity in a component, of inherited from somewhere else. In which cas the value
	is false
	*/
	private boolean isOriginal = true;
	/** Variable that indicates that a set of lines was tested and found to have an identical set
	of code somewhere else (true). If the variable is false it need not be tested again. The original
	set will still be containing the value true */
	private boolean isDuplicate = false;

	private String triggerName = new String();
	private ArrayList<uCodeLine> codeLines = new ArrayList<uCodeLine>();
	private ArrayList<uOperation> myOperations = new ArrayList<uOperation>();
	private String libraryName = null;

	String actualCodeText = new String();

	uLines(String triggerName, ArrayList<uCodeLine> myLines){
		this.triggerName = triggerName;
		this.codeLines = myLines;

		if (this.actualCodeText.isEmpty()){
			/* create a string of processed lines */
			this.actualCodeText = this.getActualCodeString(0);
		}
	}

	uLines(String myCode){

		/* replace the XML specific tags like &lt;*/
		myCode = cleanupText(myCode);

		/* cut up the string and put it in an arraylist */
		this.createArrayListFromString(myCode);

		/* do some additional processing */
		this.getIncludeProcs();

		/* process the set of lines  */
		this.executePeerReviewMultipleLines();

		/* create a string of processed lines */
		this.getActualCodeString(0);

		/* look for entries and operations */
		this.getSubProcedures();
	}


	public void getIncludeProcs(){
		/* this function steps through the individual lines looking for the #include
		statement. If found it will add the include lines to the proc */

		for (int i =0; i< codeLines.size(); i++){
			if (codeLines.get(i).isInclude()){

				/*** get the include name ***/
				String myLibrary = codeLines.get(i).getIncludelibrary(this.getLibraryname());
				String myProc = codeLines.get(i).getIncludeproc();

				/*** retrieve the include proc from the folder ***/
				File dir = new File(uSource.myPath + "I/");
				fileExtensionFilterStarts filter = new fileExtensionFilterStarts(myLibrary);
				String[] myFiles = dir.list(filter);

				if (myFiles != null) {
					for (int j=0; j< myFiles.length; j++) {

						uSource mySource = null;
						mySource = (uSource)loadObject(uSource.myPath + "I/", myFiles[j]);

						/* if the componentname is the same add the variable */
						if(myLibrary.equals(mySource.getLibraryname())
							&& myProc.equals(mySource.getSourcename())){
							System.out.println("uLines: Found include procedure " + myLibrary + ":" + myProc);
							break;
						}
					}//endfor
				} else {
					System.out.println("No matching files found in " + uSource.myPath + "I/");
				}//endif


				/* add a codeline commenting the start of the include section */

				/* add the include lines */

				/* add a codeline commenting the end of the include section */

			}//endif
		}//endfor
	}

	/** look if the snippet has a lower level command */
	public static boolean snippetHasLevelCommand(ArrayList<uCodeLine> mainSnippet, ArrayList<uCodeLine> snippet, int counter){

		/* first determine where the line number is */


		/* now step through the remaining lines in the snippet looking for another snippet */
		for (int i = counter; i < snippet.size(); i++){
			if (snippet.get(i).isLevelCommand()){
				return true;
			}
		}
		return false;
	}

	public void createArrayListFromString(String myCode){
		/**
		 * This function creates an array list of uCodeLine objects from a single string.
		 * The lines in the string are separated by a <BR>. This tag is manually added. In the XML
		 * also a <CR/> tag may be present.
		 */

		 /* init */
		int counter = 0;
		int atIndentation = 0;
		String myValue;

		//System.out.println("*****************");
		//System.out.println("Processing <CR/><BR>");
		//System.out.println("*****************");

		// step 0: search for values of <CR/><BR> tags and remove these
		String searchStringAll = new String("<CR/><BR>");
		int myAllPointer = myCode.indexOf(searchStringAll, 0);
		if (myAllPointer >=0){
			//System.out.println("Removing tags <CR/><BR>");
			myCode = myCode.replaceAll(searchStringAll, "");

			// look for more CR tags
			if (myCode.indexOf("<CR/>", 0) >=0){
				System.out.println("Still <CR/> tags present");
			}//endif
		}//endif

		//System.out.println("*****************");
		//System.out.println("Processing <BR>");
		//System.out.println("*****************");

		/* Step 1: create an array of uCodeLine objects from the string of code */
		String searchStringBreak = new String("<BR>");
		while (!myCode.isEmpty()){
			int myBreakPointer = myCode.indexOf(searchStringBreak, 0);

			//remove the breaks
			if (myBreakPointer >= 0){
				myValue = new String(myCode.substring(0, myBreakPointer).toUpperCase());
				myCode =  myCode.substring(myBreakPointer + 4, myCode.length());
			} else {
				/* the last line */
				myValue = new String(myCode.toUpperCase());
				myCode = "";
			}//endif

			/* create the codeline */
			uCodeLine myCodeLine = new uCodeLine((counter + 1), myValue);

			/* Fill the arrays needed to do stuff */
			this.codeLines.add(myCodeLine);

			counter++;
		}//endwhile

		/* Step 2: step through the codelines looking for incomplete lines and complete them */
		for (int i = 0; i < this.codeLines.size(); i++){
			/* if the line is incomplete */
			if (this.codeLines.get(i).isIncomplete()){
				/* add the processed line to the next line */

				String myLine = new String(this.codeLines.get(i).getProcessedLine());

				myLine = myLine.substring(0, myLine.indexOf("%\\", 0));

				/* if we are at the last line and we still found a line terminator than raise an error */
				if ((i+1) >= this.codeLines.size()){
					this.codeLines.get(i).getCodeLineSuggestions(5300);
					break;
				}else{
					this.codeLines.get(i+1).setProcessedline(myLine + this.codeLines.get(i+1).getProcessedLine());
				}
				/* clear the prcessed line */
				this.codeLines.get(i).setProcessedline("");
			} else {

				uCodeLine myCodeLine = this.codeLines.get(i);

				/* look for the indentation, the processed class line is set in the constructor of ucodeline */
				if (myCodeLine.isLevelCommand()){
					myCodeLine.setAtIndentations(atIndentation);
					atIndentation++;
				} else if (myCodeLine.isLevelCommandCloser()){
					atIndentation--;
					myCodeLine.setAtIndentations(atIndentation);
				} else {
					/* set the indentation */
					myCodeLine.setAtIndentations(atIndentation);
				}


			}
		}



	}//createArrayListFromString


	public void executePeerReviewMultipleLines(){
		/* process the set of codelines and search for violations */
		for (int i = 0; i < this.codeLines.size(); i++){
			uCodeLine myLine = this.codeLines.get(i);

			if (myLine.needsErrorHandling()){
				/* if there are no more lines or there are, but it contains no error handling
				($status is present) than raise an error
				*/

				/* search for the next codeline */
				int k = i+1;
				while (k < this.codeLines.size()
					&& this.codeLines.get(k).getProcessedLine().isEmpty()){
					k++;
				}

				/* found the next line, or no more lines */
				if (k >= this.codeLines.size()){
					/* no next line, so no error handling */
					myLine.setWarning(myLine.getCodeLineSuggestions(7000));
				} else {
					/* found it, test it and report it */
					if(!this.codeLines.get(k).commandInCodeLine("$STATUS")){
						/* no error handling in the next line */
						myLine.setWarning(myLine.getCodeLineSuggestions(7000));
					}
				}
			}
		}
	}


	public void getSubProcedures(){
		/**
		 * This function scans the set of codelines to determine if it contains
		 * subfunctions like entry and operations.
		 */

		/* start stepping through the lines */
		boolean isEntry = false;

		uOperation myOperation = new uOperation();
		for (int i = 0; i < this.getCodelines().size(); i++){

			String myLine = new String(this.getCodelines().get(i).getProcessedLine());

			if (!isEntry){
				/* if the line starts with entry or operation we hevae found a sub procedure */
				if ((myLine.length() >= 5 && myLine.substring(0,5).equals("ENTRY"))
					|| (myLine.length() >= 9 && myLine.substring(0,9).equals("OPERATION"))){

					/* set the temporary indicator that we are in an entry or an operation */
					isEntry = true;
					myOperation = new uOperation();

					/* add the code object */
					myOperation.setCodeline(this.getCodelines().get(i));
				}
			} else {
				/* add the code object */
				myOperation.setCodeline(this.getCodelines().get(i));

				/* if the length is exactly 3 positions of the processed line and it
				states END, we have reached the end */
				if (myLine.length() == 3
					&& myLine.equals("END")){

					/* add to the list of operations */
					myOperation.processOperation();
					myOperations.add(myOperation);

					isEntry = false;
				}//endif
				if (i == (this.getCodelines().size()-1)){
					/* in some cases the END line may be missing. So when we get to
					the end of the arraylist of lines we shoul terminate
					*/

					/* add to the list of operations */
					myOperation.processOperation();
					myOperations.add(myOperation);

					isEntry = false;

					/* leave */
					break;
				}//endif
			}//endif
		}//endfor
	}




	/*** getters and setters ***/
	/*** triggers ***/
	public void setTriggerName(String myTriggerName){
		this.triggerName = myTriggerName;
	}
	public String getTriggerName(){
		return this.triggerName;
	}

	/*** codelines ***/
	public void setCodelines(ArrayList<uCodeLine> codeLines){
		this.codeLines = codeLines;
	}
	public ArrayList<uCodeLine> getCodelines(){
		return this.codeLines;
	}

	/*** processed lines ***/
	public ArrayList<uCodeLine> getActualCode(){
		/* init */
		ArrayList<uCodeLine> lines = new ArrayList<uCodeLine>();

		/* get the processed classlines */
		for (int i =0; i < this.codeLines.size(); i++){
			if (!this.codeLines.get(i).getProcessedLine().isEmpty()){
				//System.out.println(this.codeLines.get(i).getProcessedLine());
				lines.add(this.codeLines.get(i));
			}
		}
		return lines;
	}

	public String getActualCodeString(int z){
		/* init */
		String code = new String();

		/* get the processed classlines */
		for (int i = z; i < this.codeLines.size(); i++){
			if (!this.codeLines.get(i).getProcessedLine().isEmpty()){
				//System.out.println(this.codeLines.get(i).getProcessedLine());
				if (code.isEmpty()){
					code = this.codeLines.get(i).getProcessedLine();
				} else {
					code += "<CR/>" + this.codeLines.get(i).getProcessedLine();
				}
			}
		}
		return code;
	}


	/*** library ***/
	public String getLibraryname(){
		return this.libraryName;
	}
	public void setLibraryname(String myLibrary){
		this.libraryName = myLibrary;
	}

	/* replace the trigger content from the */
	public void replaceTriggerContentDeprecated(ArrayList<uLines> myTriggers){

		/*if the arraylist of code is empty */
		if (this.codeLines.isEmpty()){
			System.out.println("uLines: Trigger is empty");

			/* step throught the myTriggers arraylist looking for the right trigger name */
			for (int i = 0; i < myTriggers.size(); i++){
				System.out.println("uLines: Looking for the trigger");
				if (this.triggerName.equals(myTriggers.get(i).getTriggerName())){
					System.out.println("uLines: Found it ");

					/* the correct trigger found now replace the code */
					this.setCodelines(myTriggers.get(i).getCodelines());
				}
			}
		} else {
			System.out.println("uLines: Trigger already contains code!");
		}
	}


	/** determine the pointer where the snippet ends, return this value as the pointer to start looking from */
	//public int getSnippetPointer(uLines codeSource, int z, ArrayList<uCodeLine> snippet){
	public static int getSnippetPointer(ArrayList<uCodeLine> actualCode, ArrayList<uCodeLine> snippet, int counter){
		/* init */

		if (actualCode.isEmpty()){
			return 0;
		} else {

			int lineNumber = snippet.get(0).getLinenumber();

			/* if the line number is negative it was never changed */
			if (lineNumber < 0){
				System.out.println("uLines: Error while retrieving linenumber!!!");
				return -1;
			}

			/* Start stepping through the source classlines  */
			for (int i = counter; i < actualCode.size(); i++){
				if (actualCode.get(i).getLinenumber() == lineNumber){
					/* yahtzee, we found it */
					//new printLogging("*** this.getSnippetSize(snippet) " + this.getSnippetSize(snippet) );

					//z = i + this.getSnippetSize(snippet);

					//new printLogging("*** z " + z);
					//new printLogging("*** i " + i);
					//new printLogging("*** linenumber  " + lineNumber);

					return i;
				}
			}

			return 0;
		}
	}


	/** get the size of the snippet and return it. But only if part of if, while etc. otherwise return 0*/
	public int getSnippetSize(ArrayList<uCodeLine> snippet){

		/* init */
		int z = 0;
		String sourceLine = snippet.get(0).getProcessedLine();

		if(snippet.isEmpty()){
			return z;
		}

		/* if the first line contains a level command, return the size otherwise return 0 */
		if (snippet.get(0).isLevelCommand()){
			z = snippet.size();
		} else {
			z = 0;
		}
		return z;

	}


	/* isOriginal */
	public void setOriginal(boolean value){
		this.isOriginal = value;

		/* set the underlying individual lines */
		for (int i = 0; i < this.codeLines.size(); i++){
			this.codeLines.get(i).setOriginal(value);
		}
	}
	public boolean getOriginal(){
		return this.isOriginal;
	}

	/* isOriginal */
	public void setDuplicate(boolean value){
		this.isDuplicate = value;

		/* set the underlying individual lines */
		for (int i = 0; i < this.codeLines.size(); i++){
			this.codeLines.get(i).setDuplicate(value);
		}
	}
	public boolean getDuplicate(){
		return this.isDuplicate;
	}
}