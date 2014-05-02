import java.util.ArrayList;
/* java persistance */
import java.io.Serializable;

public class uCodeLine extends generalFunctions implements Serializable{

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

	private int myLineNumber = -1;

	/* number of indentations */
	private int atIndentations = -1;

	/**
	The line as it is passed bij the constructor
	*/
	private String myClassLine = new String();
	/**
	The classline after is it stripped of comments and blank spaces. So it contains actual code.
	*/
	private String myProcessedLine = new String();

	/** A list of warnings, errors and suggestions partaining to the codeline */
	public ArrayList<String> myWarnings = new ArrayList<String>();

	/** The total number of lines in the application */
	static int atTotalLines = 0;

	/** the number of commented or blank lines */
	static int atCommentLines = 0;

	/** The number of actual codelines */
	static int atCodeLines = 0;

	/** The number of duplicate lines */
	static int atDuplicates = 0;


	uCodeLine(int myNumber, String myLine){
		/* every lines that is created means 1 added tot the totallines */
		this.atTotalLines++;

		/* constructor */
		this.setClassline(myLine);
		this.setLinenumber(myNumber);

		/* if the line is not comment process it */
		if (!this.isComment()){
			/* actual code line found */
			this.myProcessedLine = getClassline();
			this.atCodeLines++;

		} else {
			/* comment found */
			this.atCommentLines++;
		}

		/* check for debug statements */
		if(commandInCodeLine("DEBUG")){
			this.setWarning(this.getCodeLineSuggestions(3000));
		}

		/* check for database independence, SQL statements in string */
		if(commandInCodeLine("SQL ")){
					this.setWarning(this.getCodeLineSuggestions(4030));
		}

		/* check for an empty line with spaces and/or tabs */
		if (this.getClassline().length() > 0
			&& this.getClassline().trim().isEmpty()){

			// there is nothing but spaces
			this.setProcessedline("");
			this.setWarning(this.getCodeLineSuggestions(1020));
		}

		/* comment after code?? */
		if (this.hasComment()){
			this.setWarning(this.getCodeLineSuggestions(1000));
			/* remove the comment from the processed line */
			this.setProcessedline(this.getProcessedLine().substring(0, this.getProcessedLine().indexOf(";",0)));
		}

		/* single line IF statement?? */
		if (this.isSingleLineIfStatement()){
			this.setWarning(this.getCodeLineSuggestions(4000));
		}

		/* deprecated code?? */
		if (this.isDeprecated()){
			this.setWarning(this.getCodeLineSuggestions(4020));
		}


		/* finally trim the line */
		this.setProcessedline(this.getProcessedLine().trim());
	}


