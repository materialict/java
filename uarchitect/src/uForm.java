import java.util.ArrayList;

/* java persistance */
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
/* error handling */
import java.io.IOException;
/* java output */
import java.io.BufferedWriter;
import java.io.FileWriter;
/* java file */
import java.io.File;

public class uForm extends generalFunctions implements Serializable{

//	public enum triggers{INIT, CLEAR, RETRIEVE, RECORD, STORE, DELET,
//						ACCEPT, QUIT, MENU, INTKEY, SPRINT, EPRINT,
//						ASYNC, GENERAL, EPRINT, ASYNC, FRLF,
//						FRGF, SFUNC, GETSTATE, SETSTATE, TPLACTUAL2}

	//String componentName = new String();
	static String myPath = new String("C:/dvo1/OUT/DICT/UFORM/");
	static String outPath = new String("C:/dvo1/OUT/PRO/");

	private boolean isProcessed = false;

	ArrayList<uLines> myTriggers = new ArrayList<uLines>();
	ArrayList<String> myWarnings = new ArrayList<String>();
	ArrayList<ugRegs> myGlobalVariables = new ArrayList<ugRegs>();
	ArrayList<uxRegs> myComponentVariables = new ArrayList<uxRegs>();
	ArrayList<uxGroup> myComponentEntities = new ArrayList<uxGroup>();

	uFormpaint formPaint = null;

