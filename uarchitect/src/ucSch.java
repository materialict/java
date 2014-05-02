import java.util.ArrayList;

/* Error logging */
import java.io.IOException;

/* java persistance */
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class ucSch extends generalFunctions implements Serializable{

	private String schemaName = new String();

	private ArrayList<ucGroup> myEntities = new ArrayList<ucGroup>();
	static String myPath = new String("C:/dvo1/OUT/DICT/UCSCH/");


	ucSch(ArrayList<ArrayList<String>> myValues){


		/* add the values to the variables */
		for (int i=0; i<myValues.size(); i++){

			if (myValues.get(i).get(0).equals("UTIMESTAMP")){
			} else if (myValues.get(i).get(0).equals("U_STAT")){
			} else if (myValues.get(i).get(0).equals("U_VLAB")){
				this.schemaName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("UVERS")){
			} else if (myValues.get(i).get(0).equals("UDESCR")){
			} else if (myValues.get(i).get(0).equals("ICMODEL")){
			} else if (myValues.get(i).get(0).equals("ICDEFINED")){
			} else if (myValues.get(i).get(0).equals("ICDIRTY")){
			} else if (myValues.get(i).get(0).equals("U_DOC")){
			} else if (myValues.get(i).get(0).equals("UDIAGRAM")){
			} else if (myValues.get(i).get(0).equals("UML_DATA")){

			} else {
				System.out.println("UCSCH: No value found for key " + myValues.get(i).get(0));
			}//endif
		}//endfor

		this.saveObject();
	}


	public void saveObject(){
		try {
			this.saveObject(myPath, this.schemaName);
		} catch (IOException e) {
			e.printStackTrace();
		}//endtry
	}

	public static ArrayList<ucSch> getAllModels(){
		/* this function retrieves a list of models from the folder.
		Loads these models into an object, puts them in an arraylist and
		then returns that list. */

		/* init */
		ArrayList<ucSch> myModels = new ArrayList<ucSch>();

		/* 1. Collect all files that contain triggers */
		String[] myFiles = getFolderContent(myPath, null);

		if (myFiles != null) {
			for (int i=0; i< myFiles.length; i++) {

				ucSch myModel = null;
				myModel = (ucSch)loadObject(myPath, myFiles[i]);
				myModels.add(myModel);
			}//endfor
		}//endif
		return myModels;
	}


	/* getters and setters */
	public String getSchemaName(){
		return this.schemaName;
	}

	public ArrayList<ucGroup> getEntities(){
		return this.myEntities;
	}

	public static void processAllModels(){
		/* processing all the schema information */
	}

	public static void fillAllModels(){
		/* this function fills the model with all required information
			- entities
		*/

		/* Collect all files that contain an schema */
		String[] myFiles = getFolderContent(ucSch.myPath, null);

		if (myFiles != null) {
			for (int i=0; i< myFiles.length; i++) {

				ucSch mySchema = null;
				mySchema = (ucSch)loadObject(ucSch.myPath, myFiles[i]);

				System.out.println("UCSCH: Processing " + mySchema.getSchemaName());

				mySchema.setEntities();

				/* save the object */
				mySchema.saveObject();

			}//endfor
		}//endif
	}


	public void setEntities(){
		/* collect the entities that belong to the schema */

		/* init */
		this.myEntities = new ArrayList<ucGroup>();

		/* Collect all files that contain entities */
		String[] myFiles = getFolderContent(ucGroup.myPath, null);

		if (myFiles != null) {
			for (int i=0; i< myFiles.length; i++) {

				ucGroup myEntity = null;
				myEntity = (ucGroup)loadObject(ucGroup.myPath, myFiles[i]);

				/* if the componentname is the same add the entity */
				if(this.schemaName.equals(myEntity.getSchemaName())){
					this.myEntities.add(myEntity);
				}//endif
			}//endfor
		}//endif
	}


}
