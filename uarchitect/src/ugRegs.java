import java.util.ArrayList;

/* Error logging */
import java.io.IOException;

/* java persistance */
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class ugRegs extends uRegister implements Serializable{
	/* global variables */

	String libraryName = new String();

	static String myPath = new String("C:/dvo1/OUT/DICT/UGREGS/");
	String PreceedingChar = new String("$$");


	ugRegs(ArrayList<ArrayList<String>> myValues){
		super();

		/* add the values to the variables */
		for (int i=0; i<myValues.size(); i++){

			if (myValues.get(i).get(0).equals("U_STAT")){
			} else if (myValues.get(i).get(0).equals("U_TYPE")){
			} else if (myValues.get(i).get(0).equals("U_FORMLIB")){
				this.libraryName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("U_NAME")){
				this.setVariablename(myValues.get(i).get(1));
			} else if (myValues.get(i).get(0).equals("UVERS")){
			} else if (myValues.get(i).get(0).equals("UDESCR")){
			} else if (myValues.get(i).get(0).equals("U_DTYP")){
				this.setVariabletype(myValues.get(i).get(1));
			} else if (myValues.get(i).get(0).equals("U_LAYOUT")){
			} else if (myValues.get(i).get(0).equals("U_DOC")){
			} else if (myValues.get(i).get(0).equals("UTIMESTAMP")){
			} else {
				System.out.println("UGREGS: No value found for key " + myValues.get(i).get(0));
			}
		}

		/* write the object */
		this.saveObject();
	}

	public void saveObject(){
		try {
			this.saveObject(this.myPath, this.libraryName + "_" + this.variableName);
		} catch (IOException e) {
			e.printStackTrace();
		}//endtry
	}


	public static ArrayList<uxRegs> getAllComponentVariables(){
		/* this function retrieves a list of components from the folder.
		Loads these components into an object, puts them in an arraylist and
		then returns that list. */

		/* init */
		ArrayList<uxRegs> myRegs = new ArrayList<uxRegs>();

		/* 1. Collect all files that contain triggers */
		//String myPath = "C:/dvo1/OUT/DICT/UXREGS/";
		String[] myFiles = getFolderContent(uxRegs.myPath, null);

		if (myFiles != null) {
			for (int i=0; i< myFiles.length; i++) {

				uxRegs myReg = null;
				myReg = (uxRegs)loadObject(uxRegs.myPath, myFiles[i]);
				myRegs.add(myReg);

			}//endfor

		}//endif

		return myRegs;
	}

	/* getters and setters */


}