	uForm(ArrayList<ArrayList<String>> myValues){

		/* add the values to the variables */
		for (int i=0; i<myValues.size(); i++){

			if (myValues.get(i).get(0).equals("UTIMESTAMP")){
			} else if (myValues.get(i).get(0).equals("UCOMPSTAMP")){

			} else if (myValues.get(i).get(0).equals("ULABEL")){
				this.componentName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("FTYP")){
			} else if (myValues.get(i).get(0).equals("UVERS")){
			} else if (myValues.get(i).get(0).equals("UDESCR")){
			} else if (myValues.get(i).get(0).equals("FHEAD")){
			} else if (myValues.get(i).get(0).equals("VMAAT")){
			} else if (myValues.get(i).get(0).equals("HMAAT")){
			} else if (myValues.get(i).get(0).equals("UCOLOR")){
			} else if (myValues.get(i).get(0).equals("WVPOS")){
			} else if (myValues.get(i).get(0).equals("WHPOS")){
			} else if (myValues.get(i).get(0).equals("WVSIZ")){
			} else if (myValues.get(i).get(0).equals("WHSIZ")){
			} else if (myValues.get(i).get(0).equals("CLRSCRN")){
			} else if (myValues.get(i).get(0).equals("UBORDER")){
			} else if (myValues.get(i).get(0).equals("RIBIN")){
			} else if (myValues.get(i).get(0).equals("RIBOT")){
			} else if (myValues.get(i).get(0).equals("MOVABLE")){
			} else if (myValues.get(i).get(0).equals("VIDEOINVERSE")){
			} else if (myValues.get(i).get(0).equals("VIDEOBRIGHT")){
			} else if (myValues.get(i).get(0).equals("VIDEOUNLINE")){
			} else if (myValues.get(i).get(0).equals("VIDEOBLINK")){
			} else if (myValues.get(i).get(0).equals("UPANEL")){
			} else if (myValues.get(i).get(0).equals("POSPANEL")){
			} else if (myValues.get(i).get(0).equals("UPULL")){
			} else if (myValues.get(i).get(0).equals("HIDESTACK")){
			} else if (myValues.get(i).get(0).equals("TEMPLATENAME")){
			} else if (myValues.get(i).get(0).equals("UINHERIT")){
			} else if (myValues.get(i).get(0).equals("LIBRAR")){
			} else if (myValues.get(i).get(0).equals("UTRANSACT")){
			} else if (myValues.get(i).get(0).equals("CONSTRAINTS")){
			} else if (myValues.get(i).get(0).equals("USTATEMANAGEDBY")){

			} else if (myValues.get(i).get(0).equals("INIT")
				|| myValues.get(i).get(0).equals("CLEAR")
				|| myValues.get(i).get(0).equals("RETRIEVE")
				|| myValues.get(i).get(0).equals("RECORD")
				|| myValues.get(i).get(0).equals("STORE")
				|| myValues.get(i).get(0).equals("DELET")
				|| myValues.get(i).get(0).equals("ACCEPT")
				|| myValues.get(i).get(0).equals("QUIT")
				|| myValues.get(i).get(0).equals("MENU")
				|| myValues.get(i).get(0).equals("INTKEY")
				|| myValues.get(i).get(0).equals("SPRINT")
				|| myValues.get(i).get(0).equals("EPRINT")
				|| myValues.get(i).get(0).equals("ASYNC")
				|| myValues.get(i).get(0).equals("GENERAL")
				|| myValues.get(i).get(0).equals("EPRINT")
				|| myValues.get(i).get(0).equals("ASYNC")
				|| myValues.get(i).get(0).equals("FRLF")
				|| myValues.get(i).get(0).equals("FRGF")
				|| myValues.get(i).get(0).equals("SFUNC")
				|| myValues.get(i).get(0).equals("GETSTATE")
				|| myValues.get(i).get(0).equals("SETSTATE")
				|| myValues.get(i).get(0).equals("TPLACTUAL")
				|| myValues.get(i).get(0).equals("TPLACTUAL2")){

				uLines myTrigger = new uLines(myValues.get(i).get(1));
				myTrigger.setTriggerName(myValues.get(i).get(0));
				this.myTriggers.add(myTrigger);

			} else if (myValues.get(i).get(0).equals("FORMPIC")){
				formPaint = new uFormpaint(myValues.get(i).get(1));

			} else if (myValues.get(i).get(0).equals("HEADER")){
			} else if (myValues.get(i).get(0).equals("LISTING")){
			} else if (myValues.get(i).get(0).equals("PERF")){
			} else if (myValues.get(i).get(0).equals("PROTO")){
			} else if (myValues.get(i).get(0).equals("TITLE")){
			} else if (myValues.get(i).get(0).equals("WINPROP")){
			} else if (myValues.get(i).get(0).equals("FRLF")){
			} else if (myValues.get(i).get(0).equals("FRGF")){
			} else if (myValues.get(i).get(0).equals("SFUNC")){
			} else if (myValues.get(i).get(0).equals("HTMLPROP")){
			} else if (myValues.get(i).get(0).equals("USCONTAINED")){
			} else if (myValues.get(i).get(0).equals("UEXECDEF")){
			} else if (myValues.get(i).get(0).equals("UPOPUP")){
			} else if (myValues.get(i).get(0).equals("UML_DATA")){
			} else if (myValues.get(i).get(0).equals("HTML_CMPPROP")){
			} else if (myValues.get(i).get(0).equals("HTML_FORMAT")){
			} else if (myValues.get(i).get(0).equals("HTML_STYLES")){
			} else if (myValues.get(i).get(0).equals("HTML_HOOK_H")){
			} else if (myValues.get(i).get(0).equals("HTML_HOOK_B")){
			} else if (myValues.get(i).get(0).equals("HTML_HOOK_E")){
			} else if (myValues.get(i).get(0).equals("HTML_BODYHOOK")){
			} else if (myValues.get(i).get(0).equals("HTML_CMPCLASS")){
			} else if (myValues.get(i).get(0).equals("GETSTATE")){
			} else if (myValues.get(i).get(0).equals("SETSTATE")){
			} else if (myValues.get(i).get(0).equals("UNOSTATE")){
			} else if (myValues.get(i).get(0).equals("CMP_EXT")){

			} else {
				System.out.println("UFORM: No value found for key " + myValues.get(i).get(0));
			}
		}

		this.saveObject();
	}

