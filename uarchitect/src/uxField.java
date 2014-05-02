import java.util.ArrayList;

/* Error logging */
import java.io.IOException;

/* java persistance */
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/* java output */
import java.io.BufferedWriter;

public class uxField extends generalFunctions implements Serializable{

	String componentName = new String();
	//String libraryName = new String();
	String fieldName = new String();
	String entityName = new String();
	String modelName = new String();

	ArrayList<uLines> myTriggers = new ArrayList<uLines>();

	private int x = 0;
	private int y = 0;
	private int totalWidth = 0;
	private int totalHeight = 0;

	static String myPath = new String("C:/dvo1/OUT/DICT/UXFIELD/");

	uxField(ArrayList<ArrayList<String>> myValues){

		/* add the values to the variables */
		for (int i=0; i<myValues.size(); i++){
			if (myValues.get(i).get(0).equals("UTIMESTAMP")){
			} else if (myValues.get(i).get(0).equals("ULABEL")){
				this.fieldName = myValues.get(i).get(1);

			} else if (myValues.get(i).get(0).equals("GRP")){
				this.entityName = myValues.get(i).get(1);

			} else if (myValues.get(i).get(0).equals("UBASE")){
				//this.libraryName = myValues.get(i).get(1);
				this.modelName = myValues.get(i).get(1);


			} else if (myValues.get(i).get(0).equals("UFORM")){
				this.componentName = myValues.get(i).get(1);

			} else if (myValues.get(i).get(0).equals("U_TLAB")){
			} else if (myValues.get(i).get(0).equals("UVERS")){
			} else if (myValues.get(i).get(0).equals("UDESCR")){
			} else if (myValues.get(i).get(0).equals("U_DTYP")){
			} else if (myValues.get(i).get(0).equals("U_INDB")){
			} else if (myValues.get(i).get(0).equals("TPLLAY")){
			} else if (myValues.get(i).get(0).equals("LAYOUTMOD")){
			} else if (myValues.get(i).get(0).equals("TPLSYN")){
			} else if (myValues.get(i).get(0).equals("SYNTAXMOD")){
			} else if (myValues.get(i).get(0).equals("TPLINTF")){
			} else if (myValues.get(i).get(0).equals("INTERFACEMOD")){
			} else if (myValues.get(i).get(0).equals("TABSTOP")){
			} else if (myValues.get(i).get(0).equals("WIDGETTYPE")){
			} else if (myValues.get(i).get(0).equals("TEMPLATENAME")){
			} else if (myValues.get(i).get(0).equals("UINHERIT")){
			} else if (myValues.get(i).get(0).equals("WIDGETCREATE")){
			} else if (myValues.get(i).get(0).equals("VALLAB")){
			} else if (myValues.get(i).get(0).equals("INITVALUE")){
			} else if (myValues.get(i).get(0).equals("TPLACTUAL")){

			} else if (myValues.get(i).get(0).equals("UPOPUP")){
			} else if (myValues.get(i).get(0).equals("UISOBJID")){
			} else if (myValues.get(i).get(0).equals("HTML_FLDPROP")){
			} else if (myValues.get(i).get(0).equals("HTML_CTRLTYPE")){
			} else if (myValues.get(i).get(0).equals("HTML_HOOK_B")){
			} else if (myValues.get(i).get(0).equals("HTML_HOOK_M")){
			} else if (myValues.get(i).get(0).equals("HTML_HOOK_E")){
			} else if (myValues.get(i).get(0).equals("HTML_LBLHK_B")){
			} else if (myValues.get(i).get(0).equals("HTML_LBLHK_E")){
			} else if (myValues.get(i).get(0).equals("HTML_FLDCLASS")){
			} else if (myValues.get(i).get(0).equals("HTML_LBLCLASS")){

			} else if (myValues.get(i).get(0).equals("DETAIL")
					|| myValues.get(i).get(0).equals("ERROR")
					|| myValues.get(i).get(0).equals("VLDF")
					|| myValues.get(i).get(0).equals("VCHANGED")
					|| myValues.get(i).get(0).equals("GETFOCUS")
					|| myValues.get(i).get(0).equals("GENERAL")
					|| myValues.get(i).get(0).equals("DOC")
					|| myValues.get(i).get(0).equals("MENU")

					|| myValues.get(i).get(0).equals("ENCRYPT")
					|| myValues.get(i).get(0).equals("DECRYPT")
					|| myValues.get(i).get(0).equals("NEXTFLD")
					|| myValues.get(i).get(0).equals("PREVFLD")
					|| myValues.get(i).get(0).equals("UFORMAT")
					|| myValues.get(i).get(0).equals("DEFORMAT")
					|| myValues.get(i).get(0).equals("STARTMOD")
					|| myValues.get(i).get(0).equals("ENDMOD")){


					uLines myTrigger = new uLines(myValues.get(i).get(1));
					myTrigger.setTriggerName(myValues.get(i).get(0));

					this.myTriggers.add(myTrigger);

			} else {
				System.out.println("UXFIELD: No value found for key " + myValues.get(i).get(0));
			}
		}

		this.saveObject();
	}


	public void saveObject(){
		//this.versionNumber++;

		try {
			this.saveObject(myPath, this.getFilename());
		} catch (IOException e) {
			e.printStackTrace();
		}//endtry
	}


