import java.util.ArrayList;

/* Error logging */
import java.io.IOException;

/* java persistance */
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class ucTable extends generalFunctions implements Serializable{

	String modelName = new String();
	String entityName = new String();

	static String myPath = new String("C:/dvo1/OUT/DICT/UCTABLE/");

	ucTable(ArrayList<ArrayList<String>> myValues){

		/* add the values to the variables */
		for (int i=0; i<myValues.size(); i++){


			if (myValues.get(i).get(0).equals("UTIMESTAMP")){

			} else if (myValues.get(i).get(0).equals("U_STAT")){
			} else if (myValues.get(i).get(0).equals("U_VLAB")){
				this.modelName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("U_TLAB")){
				this.entityName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("UVERS")){
			} else if (myValues.get(i).get(0).equals("UDESCR")){
			} else if (myValues.get(i).get(0).equals("U_DOC")){


			} else {
				System.out.println("UCTABLE: No value found for key " + myValues.get(i).get(0));
			}
		}

		try {
			this.saveObject(this.myPath, this.getFilename());
		} catch (IOException e) {
			e.printStackTrace();
		}//endtry
	}

	private String getFilename(){
		/* create a string of filenames and strip the non-authorized characters */

		String myFilename = new String(this.modelName + "_" + this.entityName);

		/* replacing the question mark the \\ are the to escape the character */
		myFilename = myFilename.replaceAll("\\?", "");

		return myFilename;
	}

	public static ArrayList<ucTable> getAllTables(){
		/* this function retrieves a list of libraries from the folder.
		Loads these libraris into an object, puts them in an arraylist and
		then returns that list. */

		/* init */
		ArrayList<ucTable> myTables = new ArrayList<ucTable>();

		/* 1. Collect all files that contain triggers */
		String[] myFiles = getFolderContent(myPath, null);

		if (myFiles != null) {
			for (int i=0; i< myFiles.length; i++) {

				ucTable myTable = null;
				myTable = (ucTable)loadObject(myPath, myFiles[i]);
				myTables.add(myTable);
			}//endfor
		}//endif
		return myTables;
	}

	/* getters and setters */

}


