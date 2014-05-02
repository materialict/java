import java.util.ArrayList;

/* file output */
import java.io.BufferedWriter;
import java.io.FileWriter;

/* Error logging */
import java.io.IOException;
/* java file */
import java.io.File;

/* java persistance */
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class ucGroup extends generalFunctions implements Serializable{

	String entityName = new String();
	String schemaName = new String();

	private ArrayList<uLines> myTriggers = new ArrayList<uLines>();
	private ArrayList<ucField> myFields = new ArrayList<ucField>();
	private ArrayList<ucKey> myKeys = new ArrayList<ucKey>();
	private ArrayList<ucRelsh> myRelations = new ArrayList<ucRelsh>();
	static String myPath = new String("C:/dvo1/OUT/DICT/UCGROUP/");


	/** The number of times painted */
	private int atTimesPainted = 0;


	/* empty constructor */
	ucGroup(){}


	ucGroup(ArrayList<ArrayList<String>> myValues){


		/* add the values to the variables */
		for (int i=0; i<myValues.size(); i++){
			if (myValues.get(i).get(0).equals("UTIMESTAMP")){
			} else if (myValues.get(i).get(0).equals("U_STAT")){
			} else if (myValues.get(i).get(0).equals("U_GLAB")){
				this.entityName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("U_VLAB")){
				this.schemaName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("U_TLAB")){
			} else if (myValues.get(i).get(0).equals("UVERS")){
			} else if (myValues.get(i).get(0).equals("UDESCR")){
			} else if (myValues.get(i).get(0).equals("U_BORD")){
			} else if (myValues.get(i).get(0).equals("U_INDB")){
			} else if (myValues.get(i).get(0).equals("U_DBMS")){
			} else if (myValues.get(i).get(0).equals("U_UPD")){
			} else if (myValues.get(i).get(0).equals("U_MINL")){
			} else if (myValues.get(i).get(0).equals("U_MAXL")){
			} else if (myValues.get(i).get(0).equals("U_MINR")){
			} else if (myValues.get(i).get(0).equals("U_MAXR")){
			} else if (myValues.get(i).get(0).equals("TPLGINTF")){
			} else if (myValues.get(i).get(0).equals("U_INTF")){
			} else if (myValues.get(i).get(0).equals("TEMPLATENAME")){
			} else if (myValues.get(i).get(0).equals("UINHERIT")){
			} else if (myValues.get(i).get(0).equals("TPLACTUAL")){
			} else if (myValues.get(i).get(0).equals("U_DOC")){
			} else if (myValues.get(i).get(0).equals("UPOPUP")){
			} else if (myValues.get(i).get(0).equals("UISBCLASS")){
			} else if (myValues.get(i).get(0).equals("UISASSOC")){
			} else if (myValues.get(i).get(0).equals("UML_DATA")){
			} else if (myValues.get(i).get(0).equals("U_OBJSVC")){
			} else if (myValues.get(i).get(0).equals("U_SVCUSE")){
			} else if (myValues.get(i).get(0).equals("HTML_ENTPROP")){
			} else if (myValues.get(i).get(0).equals("HTML_TABTYPE")){
			} else if (myValues.get(i).get(0).equals("HTML_ENTHK_B")){
			} else if (myValues.get(i).get(0).equals("HTML_ENTHK_E")){
			} else if (myValues.get(i).get(0).equals("HTML_OCCHK_B")){
			} else if (myValues.get(i).get(0).equals("HTML_OCCHK_E")){
			} else if (myValues.get(i).get(0).equals("HTML_ENTCLASS")){

			} else if (myValues.get(i).get(0).equals("P_READ")
				|| myValues.get(i).get(0).equals("P_WRIT")
				|| myValues.get(i).get(0).equals("P_DEL")
				|| myValues.get(i).get(0).equals("P_LOCK")
				|| myValues.get(i).get(0).equals("P_HELP")
				|| myValues.get(i).get(0).equals("P_OREM")
				|| myValues.get(i).get(0).equals("P_ODEF")
				|| myValues.get(i).get(0).equals("P_WUP")
				|| myValues.get(i).get(0).equals("P_DUP")
				|| myValues.get(i).get(0).equals("P_DET")
				|| myValues.get(i).get(0).equals("P_OCUR")
				|| myValues.get(i).get(0).equals("P_OMOD")
				|| myValues.get(i).get(0).equals("P_MENU")
				|| myValues.get(i).get(0).equals("P_FNDOCC")
				|| myValues.get(i).get(0).equals("P_ERROR")
				|| myValues.get(i).get(0).equals("P_STARTMOD")
				|| myValues.get(i).get(0).equals("P_GENERAL")
				|| myValues.get(i).get(0).equals("VLDE")
				|| myValues.get(i).get(0).equals("VLDK")){



					uLines myTrigger = new uLines(myValues.get(i).get(1));
					myTrigger.setTriggerName(myValues.get(i).get(0));

					this.myTriggers.add(myTrigger);

			/* uniface 8 */
			} else if (myValues.get(i).get(0).equals("UABBR")){

			} else if (myValues.get(i).get(0).equals("UEOOPERS")){
			} else if (myValues.get(i).get(0).equals("UEOINTERFACE")){

			} else if (myValues.get(i).get(0).equals("UECOPERS")){
			} else if (myValues.get(i).get(0).equals("UECINTERFACE")){

			} else if (myValues.get(i).get(0).equals("UHINTERFACE")){

			} else if (myValues.get(i).get(0).equals("UPARENT")){
			} else if (myValues.get(i).get(0).equals("UTARGET")){

			} else {
				System.out.println("UCGROUP: No value found for key " + myValues.get(i).get(0));
			}//endif
		}//endfor

		this.saveObject();
	}

	public void saveObject(){
		try {
			this.saveObject(myPath, this.getFilename(this.schemaName, this.entityName));
		} catch (IOException e) {
			e.printStackTrace();
		}//endtry
	}


	/** create a filename for the object */
	private String getFilename(String schemaName, String entityName){
		/* create a string of filenames and strip the non-authorized characters */

		String myFilename = new String(schemaName + "_" + entityName);

		/* replacing the question mark the \\ are the to escape the character */
		myFilename = myFilename.replaceAll("\\?", "");

		return myFilename;
	}


	/** return a ucGroup object
	 *
	 */
	public ucGroup getEntity(String schemaName, String entityName){
		ArrayList<ucGroup> myList = this.getEntities(schemaName, entityName);
		if (!myList.isEmpty()){
			return myList.get(0);
		} else if (myList.size() > 1){
			System.out.println("UCGROUP: more than 1 entity found!");
		}
		return null;
	}

	public ArrayList<ucGroup> getEntities(){
		return this.getEntities(null, null);
	}

	public ArrayList<ucGroup> getEntities(String schemaName, String entityName){
		/* create an array list of all conceptual entities */
		ArrayList<ucGroup> myList = new ArrayList<ucGroup>();

		/* init */
		String fileName = null;
		String[] myFiles = null;

		/* if the schema and entity are passed then use them to build a string */
		if (schemaName != null && entityName != null){
			//System.out.println("getEntities schema and entity are filled ");

			fileName = this.getFilename(schemaName, entityName);

			/* Collect all files that contain an entity */
			File f = new File(myPath + fileName + ".dvo");
			if (!f.exists()){
				System.out.println("getEntities file " + fileName + ".dvo DOESNOT exist !!!");
				//return null;
			} else {
				/* it does exist so load it  */
				ucGroup myEntity = null;
				myEntity = (ucGroup)loadObject(myPath, fileName + ".dvo");
				myList.add(myEntity);
			}

		} else {
			System.out.println("getEntities empty schema and entity ");
			/* Collect all files that contain an entity */
			myFiles = getFolderContent(myPath, null);


			if (myFiles != null) {
				System.out.println("getEntities myFiles = " + myFiles.length);

				for (int i=0; i< myFiles.length; i++) {
					System.out.println(myFiles[i]);

					ucGroup myEntity = null;
					myEntity = (ucGroup)loadObject(myPath, myFiles[i]);

					/* put the object in the list */
					myList.add(myEntity);

				}//endfor
			}//endif
		}
		return myList;
	}


	public ArrayList<String> getAllEntityNames(){

		/* init */
		ArrayList<String> entityNames = new ArrayList<String>();

		ArrayList<ucGroup> allEntities = ucGroup.getAllEntities();
		for (int i = 0 ; i < allEntities.size(); i++){
			entityNames.add(allEntities.get(i).getEntityName());
		}
		return entityNames;
	}


	public static ArrayList<ucGroup> getAllEntities(){
		/* create an array list of all conceptual entities */
		System.out.println("UCGROUP: this function is deprecated. Use getEntities instead");
		ArrayList<ucGroup> myList = new ArrayList<ucGroup>();

		ArrayList<Object> allObjects = generalFunctions.getAllObjects(uForm.myPath);

		for (int i = 0; i < allObjects.size(); i++){
			myList.add((ucGroup) allObjects.get(i));
		}

//		ArrayList<uForm> allForms = (ArrayList<uForm>)generalFunctions.getAllObjects(uForm.myPath);
		return myList;
	}


	public static void fillAllEntities(){
		/* this function fills the entity with all required information
			- keys
			- fields
			- relationships

		*/

		/* if the folder exists then quit */
		File dir = new File(myPath);
		if (!dir.exists()){
			/* Collect all files that contain an entity */
			ArrayList<ucGroup> myEntities = ucGroup.getAllEntities();

			System.out.println("Processing conceptual entities... ");

			ucGroup.setEntityFields(myEntities);
			ucGroup.setEntityKeys(myEntities);
			ucGroup.setEntityRelations(myEntities);

			/* save the objects */
			System.out.println("Saving... ");
			for (int i=0; i< myEntities.size(); i++) {
				myEntities.get(i).saveObject();
			}//endfor

		}

	}


	public static void setEntityFields(ArrayList<ucGroup> myEntities){
		/* collect the fields that belong to the entity  */

		/* init */
		//this.myFields = new ArrayList<ucField>();
		ArrayList<ucField> allFields = new ArrayList<ucField>();

		/* ***************************************
		 * 1. collect all the fields
		 * ***************************************/
		String[] myFiles = getFolderContent(ucField.getPath(), null);

		/* if the list is not empty */
		if (myFiles != null) {
			/* step through the files and start matching */
			for (int i=0; i< myFiles.length; i++) {
				ucField myField = null;
				myField = (ucField)loadObject(ucField.myPath, myFiles[i]);

					/* add the key */
				allFields.add(myField);
			}//endfor
		}//endif

		/* ***************************************
		 * 2. merge the keys with entities
		 * ***************************************/

		/* step through the entities */
		for (int i=0; i< myEntities.size(); i++) {
			//System.out.println("Processing fields... (" + i + " from " + myEntities.size() +")");

			ArrayList<ucField> myFields = new ArrayList<ucField>();

			ucGroup myEntity = myEntities.get(i);

			/* step through the relations */
			for (int j=0; j< allFields.size(); j++) {
				ucField myField = allFields.get(j);


				/* if the entityname is the same add the entity */
				if(myEntity.getEntityName().equals(myField.getEntityName())){
					myFields.add(myField);
				}//endif

			}//endfor
			myEntity.setFields(myFields);
		}//endfor

	}

	/** show the entities end the number of times they are painted */
	public static void countEntities() throws IOException{
		/* init */
		String[] allFiles = getFolderContent(ucGroup.myPath, null);
		BufferedWriter outputFile = new BufferedWriter(new FileWriter("C:/dvo1/painted_entities.txt"));


		/* if the list is not empty */
		if (allFiles != null) {
			/* step through the files and start matching */
			for (int i=0; i< allFiles.length; i++) {
				ucGroup entity = null;
				entity = (ucGroup)loadObject(ucGroup.myPath, allFiles[i]);

				outputFile.write(entity.getSchemaName() +"."+ entity.getEntityName() + " painted " + entity.atTimesPainted + " times!");

			}
		}
		outputFile.close();

	}


	public static void setEntityKeys(ArrayList<ucGroup> myEntities){
		/* collect the keys that belong to the entity */

		/* init */
		ArrayList<ucKey> allKeys = new ArrayList<ucKey>();

		/* ***************************************
		 * 1. collect all the keys
		 * ***************************************/
		String[] myFiles = getFolderContent(ucKey.myPath, null);

		/* if the list is not empty */
		if (myFiles != null) {
			/* step through the files and start matching */
			for (int i=0; i< myFiles.length; i++) {
				ucKey myKey = null;
				myKey = (ucKey)loadObject(ucKey.myPath, myFiles[i]);

				/* add the key */
				allKeys.add(myKey);
			}
		}

		/* ***************************************
		 * 2. merge the keys with entities
		 * ***************************************/

		/* step through the entities */
		for (int i=0; i< myEntities.size(); i++) {
			//System.out.println("Processing keys... (" + i + " from " + myEntities.size() +")");

			ArrayList<ucKey> myKeys = new ArrayList<ucKey>();

			ucGroup myEntity = myEntities.get(i);

			/* step through the relations */
			for (int j=0; j< allKeys.size(); j++) {
				ucKey myKey = allKeys.get(j);

				/* if the entityname is the same add the entity */
				if(myEntity.getEntityName().equals(myKey.getEntityName())
					&& myEntity.getSchemaName().equals(myKey.getSchemaName())){
					myKeys.add(myKey);
				}
			}//endfor
			myEntity.setKeys(myKeys);
		}//endfor
	}


	public static void setEntityRelations(ArrayList<ucGroup> myEntities){
		/* collect the relationships that belong to the entity */

		/* init */
		ArrayList<ucRelsh> allRelations = new ArrayList<ucRelsh>();

		/* ***************************************
		 * 1. collect all the relations
		 * ***************************************/
		String[] myFiles = getFolderContent(ucRelsh.myPath, null);

		/* if the list is not empty */
		if (myFiles != null) {
			/* step through the files and start matching */
			for (int i=0; i< myFiles.length; i++) {
				ucRelsh myRelation = null;
				myRelation = (ucRelsh)loadObject(ucRelsh.myPath, myFiles[i]);

				/* add the relation */
				allRelations.add(myRelation);
			}
		}

		/* ***************************************
		 * 2. merge the relations with entities
		 * ***************************************/

		/* step through the entities */
		for (int i=0; i< myEntities.size(); i++) {
			//System.out.println("Processing relations... (" + i + " from " + myEntities.size() +")");

			ArrayList<ucRelsh> myRelations = new ArrayList<ucRelsh>();

			ucGroup myEntity = myEntities.get(i);

			/* step through the relations */
			for (int j=0; j< allRelations.size(); j++) {
				ucRelsh myRelation = allRelations.get(j);

				/*** Relations where the entity is parent ***/
				if(myEntity.getEntityName().equals(myRelation.getParententity())
					&& myEntity.getSchemaName().equals(myRelation.getParentschema())){
					myRelations.add(myRelation);
				}//endif

				/*** Relations where the entity is child ***/
				if(myEntity.getEntityName().equals(myRelation.getChildentity())
					&& myEntity.getSchemaName().equals(myRelation.getChildschema())){
					myRelations.add(myRelation);
				}//endif

			}//endfor
			myEntity.setRelations(myRelations);
		}//endfor
	}

	public static void lookForDuplicates(){
		/*init*/
		ArrayList<uCodeLine> mainSnippet = new ArrayList<uCodeLine>();
		ArrayList<uCodeLine> snippet = new ArrayList<uCodeLine>();
		boolean isDuplicate = false;
		boolean isTrue = false;

		/* get all the folder content */
		String[] allEntities = getFolderContent(ucGroup.myPath, null);

		/* while their are still forms in the directory */
		if (allEntities != null) {

			/* step through all the forms */
			for (int i = 0; i < allEntities.length; i++){
				/* load the uForm object */
				ucGroup entity = (ucGroup)loadObject(ucGroup.myPath, allEntities[i]);

				/* step through the form triggers */
				for (int m = 0; m < entity.getTriggers().size(); m++){
					/* get the triggername */
					String entityName = entity.getEntityName();
					String triggerName = entity.getTriggers().get(m).getTriggerName();

					new printLogging("**************************");
					new printLogging("*** " + entityName + "." + triggerName + " ***");
					new printLogging("**************************");
					System.out.println("Processing: " + entityName + "." + triggerName );

					/* load the codeobject*/
					uLines codeObject = entity.getTriggers().get(m);
					ArrayList<uCodeLine> actualCode = codeObject.getActualCode();

					new printLogging("*** actual code ***");
					new printLogging(actualCode);

					/* hack the code into snippets */
					ArrayList<codeSnippet> allSnippets = codeSnippet.createSnippets(actualCode, "UCGROUP", entityName, triggerName, 0);

					/* step through the snippets, looking for duplicates */
					for (int n = 0; n < allSnippets.size(); n++){
						if (allSnippets.get(n).lookForDuplicates()){

						}
					}
				}
				entity.saveObject();
			}
		}
	}//lookForDuplicates


	/** match the conceptual entity triggername with the trigger in the snippet */
	public int getTriggerPointer(String objectName, String triggerName){

		if (this.getEntityName().equals(objectName)){
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

	/* entityname */
	public String getEntityName(){
		return this.entityName;
	}

	/* schemaname */
	public String getSchemaName(){
		return this.schemaName;
	}

	/* triggers */
	public ArrayList<uLines> getTriggers(){
		return this.myTriggers;
	}

	/* fields */
	public ArrayList<ucField> getFields(){
		return this.myFields;
	}
	public void setFields(ArrayList<ucField> myFields){
		this.myFields = myFields;
	}

	public ucField getField(String fieldName){
		for (int i = 0; i < this.myFields.size();i++){
			if (fieldName.equals(this.myFields.get(i).getFieldName())){
				return this.myFields.get(i);
			}
		}
		//System.out.println("UCGROUP: Field " + fieldName + " not found in entity!");
		return null;
	}

	/* keys */
	public ArrayList<ucKey> getKeys(){
		return this.myKeys;
	}
	public void setKeys(ArrayList<ucKey> myKeys){
		this.myKeys = myKeys;
	}

	/** get the path where the ucGroup object are stored */
	public static String getPath(){
		return myPath;
	}

	/* relations */
	/** Return a list of relations where the entity is either parent of child */
	public ArrayList<ucRelsh> getRelations(){
		return this.myRelations;
	}
	/** Set list of relations where the entity is either parent of child */
	public void setRelations(ArrayList<ucRelsh> myRelations){
		this.myRelations = myRelations;
	}

	/** add 1 to the times painted */
	public void setTimesPainted(){
		this.atTimesPainted++;
	}
}
