import java.util.ArrayList;
/* error handling */
import java.io.IOException;


public class codeSnippet extends generalFunctions{

	//ArrayList<codeSnippet> codeSnippet = new ArrayList<codeSnippet>();
	static String myPath = new String("C:/DVO1/OUT/LOG/SNIPPETS/");

	int startPointer = 0;
	int endPointer = 0;
	int triggerPointer = 0;

	String objectType = new String();
	String triggerName = new String();
	String entityName = new String();
	String objectName =  new String();

	String singleStringOfSnippet = new String();

	static int includeNumber = 0;

	ArrayList<uCodeLine> snippet = new ArrayList<uCodeLine>();


	codeSnippet(ArrayList<uCodeLine> actualCode, String objectType, String objectName, String triggerName, int pointer){

		/* init */
		ArrayList<uCodeLine> snippet = new ArrayList<uCodeLine>();

		/* first set the starting pointer */
		this.setStartPointer(pointer);
		this.setObjectName(objectName);
		this.setObjectType(objectType);
		this.setTriggerName(triggerName);

		/* if the pointer = -1 get the entire trigger */
		if (pointer == -1){
			/* correct the pointer */
			this.setStartPointer(0);

			/* add the codelines */
			for (int i = 0; i < actualCode.size(); i++){
				snippet.add(actualCode.get(i));
			}

		} else {
			/* step through the lines looking for the snippet */
			for (int i = pointer; i < actualCode.size(); i++){

				String sourceLine = actualCode.get(i).getProcessedLine();

				/* create snippets */
				if (sourceLine.startsWith("OPERATION")){
					//new printLogging("*** Operation ***");
					snippet = this.createSnippet(i, actualCode, "OPERATION", "END", true);
					break;
				} else if (sourceLine.startsWith("ENTRY")){
					//new printLogging("*** Entry ***");
					snippet = this.createSnippet(i, actualCode, "ENTRY", "END", true);
					break;
				} else if(sourceLine.startsWith("VARIABLES")){
					//new printLogging("*** Variables ***");
					snippet = this.createSnippet(i, actualCode, "VARIABLES", "ENDVARIABLES", true);
					break;
				} else if(sourceLine.startsWith("PARAMS")){
					//new printLogging("*** Params ***");
					snippet = this.createSnippet(i, actualCode, "PARAMS", "ENDPARAMS", true);
					break;
				} else if(sourceLine.startsWith("IF")){
					//new printLogging("*** If ***");
					snippet = this.createSnippet(i, actualCode, "IF", "ENDIF", true);
					break;
				} else if(sourceLine.startsWith("#IF")){
					//new printLogging("*** #If ***");
					snippet = this.createSnippet(i, actualCode, "#IF", "#ENDIF", true);
					break;
				} else if(sourceLine.startsWith("WHILE")){
					//new printLogging("*** While ***");
					snippet = this.createSnippet(i, actualCode, "WHILE", "ENDWHILE", true);
					break;
				} else if(sourceLine.startsWith("SELECTCASE")){
					//new printLogging("*** Selectcase ***");
					snippet = this.createSnippet(i, actualCode, "SELECTCASE", "ENDSELECTCASE", true);
					break;
				} else if(sourceLine.startsWith("#INCLUDE")){
					//new printLogging("*** #Include (skip) ***");
					/* dont do anything, just skip it */
				} else {
					//new printLogging("*** General Snippet ***");
					if (actualCode.get(i).getAtIndentations() < 1
						&& !actualCode.get(i).isLevelCommandCloser()
						&& !actualCode.get(i).getProcessedLine().equals("ELSE")){
						//new printLogging("*** General ***");
						//snippet.add(new uCodeLine(0, "OTHER" + actualCode.get(i).getAtIndentations() + " "+ actualCode.get(i).getProcessedLine()));

						for (int j = i; j < actualCode.size(); j++){
							//System.out.println("Size : " + j + " of " + actualCode.size());

							/* if the line starts with one of the commando's or is already marked as duplicate */
							if (actualCode.get(j).isLevelCommand()
							|| actualCode.get(j).isDuplicate()
							){
								/* reset i */
								i = j;
								/* Leave the inner loop */
								break;
							} else {
								snippet.add(actualCode.get(j));
							}

						}//endfor
						/* Leave the outer loop */
						break;
					}//endif
				}//endif
			}//endfor
		}

		/* set the end pointer */
		this.setEndPointer(this.getStartPointer() + snippet.size());

		/* set the snippet */
		this.setSnippet(snippet);

		/* create the single string snippet */
		this.createSingleLineOfSnippet();
		/*************************************/

		if (this.getSnippet().size() > 0){
			this.printSnippet();
		}
	}