	public String getCodeLineSuggestions(int messageNumber){
		/* get the message corresponding  with the messagenumber */

		String myValue = new String();

		switch (messageNumber){

			case 1000:myValue = "Suggestion (" + messageNumber + "): Comment found after codeline.";break;
			case 1010:myValue = "Suggestion (" + messageNumber + "): Random usage of spaces and tabs found.";break;
			case 1020:myValue = "Suggestion (" + messageNumber + "): Spaces found in otherwise empty string.";break;
			case 1030:myValue = "Suggestion (" + messageNumber + "): Spaces or tabs found at the end of the line.";break;

			/* component stuff */
			case 1100:myValue = "Warning (" + messageNumber + "): Component needs te be cleaned!";break;

			// include commands
			case 2000:myValue = "Warning (" + messageNumber + "): Library not given in #INCLUDE.";break;
			case 2010:myValue = "Warning (" + messageNumber + "): Empty #COMMENT command.";break;

			// coding violations
			case 3000:myValue = "Warning (" + messageNumber + "): Debug statement found.";break;
			case 3010:myValue = "Warning (" + messageNumber + "): Entry without end found.";break;



			case 4000:myValue = "Warning (" + messageNumber + "): usage of deprecated code.";break;
			case 4010:myValue = "Warning (" + messageNumber + "): GOTO command found in code.";break;
			case 4020:myValue = "Warning (" + messageNumber + "): Single line if statement found.";break;
			case 4030:myValue = "Warning (" + messageNumber + "): Database specific statement (SQL) found.";break;

			/*
			 * Code validations
			 */
			case 5000:myValue = "Warning (" + messageNumber + "): Variable is never used in proc.";break;
			case 5001:myValue = "Warning (" + messageNumber + "): Component variable is never used in proc.";break;

			case 5010:myValue = "Warning (" + messageNumber + "): Parameter is never used in proc.";break;
			case 5210:myValue = "Warning (" + messageNumber + "): Function contains no fields or entities. It should be defined in the global or include proc section.";break;
			case 5220:myValue = "Warning (" + messageNumber + "): Function contains one entity. It should be defined in the entity operations trigger.";break;
			case 5230:myValue = "Warning (" + messageNumber + "): Function contains one field. It should be defined in the field operations trigger.";break;
			case 5240:myValue = "Warning (" + messageNumber + "): Function contains more than one entity. It should be defined in component operations trigger.";break;

			case 5300:myValue = "Warning (" + messageNumber + "): End of line terminator %\\ found without corresponding line.";break;

			/*
			 * Model infringements
			 */
			case 6000:myValue = "Warning (" + messageNumber + "): Field is not fully qualified (i.e. FIELDNAME.ENTNAME.";break;
			case 6100:myValue = "Warning (" + messageNumber + "): No relation found between entities ";break;
			case 6200:myValue = "Warning (" + messageNumber + "): No entity painted on component";break;
			/*
			 * Errorhandling
			 */
			case 7000:myValue = "Error (" + messageNumber + "): No errorhandling found in next codeline.";break;

			case 9000:myValue = "Suggestion (" + messageNumber + "): Duplicate codelines .";break;
			default:
				myValue = "";
				break;
		}//endswitch

		return myValue;
	}//getCodeLineSuggestions


	/*** boolean operators ***/
	public boolean isInclude(){
		/* this method determines if the commandline contains an include statement */

		if (this.getProcessedLine().startsWith("#INCLUDE")){
			//System.out.println(this.getProcessedLine());
			return true;
		} else {
			return false;
		}
	}//isComment

	public boolean hasComment(){
		/* if the processedclassline contains a semicolon somewhere in the line
		then we have found comment after the instrucution */

		if (!this.getProcessedLine().trim().isEmpty()
			&& this.getProcessedLine().trim().indexOf(";", 0)>0){
			return true;
		} else {
			return false;
		}
	}//isComment


	public boolean isComment(){
		/* if the string, after it has its tabs and spaces removed
		has a semicolon as a first parameter it is a comment */

		if (!this.getClassline().trim().isEmpty()
			&& this.getClassline().trim().substring(0,1).equals(";")){
			return true;
		} else {
			return false;
		}
	}//isComment


	public boolean isIncomplete(){
		/* check if the line is an incomplete line */

		if (this.getProcessedLine().length() >= 2
			&& this.getProcessedLine().indexOf("%\\", 0 ) > 0){
			return true;

		} else {
			/* otherwise */
			return false;
		}
	}


	public boolean needsErrorHandling(){
		/* check if the code in the line requires error handling */

		/*
		 * Define the arraylist locally so it keeps some memry free after use
		 */
		ArrayList<String> myCommands = new ArrayList<String>();


		/* Which commands need error handling */
		myCommands.clear();
		myCommands.add("CALL");
		myCommands.add("RETRIEVE");
		myCommands.add("STORE");
		myCommands.add("COMMIT");
		myCommands.add("RUN");
		myCommands.add("ACTIVATE");

		for (int i=0; i < myCommands.size(); i++){
			String myCommand = myCommands.get(i);

			if (this.commandInCodeLine(myCommand)){
				//this.setWarning("Command " + myCommand + " needs error handling ");
				return true;
			}
		}
		/* otherwise */
		return false;
	}