	public void saveObject() {
		try {
			this.saveObject(this.myPath, this.componentName);
		} catch (IOException e) {
			e.printStackTrace();
		}//endtry

	}

	public void generateProclisting() throws IOException{
		/* this function generates a uniface like proc listing */

		int counter = 0;
		BufferedWriter myOutput = new BufferedWriter(new FileWriter(this.outPath + this.componentName +".pro"));


		/* 1. write the Globalvariables */
		myOutput.write("*** Global Variables ***");
		myOutput.newLine();
		for (int i=0; i < this.myGlobalVariables.size(); i++){
			ugRegs myReg = this.myGlobalVariables.get(i);
			myOutput.write(myReg.getVariablename() + " : " + myReg.getVariabletype());
			myOutput.newLine();
		}


		/* 2. Write the componentvariables */
		myOutput.write("*** Component Variables ***");
		myOutput.newLine();
		for (int i=0; i < this.myComponentVariables.size(); i++){
			uxRegs myReg = this.myComponentVariables.get(i);
			myOutput.write(myReg.getVariablename() + " : " + myReg.getVariabletype());
			myOutput.newLine();
		}


		/* first write the component warnings */
		for (int i = 0; i< this.myWarnings.size(); i++){
			myOutput.write(this.myWarnings.get(i));
			myOutput.newLine();
		}


		/* then the component triggers */
		for (int i = 0; i< this.myTriggers.size(); i++){

			myOutput.write("Trigger <" + myTriggers.get(i).getTriggerName() + "> from Form: " + this.getComponentName());
			myOutput.newLine();

			ArrayList<uCodeLine> myCode = myTriggers.get(i).getCodelines();

			for (int j = 0; j < myCode.size(); j++){

				/* print the line number followed by the code line */
				myOutput.write(myCode.get(j).getLinenumberInString());
				myOutput.write(" : ");
				myOutput.write(myCode.get(j).getClassline());

				myOutput.newLine();

				/* on a new line print the comments, if they are not empty */
				ArrayList<String> myRemarks = myCode.get(j).getWarnings();
				for (int k = 0; k < myRemarks.size(); k++){
					myOutput.write(myRemarks.get(k));
					myOutput.newLine();
				}
			}
			myOutput.write("************************************************");
			myOutput.newLine();
		}

		/* then the entity triggers */
		for (int i = 0; i < this.myComponentEntities.size(); i++){
			try{
				/* generate the proc listing */
				//this.myComponentEntities.get(0).generateProclisting(myOutput);
				this.myComponentEntities.get(i).generateProclisting(myOutput);

			} catch (IOException e){
				/* catch the other errors */
				e.printStackTrace();
			}
		}
		myOutput.close();
	}

	public static void lookForDuplicates(){
		/*init*/
		ArrayList<uCodeLine> mainSnippet = new ArrayList<uCodeLine>();
		ArrayList<uCodeLine> snippet = new ArrayList<uCodeLine>();
		boolean isDuplicate = false;
		boolean isTrue = false;

		/* get all the folder content */
		String[] allForms			= getFolderContent(uForm.myPath, null);

		/* while their are still forms in the directory */
		if (allForms != null) {

			/* step through all the forms */
			for (int i = 0; i < allForms.length; i++){
				/* load the uForm object and get the componentname */
				uForm form = (uForm)loadObject(uForm.myPath, allForms[i]);
				String componentName = form.getComponentName();
				System.out.println("Component: " + componentName);

				timeObject myTime = new timeObject();
				new printLogging("Component: " + componentName + "    " + myTime.getTime(), "tracing");

				/* step through the form triggers */
				for (int m = 0; m < form.getTriggers().size(); m++){
					/* get the triggername */
					String triggerName = form.getTriggers().get(m).getTriggerName();

					new printLogging("**************************");
					new printLogging("*** " + componentName + "." + triggerName + " ***");
					new printLogging("**************************");

					/* load the codeobject*/
					uLines codeObject = form.getTriggers().get(m);
					ArrayList<uCodeLine> actualCode = codeObject.getActualCode();

					new printLogging("*** actual code ***");
					new printLogging(actualCode);

					/* hack the code into snippets */
					ArrayList<codeSnippet> allSnippets = codeSnippet.createSnippets(actualCode, "UFORM", componentName, triggerName, 0);

					/* step through the snippets, looking for duplicates */
					for (int n = 0; n < allSnippets.size(); n++){
						//new printLogging("Continuing snippet " + n + " of " + allSnippets.size() + " in " + componentName + "." + triggerName, "tracing");
						if (allSnippets.get(n).lookForDuplicates()){
							//new printLogging("-> duplicate snippet found!", "tracing");
						}
					}
				}
				form.saveObject();
			}
		}
	}//lookForDuplicates


