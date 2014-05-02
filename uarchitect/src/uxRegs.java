import java.util.ArrayList;

/* Error logging */
import java.io.IOException;

/* java persistance */
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Class consisting of the dataparts for component variables
 * @author Dennis Vorst
 * @version 1.0
 */

public class uxRegs extends uRegister implements Serializable{

	String componentName = new String();

	static String myPath = new String("C:/dvo1/OUT/DICT/UXREGS/");
	String preceedingChar = new String("$");
	String followingChar = new String("$");

	uxRegs(ArrayList<ArrayList<String>> myValues){
		super();

		/* add the values to the variables */
		for (int i=0; i<myValues.size(); i++){

			if (myValues.get(i).get(0).equals("U_STAT")){
			} else if (myValues.get(i).get(0).equals("U_TYPE")){
			} else if (myValues.get(i).get(0).equals("U_FORMLIB")){
				this.componentName = myValues.get(i).get(1);
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
				System.out.println("UXREGS: No value found for key " + myValues.get(i).get(0));
			}
		}

		/* write the object */
		this.saveObject();
	}

	/**
	 * Save the java object to a folder
	 *
	 */
	public void saveObject(){
		try {
			this.saveObject(this.myPath, this.componentName + "_" + this.variableName);
		} catch (IOException e) {
			e.printStackTrace();
		}//endtry
	}

	/**
	 * this method retrieves a list of components from the folder.
	 * Loads these components into an object, puts them in an arraylist and
	 * then returns that list.
	 */
	public ArrayList<uxRegs> getAllComponentVariables(){

		/* init */
		ArrayList<uxRegs> myRegs = new ArrayList<uxRegs>();

		/* 1. Collect all files that contain triggers */
		String[] myFiles = getFolderContent(myPath, null);

		if (myFiles != null) {
			for (int i=0; i< myFiles.length; i++) {

				uxRegs myReg = null;

//				try {
					myReg = (uxRegs)loadObject(myPath, myFiles[i]);
//				} catch (IOException e) {
//					e.printStackTrace();
//				} finally{
//
//				}

				myRegs.add(myReg);

			}//endfor

		}//endif

		return myRegs;
	}

	/* getters and setters section */
	/**
	 * Return the name of the component the variable is declared in.
	 */
	public String getComponentName(){
		return this.componentName;
	}

}