	public void saveObject() {
		try {
			this.saveObject(this.myPath, objectType + "." + triggerName + "." + entityName + "." + objectName + ".snip");

		} catch (IOException e) {
			e.printStackTrace();
		}//endtry

	}

	private void rewriteObject(){
		/* this function reprograms the snippet to the current Uniface 9 standards
		only for entries and operations or triggers
		*/
		if (this.snippet.get(0).getProcessedLine().indexOf("ENTRY", 0) < 0
			&& this.snippet.get(0).getProcessedLine().indexOf("OPERATION", 0) < 0){
			return;
		}

		for (int x =0; x < this.snippet.size(); x++ ){


		}
	}


	public ArrayList<uCodeLine> createSnippet(int pointer, ArrayList<uCodeLine> actualCode, String startTag, String endTag, boolean doLevels){
		/* init */
		int level = 0;
		ArrayList<uCodeLine> snippet = new ArrayList<uCodeLine>();
		//snippet.add(new uCodeLine(0, startTag ));

		/* if the pointer = -1 we need to put the entire trigger in,
		the first snippet must be the entire trigger content. */
		if (pointer == -1){
			for (int i = pointer; i < actualCode.size(); i++){
				snippet.add(actualCode.get(i));
			}
		} else {
			//System.out.println("************");
			for (int i = pointer; i < actualCode.size(); i++){
				/* measure the level */
				if (actualCode.get(i).getProcessedLine().startsWith(startTag)){
					level++;
					//System.out.println("level " + level);
				}
				//if (code.get(i).getProcessedLine().startsWith(endTag)){
				if (actualCode.get(i).getProcessedLine().equals(endTag)){
					level--;

					//System.out.println("level " + level);
				}

				//System.out.println(code.get(i).getProcessedLine());
				snippet.add(actualCode.get(i));

				/* if the length of the endtag is the same as the length of the line,
				and the string is the same */
				if(actualCode.get(i).getProcessedLine().length() == endTag.length()
					&& actualCode.get(i).getProcessedLine().equals(endTag)){
					//System.out.println("level " + level);
					if (doLevels){
						if(level == 0){
							break;
						}
					} else {
						break;
					}
				}
			}
		}

		//System.out.println("************");
		return snippet;
	}

	/** create an arraylist of codesnippets */
	public static ArrayList<codeSnippet> createSnippets(ArrayList<uCodeLine> actualCode, String objectType, String objectName, String triggerName, int pointer){
		/*		*/
		int levelCounter = 0;

		/* init */
		ArrayList<codeSnippet> allSnippets = new ArrayList<codeSnippet>();

		/* first snippet is the entire trigger content */
		//allSnippets.add(new codeSnippet(actualCode, objectType, objectName, triggerName, -1));

		/* step throught the code looking for snippets */
		for (int i = 0; i < actualCode.size(); i++){
			/* only create a new snippet if the code starts with a level command or the indentation is 0 */

			if (actualCode.get(i).isLevelCommand()
			|| (actualCode.get(i).getAtIndentations() < 1 && !actualCode.get(i).isLevelCommandCloser())){
				/* if the line has a levelcommand or the level is null we may have a snippet */
				codeSnippet snippet = new codeSnippet(actualCode, objectType, objectName, triggerName, i);

				//new printLogging("*** Snippet size " + snippet.getSnippet().size());

				/* if it is not a level command than raise the pointer */
				if (!actualCode.get(i).isLevelCommand()){
					i += snippet.getSnippet().size();
				}



				/* add the snippet to the list */
				if (!snippet.getSnippet().isEmpty()){
					/* if the size is bigger than 10 lines */
					//if (snippet.getSnippet().size() > 10){
						allSnippets.add(snippet);

					//}
				}
			}
		}

		/* log the snippets */
		//for (int i = 0; i < allSnippets.size(); i++){
			//new printLogging("*** codeSnippet class ***");

			//new printLogging("*** Start : " + allSnippets.get(i).getStartPointer());
			//new printLogging("*** End : " + allSnippets.get(i).getEndPointer());
			//new printLogging(allSnippets.get(i).getSnippet());
		//}

		/* return the snippets */
		return allSnippets;
	}