	/** Retrieve all form objects in an arraylist */
	public static ArrayList<uForm> getAllFormsGoed(){

		/* init */
		ArrayList<uForm> allForms = new ArrayList<uForm>();

		/* 1. Collect all files that contain triggers */
		String[] myFiles = getFolderContent(uForm.myPath, null);

		if (myFiles != null) {
			for (int i=0; i< myFiles.length; i++) {

				uForm curForm = null;
				curForm = (uForm)loadObject(uForm.myPath, myFiles[i]);
				allForms.add(curForm);
			}//endfor
		}//endif
		return allForms;
	}


	public static ArrayList<uForm> getAllForms(){

		/* init */
		ArrayList<uForm> allForms = new ArrayList<uForm>();
		ArrayList<Object> allObjects = generalFunctions.getAllObjects(myPath);

		for (int i = 0; i < allObjects.size(); i++){
			allForms.add((uForm) allObjects.get(i));
		}

		return allForms;
	}


	public static void processAllComponents(){
		/* this function processes all the compnents.
		looking for coding violations and other apprehensive stuff */

		/* Collect all files that are forms */
		System.out.println("Retrieving all forms");

		String[] allFiles = getFolderContent(uForm.myPath, null);

		if (allFiles != null) {

			for (int i=0; i< allFiles.length; i++) {

				uForm myForm = null;
				myForm = (uForm)loadObject(uForm.myPath, allFiles[i]);

				//System.out.println("UFORM: Processing " + myForm.getComponentName());

				/* checking the relations */
				//System.out.println("UFORM: checking relations!");
				for (int j = 0; j < myForm.myComponentEntities.size() ; j++){
					uxGroup myEntity = myForm.myComponentEntities.get(j);
					myEntity.checkRelations();
				}

				/* save the object */
				myForm.saveObject();

			}
		}
	}


	public static void fillAllComponents(){
		/* this function fills the component with all required information
			- painted entities
			- local variables
			- global variables
		*/

		// dvo 16-12-2009
		return;

		/* Collect all files that contain an schema */
//		String[] myFiles = getFolderContent(myPath, null);

//		if (myFiles != null) {
//			for (int i=0; i< myFiles.length; i++) {
//
//				uForm myForm = null;
//				myForm = (uForm)loadObject(myPath, myFiles[i]);
//
//
//				System.out.println("Filling " + myForm.getComponentName());
//
//				myForm.setComponentEntities();
//				myForm.setComponentVariables();
//				myForm.setGlobalVariables();
//
//				myForm.checkFormpaint();
//
//				/* save the object */
//
//				//if(myForm.myComponentEntities.isEmpty()
//				//	|| myForm.myComponentEntities.get(0).getEntityDefinition() == null){
//				//	System.out.println("No entities found on " + myForm.getComponentName());
//				//} else {
//				//	//System.out.println(myForm.myComponentEntities.get(0).getEntityDefinition());
//				//}
//				myForm.saveObject();
//
//			}//endfor
//		}//endif
	}