	/** look if there is a command that means a level lower of indentation
	This function returns false if it is not a levelcommand of a single line if statement. It returns true if it is a level command.
	*/
	public boolean isLevelCommand(){

		/* init */
		boolean isTrue = false;

		String codeLine = this.getProcessedLine();

		if (codeLine.startsWith("OPERATION")
			|| codeLine.startsWith("ENTRY")
			|| codeLine.startsWith("VARIABLES")
			|| codeLine.startsWith("PARAMS")
			|| codeLine.startsWith("IF")
			|| codeLine.startsWith("#IF")
			|| codeLine.startsWith("WHILE")
			|| codeLine.startsWith("SELECTCASE")){

			/* however if the IF statement is a single line command, do nothing */
			if (this.isSingleLineIfStatement()){
				isTrue = false;
				//new printLogging("SINGLE LINE IF (" +lineNumber+ "): " + codeLine, "C:/dvo1/out/LOG/levelcommand.log");

			} else {
				isTrue = true;
				//new printLogging("OTHER (" +lineNumber+ "): " + codeLine, "C:/dvo1/out/LOG/levelcommand.log");

			}
		}

		return isTrue;
	}

	/** look if there is a command that is an ending tag for the level of indentation */
	public boolean isLevelCommandCloser(){
		boolean isTrue = false;

		String codeLine = this.getProcessedLine();

		if (codeLine.startsWith("END")
			|| codeLine.startsWith("ENDVARIABLES")
			|| codeLine.startsWith("ENDPARAMS")
			|| codeLine.startsWith("ENDIF")
			|| codeLine.startsWith("#ENDIF")
			|| codeLine.startsWith("ENDWHILE")
			|| codeLine.startsWith("ENDSELECTCASE")){
			isTrue = true;
		}

		return isTrue;
	}

	public boolean commandInCodeLine(String myCommand){
		/* check if the command is used in this commandline. */

		/* if the procesed line is empty we are dealing with comment or a blank line
		in that case return false */
		if (this.myProcessedLine.isEmpty()){
			return false;
		}//endif

		/* if the line can contain the command */
		if (this.myProcessedLine.length() >= myCommand.length()){
			/* let's compare */


			/* is it the first command in the line? */
			if (this.myProcessedLine.substring(0, myCommand.length()).equals(myCommand)){
				return true;
			}//endif

			/* it can be found with a space for and aft */
			if (this.myProcessedLine.indexOf(" " + myCommand + " ", 0) >= 0){
				return true;
			}//endif

			/* it can be found after a bracket and a space */
			if (this.myProcessedLine.indexOf(") " + myCommand, 0) >= 0){
				return true;
			}//endif

			/* or after a bracket with no space */
			if (this.myProcessedLine.indexOf(")" + myCommand, 0) >= 0){
				return true;
			}//endif

			/* or within a string  */
			if (this.myProcessedLine.indexOf("%" + myCommand + "%", 0) >= 0){
				return true;
			}//endif


			/* if it is otherwise found we need to investigate */
			if (myProcessedLine.indexOf(myCommand, 0) >= 0){
				return true;
			}//endif
		}//endif
		return false;
	}

	/*** getters and setters ***/
	/* classlines */
	public void setClassline(String myLine){
		this.myClassLine = myLine;
	}
	public String getClassline(){
		return this.myClassLine;
	}

	/* processed lines */
	public void setProcessedline(String myLine){
		this.myProcessedLine = myLine;
	}
	public String getProcessedLine(){
		return this.myProcessedLine;
	}

	/* line number */
	public void setLinenumber(int myNumber){
		this.myLineNumber = myNumber;
	}
	public int getLinenumber(){
		return this.myLineNumber;
	}
	public String getLinenumberInString(){
		/* return the linenumber in a String type of length 5 */
		String temp = new String();

		temp = "     " + Integer.toString(this.getLinenumber());
		temp = temp.substring(temp.length() - 5, temp.length());

		return temp;
	}