	public void printSnippet(){
		/** print the snippet in a single line into a log file **/

		/* save the snippet */
		//codeSnippet mySnippet = allSnippets.get(n);
		String myLine = new String();
		String myCode = new String();
		String seperator = new String("@BVM@");

		myLine += objectType + seperator;
		myLine += triggerName + seperator;
		myLine += entityName + seperator;
		myLine += objectName + seperator;

		/* get the first line number */
		myLine += this.getSnippet().get(0).getLinenumber() + seperator;
		/* get the number of lines */
		myLine += this.getSnippet().size() + seperator;

		myLine += getSingleLineOfSnippet();

		new printLogging(myLine, true, "C:/dvo1/out/log/snippets.log");
	}

	public void createSingleLineOfSnippet(){
		/* creates a single string of code seperated bij @DVO@ */

		/* create the lines */
		ArrayList<uCodeLine> myLines = this.getSnippet();
		String seperator = new String("@DVO@");

		for (int x=0; x < myLines.size();x++){
			if (!myLines.get(x).getProcessedLine().isEmpty()){
				if (this.singleStringOfSnippet.isEmpty()){
					this.singleStringOfSnippet = myLines.get(x).getProcessedLine();
				} else {
					this.singleStringOfSnippet += seperator + myLines.get(x).getProcessedLine();
				}
			}
		}
	}

	public String getSingleLineOfSnippet(){
		return this.singleStringOfSnippet;
	}

