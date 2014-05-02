import java.util.ArrayList;
/* Error logging */
import java.io.IOException;
/* java persistance */
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
/* java output */
import java.io.BufferedWriter;
/* java file */
import java.io.File;

public class uxGroup extends generalFunctions implements Serializable{

	String entityName = new String();
	String schemaName = new String();
	String componentName = new String();

	private ArrayList<uLines> myTriggers = new ArrayList<uLines>();
	private ArrayList<uxField> myPaintedFields = new ArrayList<uxField>();
	private ArrayList<uxGroup> myChildren = new ArrayList<uxGroup>();
	private ucGroup entityDefinition = null;

	//public int versionNumber = 0;

	private int x = 0;
	private int y = 0;
	private int singleWidth = 0;
	private int singleHeight = 0;
	private int totalWidth = 0;
	private int totalHeight = 0;
	private int paintedOrder = 0;

	private boolean inDatabase = true;

	ArrayList<String> myWarnings = new ArrayList<String>();

	static String myPath = new String("C:/dvo1/OUT/DICT/UXGROUP/");

	/* dummy constructor */
	uxGroup(){}

	uxGroup(ArrayList<ArrayList<String>> myValues){


		/* add the values to the variables */
		for (int i=0; i<myValues.size(); i++){


			if (myValues.get(i).get(0).equals("UTIMESTAMP")){
			} else if (myValues.get(i).get(0).equals("ULABEL")){
				this.entityName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("UBASE")){
				this.schemaName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("UFORM")){
				this.componentName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("UVERS")){
			} else if (myValues.get(i).get(0).equals("UDESCR")){
			} else if (myValues.get(i).get(0).equals("U_BORD")){
			} else if (myValues.get(i).get(0).equals("U_INDB")){
				if(myValues.get(i).get(1).equals("N")){
					this.inDatabase = false;
				} else {
					this.inDatabase = true;
				}
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
			} else if (myValues.get(i).get(0).equals("LCK")){
			} else if (myValues.get(i).get(0).equals("UNLOCK")){
			} else if (myValues.get(i).get(0).equals("GETSEGM")){
			} else if (myValues.get(i).get(0).equals("FLIST")){
			} else if (myValues.get(i).get(0).equals("BREAK")){
			} else if (myValues.get(i).get(0).equals("UPOPUP")){
			} else if (myValues.get(i).get(0).equals("UISBCLASS")){
			} else if (myValues.get(i).get(0).equals("UISASSOC")){
			} else if (myValues.get(i).get(0).equals("U_OBJSVC")){
			} else if (myValues.get(i).get(0).equals("U_SVCUSE")){
			} else if (myValues.get(i).get(0).equals("HTML_ENTPROP")){
			} else if (myValues.get(i).get(0).equals("HTML_TABTYPE")){
			} else if (myValues.get(i).get(0).equals("HTML_ENTHK_B")){
			} else if (myValues.get(i).get(0).equals("HTML_ENTHK_E")){
			} else if (myValues.get(i).get(0).equals("HTML_OCCHK_B")){
			} else if (myValues.get(i).get(0).equals("HTML_OCCHK_E")){
			} else if (myValues.get(i).get(0).equals("HTML_ENTCLASS")){



			} else if (myValues.get(i).get(0).equals("VLDE")
					|| myValues.get(i).get(0).equals("VLDK")
					|| myValues.get(i).get(0).equals("ADDOCC")
					|| myValues.get(i).get(0).equals("DELOCCR")
					|| myValues.get(i).get(0).equals("CREOCC")
					|| myValues.get(i).get(0).equals("DOC")
					|| myValues.get(i).get(0).equals("DETAIL")
					|| myValues.get(i).get(0).equals("GENERAL")
					|| myValues.get(i).get(0).equals("GETOCC")
					|| myValues.get(i).get(0).equals("PUTOCC")
					|| myValues.get(i).get(0).equals("DELOCC")
					|| myValues.get(i).get(0).equals("MENU")
					|| myValues.get(i).get(0).equals("FINDOCC")
					|| myValues.get(i).get(0).equals("ERROR")
					|| myValues.get(i).get(0).equals("STARTMOD")
					|| myValues.get(i).get(0).equals("ENDMOD")){

					uLines myTrigger = new uLines(myValues.get(i).get(1));
					myTrigger.setTriggerName(myValues.get(i).get(0));

					this.myTriggers.add(myTrigger);


			} else {
				System.out.println("UXGROUP: No value found for key " + myValues.get(i).get(0));
			}//endif
		}//endfor

		this.saveObject();
	}


