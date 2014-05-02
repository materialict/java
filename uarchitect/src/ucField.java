import java.util.ArrayList;

/* file output */
import java.io.BufferedWriter;
import java.io.FileWriter;


/* Error logging */
import java.io.IOException;

/* java persistance */
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class ucField extends generalFunctions implements Serializable{

	String schemaName = new String();
	String fieldName = new String();
	String entityName = new String();

	String dataType = new String();
	String inDatabase = new String();
	String templateInterface = new String();
	String shortInterface = new String();
	String templateSyntax = new String();
	String shortSyntax = new String();
	String templateLayout = new String();
	String shortLayout = new String();


	ArrayList<uLines> myTriggers = new ArrayList<uLines>();

	static String myPath = new String("C:/DVO1/OUT/DICT/UCFIELD/");

	/** The number of times painted */
	private int atTimesPainted = 0;


	ucField(ArrayList<ArrayList<String>> myValues){

		/* add the values to the variables */
		for (int i=0; i<myValues.size(); i++){

			if (myValues.get(i).get(0).equals("UTIMESTAMP")){
			} else if (myValues.get(i).get(0).equals("U_STAT")){
			} else if (myValues.get(i).get(0).equals("U_FLAB")){
				this.fieldName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("U_VLAB")){
				this.schemaName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("U_TLAB")){
				this.entityName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("U_FSEQ")){
			} else if (myValues.get(i).get(0).equals("UVERS")){
			} else if (myValues.get(i).get(0).equals("UDESCR")){
			} else if (myValues.get(i).get(0).equals("U_LEV")){
			} else if (myValues.get(i).get(0).equals("U_DTYP")){
				this.dataType = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("U_INDB")){
				this.inDatabase = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("TPLINTF")){
				this.templateInterface = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("U_INTF")){
				this.shortInterface = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("TPLSYN")){
				this.templateSyntax = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("U_SYN")){
				this.shortSyntax = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("TPLLAY")){
				this.templateLayout = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("U_LAY")){
				this.shortLayout = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("WIDGETTYPE")){
			} else if (myValues.get(i).get(0).equals("TEMPLATENAME")){
			} else if (myValues.get(i).get(0).equals("UINHERIT")){
			} else if (myValues.get(i).get(0).equals("UDEFWIDTH")){
			} else if (myValues.get(i).get(0).equals("UDEFDEPTH")){
			} else if (myValues.get(i).get(0).equals("U_DESC")){
			} else if (myValues.get(i).get(0).equals("U_DOC")){

			} else if (myValues.get(i).get(0).equals("WIDGETCREATE")){
			} else if (myValues.get(i).get(0).equals("VALLAB")){
			} else if (myValues.get(i).get(0).equals("INITVALUE")){
			} else if (myValues.get(i).get(0).equals("ACTVALS")){
			} else if (myValues.get(i).get(0).equals("VLDF")){
			} else if (myValues.get(i).get(0).equals("UPOPUP")){
			} else if (myValues.get(i).get(0).equals("UISOBJID")){
			} else if (myValues.get(i).get(0).equals("UML_DATA")){
			} else if (myValues.get(i).get(0).equals("HTML_FLDPROP")){
			} else if (myValues.get(i).get(0).equals("HTML_CTRLTYPE")){
			} else if (myValues.get(i).get(0).equals("HTML_HOOK_B")){
			} else if (myValues.get(i).get(0).equals("HTML_HOOK_M")){
			} else if (myValues.get(i).get(0).equals("HTML_HOOK_E")){
			} else if (myValues.get(i).get(0).equals("HTML_LBLHK_B")){
			} else if (myValues.get(i).get(0).equals("HTML_LBLHK_E")){
			} else if (myValues.get(i).get(0).equals("HTML_FLDCLASS")){
			} else if (myValues.get(i).get(0).equals("HTML_LBLCLASS")){


			} else if (myValues.get(i).get(0).equals("P_HELP")
					|| myValues.get(i).get(0).equals("P_DET")
					|| myValues.get(i).get(0).equals("P_SMOD")
					|| myValues.get(i).get(0).equals("P_EMOD")
					|| myValues.get(i).get(0).equals("P_NFLD")
					|| myValues.get(i).get(0).equals("P_PFLD")
					|| myValues.get(i).get(0).equals("P_ENCR")
					|| myValues.get(i).get(0).equals("P_DECR")
					|| myValues.get(i).get(0).equals("P_MENU")
					|| myValues.get(i).get(0).equals("P_ERROR")
					|| myValues.get(i).get(0).equals("P_GENERAL")
					|| myValues.get(i).get(0).equals("P_FORMAT")
					|| myValues.get(i).get(0).equals("P_DEFORMAT")
					|| myValues.get(i).get(0).equals("P_GETFOCUS")
					|| myValues.get(i).get(0).equals("P_VCHANGED")){

					uLines myTrigger = new uLines(myValues.get(i).get(1));
					myTrigger.setTriggerName(myValues.get(i).get(0));

					this.myTriggers.add(myTrigger);

			/* uniface 8 */
			} else if (myValues.get(i).get(0).equals("UCINTERFACE")){
			} else if (myValues.get(i).get(0).equals("UHINTERFACE")){


			} else {
				System.out.println("UCFIELD: No value found for key " + myValues.get(i).get(0));
			}
		}

		this.saveObject();
	}


	public void saveObject(){
		try {
			this.saveObject(myPath, this.getFilename());
		} catch (IOException e) {
			e.printStackTrace();
		}//endtry
	}


	public static void listAllFields(){
		/*init*/
		String outputFile = new String("allfields.txt");
		new printLogging("Model	Entity	Field	Datatype	In Db	Interface	Syntax	Layout", outputFile);

		/* get all the folder content */
		String[] allFields = getFolderContent(ucField.myPath, null);
		/* while their are still forms in the directory */
		if (allFields != null) {

			/* step through all the forms */
			for (int i = 0; i < allFields.length; i++){
				/* load the uForm object */
				ucField field = (ucField)loadObject(ucField.myPath, allFields[i]);

				String schemaName = field.getSchemaName();
				String entityName = field.getEntityName();
				String fieldName = field.getFieldName();
				String dataType = field.getDataType();
				String inDatabase = field.getInDatabase();
				String typeInterface = field.getInterface();
				String typeSyntax = field.getSyntax();
				String typeLayout = field.getLayout();

				String message = new String(schemaName + "	" + entityName + "	" + fieldName + "	" + dataType + "	" + inDatabase + "	" + typeInterface + "	" + typeSyntax + "	" + typeLayout );
				new printLogging(message, outputFile);
			}
		}


	}

	public static void lookForDuplicates(){
		/*init*/
		ArrayList<uCodeLine> mainSnippet = new ArrayList<uCodeLine>();
		ArrayList<uCodeLine> snippet = new ArrayList<uCodeLine>();
		boolean isDuplicate = false;
		boolean isTrue = false;

		/* get all the folder content */
		String[] allFields = getFolderContent(ucField.myPath, null);

		/* while their are still forms in the directory */
		if (allFields != null) {

			/* step through all the forms */
			for (int i = 0; i < allFields.length; i++){
				/* load the uForm object */
				ucField field = (ucField)loadObject(ucField.myPath, allFields[i]);

				/* step through the form triggers */
				for (int m = 0; m < field.getTriggers().size(); m++){
					/* get the triggername */
					String entityName = field.getEntityName();
					String fieldName = field.getFieldName();
					String triggerName = field.getTriggers().get(m).getTriggerName();

					new printLogging("**************************");
					new printLogging("*** " + entityName + "." + fieldName + "." + triggerName + " ***");
					new printLogging("**************************");
					System.out.println("Processing: " + entityName + "." + fieldName + "." + triggerName );

					/* load the codeobject*/
					uLines codeObject = field.getTriggers().get(m);
					ArrayList<uCodeLine> actualCode = codeObject.getActualCode();

					new printLogging("*** actual code ***");
					new printLogging(actualCode);

					/* hack the code into snippets */
					ArrayList<codeSnippet> allSnippets = codeSnippet.createSnippets(actualCode, "UCFIELD", fieldName, triggerName, 0);

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



	/** match the conceptual field triggername with the trigger in the snippet */
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


	private String getFilename(){
		/* create a string of filenames and strip the non-authorized characters */

		String myFilename = new String(this.entityName +"_"+ this.fieldName);

		/* replacing the question mark the \\ are the to escape the character */
		myFilename = myFilename.replaceAll("\\?", "");

		return myFilename;
	}

	/** show the entities end the number of times they are painted */
	public static void countEntities() throws IOException{
		/* init */
		String[] allFiles = getFolderContent(ucField.myPath, null);
		BufferedWriter outputFile = new BufferedWriter(new FileWriter("C:/dvo1/painted_fields.txt"));


		/* if the list is not empty */
		if (allFiles != null) {
			/* step through the files and start matching */
			for (int i=0; i< allFiles.length; i++) {
				ucField field = null;
				field = (ucField)loadObject(ucField.myPath, allFiles[i]);

				outputFile.write(field.getSchemaName() +"."+ field.getEntityName() + "." + field.getFieldName() + " painted " + field.atTimesPainted + " times!");

			}
		}
		outputFile.close();

	}



	/* getters and setters */
	public ArrayList<uLines> getTriggers(){
		return this.myTriggers;
	}
	public String getSchemaName(){
		return this.schemaName;
	}
	public String getEntityName(){
		return this.entityName;
	}
	public String getFieldName(){
		return this.fieldName;
	}
	public static String getPath(){
		return myPath;
	}

	public String getDataType(){
		return this.dataType;
	}
	public String getInDatabase(){
		return this.inDatabase;
	}

	public String getInterface(){
		if (this.shortInterface.isEmpty()){
			return this.templateInterface;
		} else {
			return this.shortInterface;
		}
	}

	public String getSyntax(){
		if (this.shortSyntax.isEmpty()){
			return this.templateSyntax;
		} else {
			return this.shortSyntax;
		}
	}

	public String getLayout(){
		if (this.shortLayout.isEmpty()){
			return this.templateLayout;
		} else {
			return this.shortLayout;
		}
	}

	/** add 1 to the times painted */
	public void setTimesPainted(){
		this.atTimesPainted++;
	}
}