	public boolean lookForDuplicates(){

		/* translate the component in to a number */
		int classType = -1;
		String objectType = this.getObjectType();

		if (objectType.equals("UFORM")){
			classType = 1;
		} else if (objectType.equals("UCGROUP")){
			classType = 2;
		} else if (objectType.equals("UCFIELD")){
			classType = 3;
		} else if (objectType.equals("UCGROUP")){
			classType = 4;
		} else if (objectType.equals("UCFIELD")){
			classType = 5;
		} else if (objectType.equals("USOURCE_I")){
			classType = 6;
		} else if (objectType.equals("USOURCE_P")){
			classType = 7;
		}

		if (classType < 0){
			new printLogging("*** [lookForDuplicates] Error while looking for duplicates (classType " + objectType + "not set) ***");
		}

		switch(classType){
			case 1:
				//new printLogging("************ Forms ***********");

				/* get all the folder content */
				String[] allForms = getFolderContent(uForm.myPath, null);

				/* init */
				int pointer = getObjectPointer(allForms, this.getObjectName());
				int linePointer = 0;

				/* within the snippet loop through the remaining objects */
				if (allForms != null){

					/* step through the remaining forms */
					for (int j = pointer; j < allForms.length; j++){
						/* load the object */
						uForm form = (uForm)loadObject(uForm.myPath, allForms[j]);
						int triggerPointer = form.getTriggerPointer(this.getObjectName(), this.getTriggerName());

						//System.out.println("Component target: " + form.getComponentName());
						//new printLogging("Component target: " + form.getComponentName(), "tracing");

						/* step through the triggers */
						for (int k = triggerPointer; k < form.getTriggers().size(); k++){
							/* compare the codelines */

							/* if the name and the trigger of the component are the same
							we start at the endpointer of the snippet, otherwise use 0 */
							if (this.getObjectName().equals(form.getComponentName())
								&& this.getTriggerName().equals(form.getTriggers().get(k).getTriggerName())
							){
								linePointer = this.getEndPointer();
							} else {
								linePointer = 0;
							}

							/* and on screen */
							//new printLogging("Component trigger target: " + form.getComponentName() + "." + form.getTriggers().get(k).getTriggerName() + "("  + linePointer + ")", "tracing");

							if (this.compareCodeLines(form.getTriggers().get(k), linePointer, form.getComponentName(), form.getTriggers().get(k).getTriggerName()) ){
								/* duplicate found log it */
								new printLogging("Component trigger target: " + form.getComponentName() + "." + form.getTriggers().get(k).getTriggerName() + "("  + linePointer + ")", "tracing");
								//new printLogging("--> duplicate found in form!", "tracing");
								return true;
							}
						}
					}
				}

				/* set the pointer to 0 for the next loop */
				pointer = 0;
			case 2:
				//new printLogging("************ Conceptual Entities ***********");

				/* get all the folder content */
				String[] allEntities		= getFolderContent(ucGroup.myPath, null);

				/* init */
				pointer = getObjectPointer(allEntities, this.getObjectName());
				linePointer = 0;

				/* within the snippet loop through the remaining objects */
				if (allEntities != null){
					for (int j = pointer; j < allEntities.length; j++){
						/* load the object */
						ucGroup entity = (ucGroup)loadObject(ucGroup.myPath, allEntities[j]);
						int triggerPointer = entity.getTriggerPointer(this.getObjectName(), this.getTriggerName());

						/* and on screen */
						//System.out.println("Conc. entity target: " + entity.getEntityName());
						//new printLogging("Conc. entity target: " + entity.getEntityName(), "tracing");


						/* step through the triggers */
						for (int k = triggerPointer; k < entity.getTriggers().size(); k++){
							/* compare the codelines */

							/* if the name and the trigger of the component are the same
							we start at the endpointer of the snippet, otherwise use 0 */
							if (this.getObjectName().equals(entity.getEntityName())
								&& this.getTriggerName().equals(entity.getTriggers().get(k).getTriggerName())
							){
								linePointer = this.getEndPointer();
							} else {
								linePointer = 0;
							}

							/* and on screen */
							//System.out.println("Conc. entity trigger target: " + entity.getTriggers().get(k).getTriggerName());
							//new printLogging("Conc. entity trigger target: " + entity.getTriggers().get(k).getTriggerName()+ "("  + linePointer + ")", "tracing");

							if (this.compareCodeLines(entity.getTriggers().get(k), linePointer, entity.getEntityName(), entity.getTriggers().get(k).getTriggerName()) ){
								//System.out.println("--> duplicate found in conceptual entity!");
								//new printLogging("--> duplicate found in conceptual entity!", "tracing");
								return true;
							}
						}
					}
				}

				/* set the pointer to 0 for the next loop */
				allEntities = null;
				pointer = 0;
			case 3:
				//new printLogging("************ Conceptual Fields ***********");
				/* get all the folder content */
				String[] allFields			= getFolderContent(ucField.myPath, null);

				/* init */
				pointer = getObjectPointer(allFields, this.getObjectName());
				linePointer = 0;

				/* within the snippet loop through the remaining objects */
				if (pointer > 0){
					for (int j = pointer; j < allFields.length; j++){
						/* load the object */
						ucField field = (ucField)loadObject(ucField.myPath, allFields[j]);
						int triggerPointer = field.getTriggerPointer(this.getObjectName(), this.getTriggerName(), this.getEntityName());

						//System.out.println("Conc. field target: " + field.getEntityName() + "." + field.getFieldName());
						//new printLogging("Conc. field target: " + field.getEntityName() + "." + field.getFieldName(), "tracing");

						/* step through the triggers */
						for (int k = triggerPointer; k < field.getTriggers().size(); k++){
							/* compare the codelines */

							/* if the name and the trigger of the component are the same
							we start at the endpointer of the snippet, otherwise use 0 */
							if (this.getEntityName().equals(field.getEntityName())
								&& this.getObjectName().equals(field.getFieldName())
								&& this.getTriggerName().equals(field.getTriggers().get(k).getTriggerName())
							){
								linePointer = this.getEndPointer();
							} else {
								linePointer = 0;
							}

							/* and on screen */
							//System.out.println("Conc. field trigger target: " + field.getEntityName() + "." + field.getFieldName() + field.getTriggers().get(k).getTriggerName());
							//new printLogging("Conc. field trigger target: " + field.getEntityName() + "." + field.getFieldName() + field.getTriggers().get(k).getTriggerName() + " ("  + linePointer + ")", "tracing");

							if (this.compareCodeLines(field.getTriggers().get(k), linePointer, field.getEntityName(), field.getTriggers().get(k).getTriggerName()) ){
								//System.out.println("--> duplicate found in conceptual field!");
								//new printLogging("--> duplicate found in conceptual field!", "tracing");
								return true;
							}
						}
					}
				}

				/* set the pointer to 0 for the next loop */
				allFields = null;
				pointer = 0;
			case 4:
				//new printLogging("************ Painted Entities ***********");
				/* get all the folder content */
				allEntities		= getFolderContent(uxGroup.myPath, null);

				/* init */
				pointer = getObjectPointer(allEntities, this.getObjectName());
				linePointer = 0;

				/* within the snippet loop through the remaining objects */
				if (pointer > 0){
					for (int j = pointer; j < allEntities.length; j++){
						/* load the object */
						uxGroup entity = (uxGroup)loadObject(uxGroup.myPath, allEntities[j]);
						int triggerPointer = entity.getTriggerPointer(this.getObjectName(), this.getTriggerName());

						//System.out.println("Painted entity target: " + entity.getEntityName());
						//new printLogging("Painted entity target: " + entity.getEntityName(), "tracing");


						/* step through the triggers */
						for (int k = triggerPointer; k < entity.getTriggers().size(); k++){
							/* compare the codelines */

							/* if the name and the trigger of the component are the same
							we start at the endpointer of the snippet, otherwise use 0 */
							if (this.getObjectName().equals(entity.getEntityName())
								&& this.getTriggerName().equals(entity.getTriggers().get(k).getTriggerName())
							){
								linePointer = this.getEndPointer();
							} else {
								linePointer = 0;
							}

							/* and on screen */
							//System.out.println("Painted entity trigger target: " + entity.getTriggers().get(k).getTriggerName());
							//new printLogging("Painted entity trigger target: " + entity.getTriggers().get(k).getTriggerName() + " ("  + linePointer + ")", "tracing");

							if (this.compareCodeLines(entity.getTriggers().get(k), linePointer, entity.getEntityName(), entity.getTriggers().get(k).getTriggerName()) ){
								//System.out.println("--> duplicate found in painted entity!");
								//new printLogging("--> duplicate found in painted entity!", "tracing");
								return true;
							}
						}
					}
				}

				/* set the pointer to 0 for the next loop */
				pointer = 0;
			case 5:
				//new printLogging("************ Painted Fields ***********");
				/* get all the folder content */
				allFields			= getFolderContent(uxField.myPath, null);

				/* init */
				pointer = getObjectPointer(allFields, this.getObjectName());
				linePointer = 0;

				/* within the snippet loop through the remaining form objects */
				if (pointer > 0){
					for (int j = pointer; j < allFields.length; j++){
						/* load the object */
						uxField field = (uxField)loadObject(uxField.myPath, allFields[j]);
						int triggerPointer = field.getTriggerPointer(this.getObjectName(), this.getTriggerName(), this.getEntityName());

						//System.out.println("Painted field target: " + field.getEntityName() + "." + field.getFieldName());
						//new printLogging("Painted field target: " + field.getEntityName() + "." + field.getFieldName(), "tracing");


						/* step through the triggers */
						for (int k = triggerPointer; k < field.getTriggers().size(); k++){
							/* compare the codelines */

							/* if the name and the trigger of the component are the same
							we start at the endpointer of the snippet, otherwise use 0 */
							if (this.getEntityName().equals(field.getEntityName())
								&& this.getObjectName().equals(field.getFieldName())
								&& this.getTriggerName().equals(field.getTriggers().get(k).getTriggerName())
							){
								linePointer = this.getEndPointer();
							} else {
								linePointer = 0;
							}

							/* and on screen */
							//System.out.println("Painted field trigger target: " + field.getEntityName() + "." + field.getFieldName() + field.getTriggers().get(k).getTriggerName());
							//new printLogging("Painted field trigger target: " + field.getEntityName() + "." + field.getFieldName() + field.getTriggers().get(k).getTriggerName()+ " ("  + linePointer + ")", "tracing");

							if (this.compareCodeLines(field.getTriggers().get(k), linePointer, field.getEntityName(), field.getTriggers().get(k).getTriggerName()) ){
								//System.out.println("--> duplicate found in painted field!");
								//new printLogging("--> duplicate found in painted field!", "tracing");
								return true;
							}
						}
					}
				}
				/* set the pointer to 0 for the next loop */
				pointer = 0;
			case 6:
				//new printLogging("************ Include Procs ***********");
				/* get all the folder content */
				String[] allProcs = getFolderContent(uSource.myPath + "I/", null);

				/* init */
				pointer = getObjectPointer(allProcs, this.getObjectName());

				/* within the snippet loop through the remaining form objects */
				if (pointer > 0){
					for (int j = pointer; j < allProcs.length; j++){
						/* load the object */
						uSource proc = (uSource)loadObject(uSource.myPath, allProcs[j]);

						//System.out.println("Include proc target: " + proc.getLibraryname() +"."+ proc.getSourcename());
						//new printLogging("Include proc target: " + proc.getLibraryname() +"."+ proc.getSourcename(), "tracing");

						/* if the type of the component and the name
						we start at the endpointer of the snippet, otherwise use 0 */
						if ("I".equals(proc.getObjectType())
							&& this.getObjectName().equals(proc.getSourcename())
						){
							linePointer = this.getEndPointer();
						} else {
							linePointer = 0;
						}

						if (this.compareCodeLines(proc.getCodeObject(), linePointer, proc.getSourcename(), "(include proc)" )){
							//System.out.println("--> duplicate found in include proc!");
							//new printLogging("--> duplicate found in include proc!", "tracing");
							return true;
						}
					}
				}
				/* set the pointer to 0 for the next loop */
				allProcs = null;
				pointer = 0;
			case 7:
				//new printLogging("************ Global Procs ***********");
				/* get all the folder content */
				allProcs = getFolderContent(uSource.myPath + "P/", null);

				/* init */
				pointer = getObjectPointer(allProcs, this.getObjectName());

				/* within the snippet loop through the remaining form objects */
				if (pointer > 0){
					for (int j = pointer; j < allProcs.length; j++){
						/* load the object */
						uSource proc = (uSource)loadObject(uSource.myPath, allProcs[j]);

						//System.out.println("Global proc target: " + proc.getLibraryname() +"."+ proc.getSourcename());
						//new printLogging("Global proc target: " + proc.getLibraryname() +"."+ proc.getSourcename(), "tracing");

						/* if the type of the component and the name
						we start at the endpointer of the snippet, otherwise use 0 */
						if ("P".equals(proc.getObjectType())
							&& this.getObjectName().equals(proc.getSourcename())
						){
							linePointer = this.getEndPointer();
						} else {
							linePointer = 0;
						}

						if (this.compareCodeLines(proc.getCodeObject(), linePointer, proc.getSourcename(), "(global proc)" )){
							//System.out.println("--> duplicate found in global proc!");
							//new printLogging("--> duplicate found in global proc!", "tracing");
							return true;
						}
					}
				}
		}
		return false;

	}