	public void setGlobalVariables(){
		/* collect the global variables that belong to the component */

		/* init */
		this.myGlobalVariables = new ArrayList<ugRegs>();

		/* Collect all files that contain global variabels  */
		String[] myFiles = getFolderContent(ugRegs.myPath, null);

		if (myFiles != null) {
			for (int i=0; i< myFiles.length; i++) {

				ugRegs myRegister = null;
				myRegister = (ugRegs)loadObject(ugRegs.myPath, myFiles[i]);


				/* all global variables must be loaded, after scannig the
				code some can be deleted */
				this.myGlobalVariables.add(myRegister);
			}//endfor
			//System.out.println("Added " + this.myGlobalVariables.size() + " registers!");
		}//endif
	}

	public void setComponentVariables(){
		/* collect the component variables that belong to the component */

		/* init */
		this.myComponentVariables = new ArrayList<uxRegs>();

		/* Collect all files that contain global variabels  */
		File dir = new File(uxRegs.myPath);
		fileExtensionFilterStarts filter = new fileExtensionFilterStarts(this.componentName);
		String[] myFiles = dir.list(filter);

		if (myFiles != null) {
			for (int i=0; i< myFiles.length; i++) {

				uxRegs myRegister = null;
				myRegister = (uxRegs)loadObject(uxRegs.myPath, myFiles[i]);


				/* if the componentname is the same add the variable */
				if(this.componentName.equals(myRegister.getComponentName())){
					this.myComponentVariables.add(myRegister);
				}
			}//endfor
			//System.out.println("Added " + this.myComponentVariables.size() + " registers!");
		}//endif
	}

	public void setComponentEntities(){
		/* collect the painted entities that belong to the component */

		/* init */
		this.myComponentEntities = new ArrayList<uxGroup>();

		/* Collect all files that contain component entities */
		File dir = new File(uxGroup.myPath);
		fileExtensionFilterStarts filter = new fileExtensionFilterStarts(this.componentName);
		String[] myFiles = dir.list(filter);

		//System.out.println("UFORM: myFiles.length " + myFiles.length);

		if (myFiles != null) {
			for (int i=0; i< myFiles.length; i++) {

				uxGroup myEntity = null;
				myEntity = (uxGroup)loadObject(uxGroup.myPath, myFiles[i]);

				//System.out.println(myEntity.getEntityName() + " versie " + myEntity.versionNumber );
				/* if the componentname is the same add the entity */
				if(this.componentName.equals(myEntity.getComponentName())){
					this.myComponentEntities.add(myEntity);
				}
			}//endfor
			//System.out.println("Added " + this.myComponentEntities.size() + " entities!");
		}//endif
	}


	public void checkComponentVariables(){
		/* this function checks the usage of the component variables */

		/* step through the componentvariables */
		for (int i = 0; i< this.myComponentVariables.size(); i++){
			/* look for the variable*/
			String myVar = this.myComponentVariables.get(i).getVariablename();
			if(!this.checkComponentVariable(myVar)){
				/* variable is never used */
				this.myWarnings.add("Warning (5001): Component variable " + myVar + " is never used in proc.");
			}
		}
	}