	private String getFilename(){
		/* create a string of filenames and strip the non-authorized characters */

		String myFilename = new String(this.componentName + "_" + this.schemaName + "_" + this.entityName);

		/* replacing the question mark the \\ are the to escape the character */
		myFilename = myFilename.replaceAll("\\?", "");
		myFilename = myFilename.replaceAll("\\*", "");

		return myFilename;
	}


	public void saveObject(){
		//this.versionNumber++;

		try {
			this.saveObject(myPath, this.getFilename());
		} catch (IOException e) {
			e.printStackTrace();
		}//endtry
	}

	public static void processAllEntities(){
		/* ater gathering all the required information
		process that information */
	}


	/**
	Method that fills the painted entity objects with the following additional information:
	- painted fields
	- conceptual entity definition
	- trigger code from the conceptual entity
	- trigger code from the conceptual entity fields
	*/
	public void fillAllEntities(){
		/* this function fills the entity with all required information
			- fields
		*/

		// dvo - 16-12-2009
		return;

		/* if the folder exists then quit */
		//File dir = new File(myPath);
		//if (!dir.exists()){

			/* Collect all files that contain an entity */
//			ArrayList<uxGroup> myEntities = uxGroup.getAllEntities();

//			System.out.println("Filling painted entities... ");

//			this.setEntityFields(myEntities);
//			this.setConceptualEntity(myEntities);
			/* transfers the trigger code from the conceptual entity to the painted one */
//			this.replaceTriggers(myEntities);



			/* save the objects */
//			System.out.println("Saving uxGroup... ");
//			for (int i=0; i< myEntities.size(); i++) {
				//System.out.println("Saving uxGroup... " + myEntities.get(i).getEntityName());

//				myEntities.get(i).saveObject();
				//myEntities.remove(i);
				//i--;
//			}//endfor
		//}
//		System.out.println("fillAllEntities done!");
	}

	/** method to retrieve the conceptual entity belonging to the painted entity */
	public void setConceptualEntity(ArrayList<uxGroup> myEntities){
		/* step through the entities */
		//System.out.println("setConceptualEntity myEntities " + myEntities.size());

		/* init */

		/* set the entity */
		for (int i=0; i< myEntities.size(); i++) {
			//System.out.println("Looking for " + myEntities.get(i).getSchemaName() + ":" + myEntities.get(i).getEntityName());
			/* get the conceptual entity */
			ucGroup myModelEntity = new ucGroup();
			myModelEntity = myModelEntity.getEntity(myEntities.get(i).getSchemaName(), myEntities.get(i).getEntityName());

			/* if found preserve it */
			if (myModelEntity != null){
				myEntities.get(i).entityDefinition = myModelEntity;

				/* mark the entity as being painted and save it */
				myModelEntity.setTimesPainted();
				myModelEntity.saveObject();
			} else {
				//System.out.println("UXGROUP: No conceptual definition found for entity " + myEntities.get(i).getSchemaName() + ":" + myEntities.get(i).getEntityName());
			}
		}
	}