	/** compare the snippet and the trigger content looking for a duplicate */
	private boolean compareCodeLines(uLines trigger, int pointer, String objectName, String triggerName){


		//new printLogging("Start compareCodeLines");

		if(this.getSnippet().isEmpty()){
			// return false
			return false;
		}


		/* save the snippet */
		//new printLogging(this.getSnippet(),
		//		objectName + "." + triggerName + "_" + pointer,
		//		objectName + "." + triggerName,
		//		"C:/dvo1/out/log/snippets/");

		/* init */
		ArrayList<uCodeLine> actualCode = trigger.getActualCode();
		String firstLine = this.getSnippet().get(0).getProcessedLine();

		/* if the snippet size is larger then quit */
		if (this.getSnippet().size() > actualCode.size()){
			return false;
		}

		/* if the first line of the snippet is already marked as duplicate then quit */
		if (this.getSnippet().get(0).isDuplicate()){
			return false;
		}

		/* step through the code object looking for a similar first line snippet */
		for (int i = pointer; i< actualCode.size(); i++){

			/* if we have found similar lines, and the line is not already marked as duplicate */
			if (firstLine.equals(actualCode.get(i).getProcessedLine())
				&& !actualCode.get(i).isDuplicate()
			){

				//new printLogging("--> duplicate found");
				boolean isEquals = true;

				/* step through the codelines of the code snippet */
				for (int j = i; j < this.getSnippet().size(); j++){

					/* if there are no more lines left leave the loop */
					if ((i+j) < actualCode.size()){
						/* if both codelines donot match, leave the loop */
						if (!this.getSnippet().get(j).getProcessedLine().equals(actualCode.get(i + j).getProcessedLine())){
							isEquals = false;
							break;
						}
					} else {
						isEquals = false;
						break;
					}
				}//endfor

				/* if the boolean value is still true */
				if (isEquals){
					/*** one more check to see if the component, line and trigger are the same ***/
					if (this.getObjectName().equals(objectName)
						&& this.getTriggerName().equals(triggerName)
						&& snippet.get(0).getLinenumber() == actualCode.get(i).getLinenumber()
					){
						//new printLogging("Linenumbers and stuff are equal");
						return false;
					}

					new printLogging("******************************");

					/* set the duplicate lines */
					for (int j = 0; j < this.getSnippet().size(); j++){
						this.getSnippet().get(j).setDuplicate(true);
					}

					/* write the logging */
					int atComment = actualCode.get(0).getAtCommentLines();
					int atTotal   = actualCode.get(0).getTotalLines();
					int atDuplicate = actualCode.get(0).getAtDuplicateLines();

					long percComment   =  Math.round( ( (double) atComment / (double) atTotal ) * 100);
					double percDuplicate = Math.round( ( (double) atDuplicate / (double) atTotal ) * 100);

					new printLogging("******************************");
					new printLogging("Source from  --> " + this.getObjectName() + "." + this.getTriggerName() + " (" + snippet.get(0).getLinenumber() + ")");
					new printLogging("Duplicate in --> " + objectName + "." + triggerName + " (" + actualCode.get(i).getLinenumber() + ")");
					new printLogging("% comment    --> " + percComment + " % (" + atComment + " out of " + atTotal + " lines)");
					new printLogging("% duplicate  --> " + percDuplicate + " % (" + atDuplicate + " out of " + atTotal + " lines)");
					new printLogging(snippet);
					//new printLogging("******************************");

					/* print the include file */
					new printLogging(snippet, "include_" + this.includeNumber, "Source from  --> " + this.getObjectName() + "." + this.getTriggerName() + " (" + snippet.get(0).getLinenumber() + ")", "C:/dvo1/out/log/include/");
					this.includeNumber++;

					/* look for the line with the corresponding line number */
					actualCode.get(i).setWarning("Suggestion (9000): Duplicate codelines found in " + objectName + "." + triggerName);
					return true;
				}//endif
			}
		}

		return false;
	}