	private boolean checkComponentVariable(String myVar){
		/* this function checks the usage of a component variable */

		/* init */
		int at = 0;

		/* step through the component triggers */
		for (int j=0; j< this.myTriggers.size(); j++){

			/* step through the individual lines in the comp triggers */
			ArrayList<uCodeLine> myLines = this.myTriggers.get(j).getCodelines();
			for (int i = 0; i < myLines.size(); i++){

				if (myLines.get(i).getProcessedLine().indexOf(myVar, 0) >=0 ){
					at++;

					/* for speed purposes, if we found one we leave the loop */
					if (at >= 1){
						return true;
					}//endif
				}//endif
			}//endfor
		}//endfor

		/* step through the entities and their triggers */
		for (int i=0; i< this.myComponentEntities.size(); i++){
			uxGroup myEntity = this.myComponentEntities.get(i);

			/* process the triggers of the entity */
			for (int j=0; j< myEntity.getTriggers().size(); j++){

				ArrayList<uCodeLine> myLines = myEntity.getTriggers().get(j).getCodelines();

				/* step through the individual lines in the comp triggers */
				for (int k = 0; k < myLines.size(); k++){
					/* if the variable is found add 1 to the total */
					if (myLines.get(k).getProcessedLine().indexOf(myVar, 0) >=0 ){
						at++;

						/* for speed purposes, if we found one we leave the loop */
						if (at >= 1){
							return true;
						}//endif
					}//endif
				}//endfor
			}//endfor


			/* step through the fields and their triggers */
			for (int l=0; l< myEntity.getFields().size(); l++){

				/* get the entity field */
				uxField myField = myEntity.getFields().get(l);

				/* step through the triggers ofthe field */
				for (int m=0; m < myField.getTriggers().size(); m++){

					ArrayList<uCodeLine> myLines = myField.getTriggers().get(m).getCodelines();

					/* step through the individual lines in the comp triggers */
					for (int n = 0; n < myLines.size(); n++){
						/* if the variable is found add 1 to the total */
						if (myLines.get(n).getProcessedLine().indexOf(myVar, 0) >=0 ){
							at++;

							/* for speed purposes, if we found one we leave the loop */
							if (at >= 1){
								return true;
							}//endif
						}//endif
					}//endfor
				}//endfor
			}//endfor
		}//endfor

		/* finally return the status */
		return false;
	}


	public void checkComponentEntities(){
		/* this function checks several things in the painted entities */

		boolean isClean = true;

		/* step through the entities and their triggers */
		for (int i=0; i< this.myComponentEntities.size(); i++){
			uxGroup myEntity = this.myComponentEntities.get(i);

			/* check for clean entity */
			if (isClean
				&& myEntity.getEntityName().substring(0,1).equals("?")){
				this.myWarnings.add("Warning (1100): Component needs te be cleaned!");
				isClean = false;
			}


			/* step through the fields and their triggers */
			for (int l=0; l< myEntity.getFields().size(); l++){

				uxField myField = myEntity.getFields().get(l);

				/* check for clean field */
				if (isClean
					&& myField.getFieldName().substring(0,1).equals("?")){
					this.myWarnings.add("Warning (1100): Component needs te be cleaned!");
					isClean = false;
				}

			}//endfor
		}//endfor
	}

	public void checkFormpaint(){


		if (!this.myComponentEntities.isEmpty()){

			/* this function processes the formpaint information */
			this.formPaint.processPaintinfo(this.myComponentEntities);

			/* add the inner painted entities */
			this.formPaint.getChildren(this.myComponentEntities);

			/* check if the information is acurate */
			this.formPaint.checkPaintinfo(this.myComponentEntities);

			/* save the objects */
			//for (int i = 0; i < this.myComponentEntities.size(); i++){
			//	this.myComponentEntities.get(i).saveObject();
			//}

		} else {
			this.myWarnings.add("Warning (6200): No entity painted on component");
		}
	}



	/* getters and setters */
	public String getComponentName(){
		return this.componentName;
	}

	public boolean getProcessed(){
		return this.isProcessed;
	}

	public void setProcessed(boolean status){
		this.isProcessed = status;
	}
	/* triggers */
	public ArrayList<uLines> getTriggers(){
		return this.myTriggers;
	}

	/** match the component triggername with the trigger in the snippet */
	public int getTriggerPointer(String objectName, String triggerName){

		if (this.getComponentName().equals(objectName)){
			ArrayList<uLines> triggers = this.getTriggers();
			for (int i = 0; i < triggers.size(); i++){
				if(triggers.get(i).getTriggerName().equals(triggerName)){
					return i;
				}
			}
		}
		return 0;
	}
}