	/** method that replaces empty triggers from the painted entity (uxGroup) with the content of the conecptual entity (ucGroup)*/
	private void replaceTriggers(ArrayList<uxGroup> myEntities){

		/* step throught the list of painted entities */
		for(int j = 0; j < myEntities.size() ; j++){
			/* if there is a conceptual entity */
			ucGroup myConceptual = myEntities.get(j).entityDefinition;
			if (myConceptual != null){

				/* step through the triggers of the painted enitities */
				System.out.println("UXGROUP: Retrieving triggers for " + myEntities.get(j).getEntityName());
				for (int i = 0; i < myEntities.get(j).myTriggers.size(); i++){
					/* step through the conceptual triggers */
					for (int k = 0; k < myConceptual.getTriggers().size(); k++){
						/* if the triggernames are the same than skip */
						if (myEntities.get(j).myTriggers.get(i).getTriggerName().equals(myConceptual.getTriggers().get(k).getTriggerName())){
							/* trigger is already defined so skip */
							myConceptual.getTriggers().remove(k);
							k--;
						}
					}
				}

				/* we have got a list of missing conceptual triggers left */
				System.out.println("UXGROUP: Adding triggers for " + myEntities.get(j).getEntityName());
				for (int k = 0; k < myConceptual.getTriggers().size(); k++){
					/* create a new trigger
					add the trigger name
					add the code lines */
					uLines myTrigger = new uLines(myConceptual.getTriggers().get(k).getTriggerName(), myConceptual.getTriggers().get(k).getCodelines());
					/* trigger is a copy */
					myTrigger.setOriginal(false);

					/* add the trigger */
					//System.out.println("myEntities.get(j) " + myEntities.get(j));
					myEntities.get(j).setTrigger(myTrigger);
				}


				/* now do the fields */
				for (int i = 0; i < myEntities.get(j).getFields().size(); i++){
					uxField paintedField = myEntities.get(j).getFields().get(i);

					/* if the field is not painted than skip it*/
					//myEntities.get(j).getFields().get(i).replaceTriggers(myConceptual.getField(paintedField.getFieldName()));

					String fieldName = paintedField.getFieldName();
					//System.out.println("UXGROUP: naam " + fieldName);
					if (myConceptual.getField(fieldName) != null){
						ucField conceptualField = myConceptual.getField(fieldName);

						/* mark field as painted */
						conceptualField.setTimesPainted();

						//System.out.println("UXGROUP: veld " + conceptualField);
						myEntities.get(j).getFields().get(i).replaceTriggers(conceptualField);
					}
				}
				/* save the object */
				myConceptual.saveObject();
			} else {
				System.out.println("UXGROUP: Conceptual entity of " + myEntities.get(j).getSchemaName() + "." + myEntities.get(j).getEntityName() + " is empty!");
			}

		}

	}

	public ArrayList<String> getAllEntityNames(){

		/* init */
		ArrayList<String> entityNames = new ArrayList<String>();

		ArrayList<uxGroup> allEntities = uxGroup.getAllEntities();
		for (int i = 0 ; i < allEntities.size(); i++){
			entityNames.add(allEntities.get(i).getEntityName());
		}
		return entityNames;
	}

	public static ArrayList<uxGroup> getAllEntities(){
		/* create an array list of all conceptual entities */

		ArrayList<uxGroup> myList = new ArrayList<uxGroup>();


		/* Collect all files that contain an entity */
		String[] myFiles = getFolderContent(myPath, null);

		if (myFiles != null) {
			for (int i=0; i< myFiles.length; i++) {

				uxGroup myEntity = null;
				myEntity = (uxGroup)loadObject(myPath, myFiles[i]);

				/* put the object in the list */
				myList.add(myEntity);

			}//endfor
		} else {
			System.out.println("UXGROUP: No entities found while getting all entities!");
		}//endif

		return myList;
	}//getAllEntities