	private String getFilename(){
		/* create a string of filenames and strip the non-authorized characters */

		String myFilename = new String(this.componentName + "_" + this.entityName + "_" + this.fieldName);

		/* replacing the question mark the \\ are the to escape the character */
		myFilename = myFilename.replaceAll("\\?", "");
		myFilename = myFilename.replaceAll("\\*", "");

		return myFilename;
	}


	public static ArrayList<uxField> getAllPaintedFields(){
		/* this function retrieves a list of components from the folder.
		Loads these components into an object, puts them in an arraylist and
		then returns that list. */

		/* init */
		ArrayList<uxField> myXFields = new ArrayList<uxField>();

		/* 1. Collect all files that contain triggers */
		String[] myFiles = getFolderContent(myPath, null);

		if (myFiles != null) {
			for (int i=0; i< myFiles.length; i++) {

				uxField myXField = null;
				myXField = (uxField)loadObject(myPath, myFiles[i]);
				myXFields.add(myXField);
			}//endfor
		}//endif
		return myXFields;
	}


	public void fillAllPaintedFields(ucField myField){

		/* transfers the trigger code of the field */

	}

	public void generateProclisting(BufferedWriter myOutput) throws IOException{
		/* write the field triggers to the file */

		System.out.println("UXFIELD: printing proc listing");
		for (int i = 0; i< this.myTriggers.size(); i++){

			myOutput.write("Trigger <" + myTriggers.get(i).getTriggerName() + "> from Field: " + this.getFieldName() + "." + this.getEntityName() + "." + this.getSchemaName());
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
	}


	public void setProperties(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		this.totalWidth = w;
		this.totalHeight = h;

//		System.out.println(this.getFieldName() +" x="+ x+" y="+ y+" w="+ totalWidth+" h="+ totalHeight);
	}


	public void replaceTriggers(ucField conceptual){
		/*remove the redundant triggers */

		if (conceptual != null){
			for (int i = 0;i < this.myTriggers.size(); i++ ){
				/* step through the conceptual triggers */
				for (int j = 0; j < conceptual.getTriggers().size(); j++ ){
					if(this.myTriggers.get(i).getTriggerName().equals(conceptual.getTriggers().get(j).getTriggerName())){
						/* remove the trigger */
						conceptual.getTriggers().remove(j);
						j--;
					}
				}
			}

			/* add the triggers */
			for (int j = 0; j < conceptual.getTriggers().size(); j++ ){
				this.setTrigger(conceptual.getTriggers().get(j));

			}
		} else {
			System.out.println("UXFIELD: Conceptual entity is empty!");
		}
	}


	public static void lookForDuplicates(){
		/*init*/
		ArrayList<uCodeLine> mainSnippet = new ArrayList<uCodeLine>();
		ArrayList<uCodeLine> snippet = new ArrayList<uCodeLine>();
		boolean isDuplicate = false;
		boolean isTrue = false;

		/* get all the folder content */
		String[] allFields = getFolderContent(uxField.myPath, null);

		/* while their are still forms in the directory */
		if (allFields != null) {

			/* step through all the forms */
			for (int i = 0; i < allFields.length; i++){
				/* load the uForm object */
				uxField field = (uxField)loadObject(uxField.myPath, allFields[i]);

				/* step through the form triggers */
				for (int m = 0; m < field.getTriggers().size(); m++){
					/* get the triggername */
					String entityName = field.getEntityName();
					String fieldName = field.getFieldName();
					String triggerName = field.getTriggers().get(m).getTriggerName();

					new printLogging("**************************");
					new printLogging("*** " + entityName + "." + fieldName + "." + triggerName + " ***");
					new printLogging("**************************");

					/* load the codeobject*/
					uLines codeObject = field.getTriggers().get(m);
					ArrayList<uCodeLine> actualCode = codeObject.getActualCode();

					new printLogging("*** actual code ***");
					new printLogging(actualCode);

					/* hack the code into snippets */
					ArrayList<codeSnippet> allSnippets = codeSnippet.createSnippets(actualCode, "UXFIELD", fieldName, triggerName, 0);

					/* step through the snippets, looking for duplicates */
					for (int n = 0; n < allSnippets.size(); n++){
						if (allSnippets.get(n).lookForDuplicates()){

						}
					}
				}
				field.saveObject();
			}
		}
	}//lookForDuplicates


	public void setTrigger(uLines myTrigger){

		//System.out.println("UXFIELD: Setting trigger: " + myTrigger.getTriggerName());
		/* trigger is a copy */
		myTrigger.setOriginal(false);
		/* add the trigger */
		this.myTriggers.add(myTrigger);
	}


	/** match the painted field triggername with the trigger in the snippet */
	public int getTriggerPointer(String objectName, String triggerName, String entityName){

		if (this.getEntityName().equals(entityName)
			&& this.getFieldName().equals(objectName)){
			ArrayList<uLines> triggers = this.getTriggers();
			for (int i = 0; i < triggers.size(); i++){
				if(triggers.get(i).getTriggerName().equals(triggerName)){
					return i;
				}
			}
		}
		return 0;
	}


	/* getters and setters */
	public ArrayList<uLines> getTriggers(){
		return this.myTriggers;
	}



	public String getEntityName(){
		return this.entityName;
	}
	public String getSchemaName(){
		return this.modelName;
	}
	public String getFieldName(){
		return this.fieldName;
	}

	public static String getPath(){
		return myPath;
	}
}