	/* start pointer */
	public void setStartPointer(int pointer){
		//new printLogging("*** startPointer " + pointer);
		this.startPointer = pointer;
	}
	public int getStartPointer(){
		return this.startPointer;
	}

	/* end pointer */
	public void setEndPointer(int pointer){
		//new printLogging("*** endPointer " + pointer);
		this.endPointer = pointer;
	}
	public int getEndPointer(){
		return this.endPointer;
	}

	/* snippet */
	public void setSnippet (ArrayList<uCodeLine> snippet){
		this.snippet = snippet;
	}
	public ArrayList<uCodeLine> getSnippet(){
		return this.snippet;
	}

	/* objectType */
	public void setObjectType(String objectType){
		this.objectType = objectType;
	}
	public String getObjectType(){
		return this.objectType;
	}

	/* triggerName */
	public void setTriggerName(String triggerName){
		this.triggerName = triggerName;
	}
	public String getTriggerName(){
		return this.triggerName;
	}

	/* objectName */
	public void setObjectName(String objectName){
		this.objectName = objectName;
	}
	public String getObjectName(){
		return this.objectName;
	}

	/* objectName */
	public void setEntityName(String entityName){
		this.entityName = entityName;
	}
	public String getEntityName(){
		return this.entityName;
	}


	/* trigger pointer */
	public void setTriggerPointer(int pointer){
		//new printLogging("*** endPointer " + pointer);
		this.triggerPointer = pointer;
	}
	public int getTriggerPointer(){
		return this.triggerPointer;
	}

}