	public static void setEntityFields(ArrayList<uxGroup> myEntities){
		/* collect the fields that belong to the entity  */

		/* init */
		//this.myFields = new ArrayList<ucField>();
		ArrayList<uxField> allFields = new ArrayList<uxField>();

		/* ***************************************
		 * 1. collect all the fields
		 * ***************************************/
		String[] myFiles = getFolderContent(uxField.getPath(), null);

		/* if the list is not empty */
		if (myFiles != null) {
			/* step through the files and start matching */
			for (int i=0; i< myFiles.length; i++) {
				uxField myField = null;
				myField = (uxField)loadObject(uxField.myPath, myFiles[i]);

				/* add the key */
				allFields.add(myField);
			}//endfor
		}//endif

		/* ***************************************
		 * 2. merge the keys with entities
		 * ***************************************/

		/* step through the entities */
		for (int i=0; i< myEntities.size(); i++) {
			ArrayList<uxField> myFields = new ArrayList<uxField>();

			uxGroup myEntity = myEntities.get(i);

			/* step through the relations */
			for (int j=0; j< allFields.size(); j++) {
				uxField myField = allFields.get(j);

				/* if the entityname is the same add the entity */
				if(myEntity.getEntityName().equals(myField.getEntityName())){
					myFields.add(myField);
				}//endif

			}//endfor
			myEntity.setFields(myFields);
		}//endfor

	}