	/* warnings */
	public ArrayList<String> getWarnings(){
		return this.myWarnings;
	}
	public void setWarning(String myWarning){
		this.myWarnings.add(myWarning);
	}
	/* include */
	public String getIncludelibrary(String myValue){
		/* return the includeproc name
		The inpuit parameter myValue contains the name of the library of the calling component
		*/
		String myLibrary = new String();

		if (this.isInclude()){
			/* remove the #INCLUDE */
			myLibrary = this.getProcessedLine().substring("#INCLUDE".length(), this.getProcessedLine().length());
			myLibrary = myLibrary.trim();

			/* scan for : */
			if (myLibrary.indexOf(":", 0) > 0){
				/* strip the include proc */
				myLibrary = myLibrary.substring(0, myLibrary.indexOf(":", 0));
			} else {
				/* the library is not entered */
				this.setWarning("Warning (2000): Library not given in #INCLUDE.");
				myLibrary = myValue;
			}
		}
		return myLibrary;
	}

	public String getIncludeproc(){
		/* return the includeproc name */
		String myInclude = new String();

		if (this.isInclude()){
			/* remove the #INCLUDE */
			myInclude = this.getProcessedLine().substring("#INCLUDE".length(), this.getProcessedLine().length());
			myInclude = myInclude.trim();

			/* scan for : */
			if (myInclude.indexOf(":", 0) > 0){
				/* strip the library */
				myInclude = myInclude.substring(myInclude.indexOf(":", 0)+1);
			} else {
				/* do nothing */

			}
		}
		return myInclude;
	}

	/** Test if a code line contains deprecated code */
	public boolean isDeprecated(){
		String codeLine = this.getProcessedLine();

		if (codeLine.startsWith("RUN")
			|| codeLine.startsWith("RUN")){
				return true;
		}
		return false;
	}

	/** look for a command line that is an IF statement without an corresponding ENDIF */
	public boolean isSingleLineIfStatement(){

		/* if it starts with if but doesnot ent with a bracket, there is code found after the bracket, ergo single line if statement */
		if (this.myProcessedLine.startsWith("IF")
			&& !this.myProcessedLine.endsWith(")")
		){
			return true;
		}
		return false;
	}

	/* isOriginal */
	public void setOriginal(boolean value){
		this.isOriginal = value;
	}
	public boolean getOriginal(){
		return this.isOriginal;
	}

	/* isDuplicate */
	public void setDuplicate(boolean value){
		/* if we change the value than add or subtract the nummber of duplicates */

		if (this.isDuplicate != value){
			//new printLogging("[setDuplicate] " + this.isDuplicate + " = " + value);

			if (value = true){
				this.atDuplicates++;
			} else if(value = false){
				this.atDuplicates--;
			}
		}
		/* change the value */
		this.isDuplicate = value;
	}

	public boolean isDuplicate(){
		return this.isDuplicate;
	}

	/* Comment Lines */
	public void setAtCommentLines(int atCommentLines){
		this.atCommentLines = atCommentLines;
	}
	public int getAtCommentLines(){
		return this.atCommentLines;
	}

	/* Total Lines  */
	public void setTotalLines(int atTotalLines){
		this.atTotalLines = atTotalLines;
	}
	public int getTotalLines(){
		return this.atTotalLines;
	}

	/* Duplicate Lines  */
	/** set the number of duplicates */
	public void setAtDuplicateLines(int atDuplicates){
		this.atDuplicates = atDuplicates;
	}
	/** add the number to the duplicate lines */
	public void addAtDuplicateLines(int atDuplicates){
		this.atDuplicates += atDuplicates;
	}
	/** get the number of duplicates */
	public int getAtDuplicateLines(){
		return this.atDuplicates;
	}

	/** indentations */
	/** set the number of indentations */
	public void setAtIndentations(int atIndentations){
		//new printLogging("Changing the indentation to " + atIndentations, "levelCommand");

		this.atIndentations = atIndentations;
	}
	/** get the number of indentations */
	public int getAtIndentations(){
		return this.atIndentations;
	}
	/** get the indentations in spaces */
	public String getIndentations(){

		String indentations = new String();

		for (int i = 0; i < this.getAtIndentations(); i++){
			indentations += "   ";
		}

		return indentations;
	}

}