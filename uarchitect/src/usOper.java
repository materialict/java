import java.util.ArrayList;

/* Error logging */
import java.io.IOException;

/* java persistance */
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class usOper extends generalFunctions implements Serializable{

	String specificationName = new String();
	String operationName = new String();

	static String myPath = new String("C:/dvo1/OUT/DICT/USOPER/");

	usOper(ArrayList<ArrayList<String>> myValues){

		/* add the values to the variables */
		for (int i=0; i<myValues.size(); i++){

			if (myValues.get(i).get(0).equals("UTIMESTAMP")){

			} else if (myValues.get(i).get(0).equals("USPECNAM")){
				this.specificationName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("UOPERNAM")){
				this.operationName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("UDESCR")){
			} else if (myValues.get(i).get(0).equals("UVERS")){
			} else if (myValues.get(i).get(0).equals("USCOPE")){
			} else if (myValues.get(i).get(0).equals("USELFCONT")){
			} else if (myValues.get(i).get(0).equals("UEXECTYP")){
			} else if (myValues.get(i).get(0).equals("UNOSTATE")){
			} else if (myValues.get(i).get(0).equals("UPRE")){
			} else if (myValues.get(i).get(0).equals("UPOST")){
			} else if (myValues.get(i).get(0).equals("URETVAL")){
			} else if (myValues.get(i).get(0).equals("UCOMMENT")){

			} else {
				System.out.println("USOPER: No value found for key " + myValues.get(i).get(0));
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

		String myFilename = new String(this.specificationName + "_" + this.operationName);

		/* replacing the question mark the \\ are the to escape the character */
		myFilename = myFilename.replaceAll("\\?", "");

		return myFilename;
	}

	/* getters and setters */
}