	public void generateProclisting(BufferedWriter myOutput) throws IOException{
		/* write the entity triggers to the file */

		System.out.println("UXGROUP: Proc listing for entities");
		for (int i = 0; i< this.myTriggers.size(); i++){

			myOutput.write("Trigger <" + myTriggers.get(i).getTriggerName() + "> from Entity: " + this.getEntityName() + "."+this.getSchemaName());
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

		/* then the field triggers */
		System.out.println("UXGROUP: Proc listing for fields");

		for (int i = 0; i < this.myPaintedFields.size(); i++){

			try{
				/* generate the proc listing */
				this.myPaintedFields.get(i).generateProclisting(myOutput);
			} catch (IOException e){
				/* catch the other errors */
				e.printStackTrace();
			}
		}
	}


	public void setProperties(int counter, int x, int y, int w, int h, int totalWidth, int totalHeight){
		this.x = x;
		this.y = y;
		this.singleWidth = w;
		this.singleHeight = h;
		this.totalWidth = totalWidth;
		this.totalHeight = totalHeight;
		this.paintedOrder = counter;

		//System.out.println(this.getEntityName() +" x="+ x+" y="+ y+" w="+ w+" h="+ h+" totalHeight="+ totalHeight+" totalWidth="+ totalWidth);
	}


	public void checkRelations(){
		/* check if there is a relationship between the painted entities
		if it is not and it is a database entity than create a warning */

		/* if the list is empty */
		if (this.entityDefinition != null){

			System.out.println("Processing... " + this.getEntityName());
			System.out.println(this.entityDefinition.getEntityName());

			/* get the relationships */
			ArrayList<ucRelsh> myRels = this.entityDefinition.getRelations();

			for (int i = 0; i < this.myChildren.size(); i++){
				uxGroup myChild = myChildren.get(i);

				/* if it is a database entity */
				if(myChild.isDatabase()){

					boolean isMatch = false;

					/* step through the relationships */
					for (int j = 0; j < myRels.size(); j++){


						/* if this entity is defined as parent and the child as child
							OR this entity is child and the child is parent. */
						if (

							(this.getEntityName().equals(myRels.get(j).getChildentity())
							&& myChild.getEntityName().equals(myRels.get(j).getParententity()))
							|| (this.getEntityName().equals(myRels.get(j).getParententity())
							&& myChild.getEntityName().equals(myRels.get(j).getChildentity())) ){

							/* we found a match */
							isMatch = true;
							break;
						}//endif
					}//endfor

					if (!isMatch){
						this.setWarning("Warning (6100): No relation found between entities " + this.getEntityName() + " and " + myChild.getEntityName());
						//System.out.println("Warning (6100): No relation found between entities " + this.getEntityName() + " and " + myChild.getEntityName());
					}//endif
				}//endif
			}//endfor
		} else {
			System.out.println("UXGROUP: no conceptual entity found for component entity " + this.entityName + "!");
			return;
		}
	}

	/* getters and setters */
	public String getComponentName(){
		return this.componentName;
	}
	public String getEntityName(){
		return this.entityName;
	}
	public String getSchemaName(){
		return this.schemaName;
	}


	public ArrayList<uLines> getTriggers(){
		return this.myTriggers;
	}

	/* single trigger manipulation */
	/** add the trigger to the trigger arraylist  */
	public void setTrigger(uLines myTrigger){

		//System.out.println("UXGROUP: Setting trigger: " + myTrigger.getTriggerName());
		this.myTriggers.add(myTrigger);
	}


	public uLines getTrigger(String triggerName){

		/* step through the triggers and return it */
		for (int i =0; i < this.myTriggers.size(); i++){
			if (this.myTriggers.get(i).getTriggerName().equals(triggerName)){
				return this.myTriggers.get(i);
			}
		}
		System.out.println("UXGROUP: Error retrieving trigger!");
		return null;
	}


	/** match the painted entity triggername with the trigger in the snippet */
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


	public ArrayList<uxField> getFields(){
		return this.myPaintedFields;
	}
	public void setFields(ArrayList<uxField> myFields){
		this.myPaintedFields = myFields;
	}

	public static String getPath(){
		return myPath;
	}
	public int getPaintedorder(){
		return this.paintedOrder;
	}
	public void addChild(uxGroup myChild){
		this.myChildren.add(myChild);
	}

	public void removeChild(uxGroup myChild){
		/* look for the child occurence and remove it */
		for (int i = 0; i < this.myChildren.size(); i++){

			if (this.myChildren.get(i).getEntityName().equals(myChild.getEntityName())
				&& this.myChildren.get(i).getSchemaName().equals(myChild.getSchemaName())	){
					/* found it*/
					//System.out.println("Removing... " + myChild.getEntityName() + " from " + this.getEntityName());
					this.myChildren.remove(i);
					break;
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
		String[] allEntities = getFolderContent(uxGroup.myPath, null);

		/* while their are still forms in the directory */
		if (allEntities != null) {

			/* step through all the forms */
			for (int i = 0; i < allEntities.length; i++){
				/* load the uForm object */
				uxGroup entity = (uxGroup)loadObject(uxGroup.myPath, allEntities[i]);

				/* step through the form triggers */
				for (int m = 0; m < entity.getTriggers().size(); m++){
					/* get the triggername */
					String entityName = entity.getEntityName();
					String triggerName = entity.getTriggers().get(m).getTriggerName();

					new printLogging("**************************");
					new printLogging("*** " + entityName + "." + triggerName + " ***");
					new printLogging("**************************");

					/* load the codeobject*/
					uLines codeObject = entity.getTriggers().get(m);
					ArrayList<uCodeLine> actualCode = codeObject.getActualCode();

					new printLogging("*** actual code ***");
					new printLogging(actualCode);

					/* hack the code into snippets */
					ArrayList<codeSnippet> allSnippets = codeSnippet.createSnippets(actualCode, "UXGROUP", entityName, triggerName, 0);

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


	public ArrayList<uxGroup> getChildren(){
		return this.myChildren;
	}
	public int getX(){
		/* return the starting x coordinate */
		return this.x;
	}
	public int getY(){
		/* return the starting y coordinate */
		return this.y;
	}
	public int getEndX(){
		/* return the ending x coordinate */
		return this.x + this.getSingleWidth();
	}
	public int getEndY(){
		/* return the ending y coordinate */
		return this.y + this.getSingleHeight();
	}
	public int getSingleWidth(){
		return this.singleWidth;
	}
	public int getSingleHeight(){
		return this.singleHeight;
	}
	public int getTotalWidth(){
		return this.totalWidth;
	}
	public int getTotalHeight(){
		return this.totalHeight;
	}
	public void setEntityDefinition(ucGroup myDefinition){
		this.entityDefinition = myDefinition;
	}
	public ucGroup getEntityDefinition(){
		return this.entityDefinition;
	}
	public boolean isDatabase(){
		return this.inDatabase;
	}

	public void setWarning(String myWarning){
		this.myWarnings.add(myWarning);
	}

}
