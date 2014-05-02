import java.util.ArrayList;

/* Error logging */
import java.io.IOException;

/* java persistance */
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class usParm extends generalFunctions implements Serializable{

	static String myPath = new String("C:/dvo1/OUT/DICT/USPARM/");

	private String specificationName = new String();
	private String operationName = new String();
	private String parameterName = new String();

	usParm(ArrayList<ArrayList<String>> myValues){

		/* add the values to the variables */
		for (int i=0; i<myValues.size(); i++){

			if (myValues.get(i).get(0).equals("UTIMESTAMP")){
			} else if (myValues.get(i).get(0).equals("USPECNAM")){
				this.specificationName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("UOPERNAM")){
				this.operationName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("UPARMNAM")){
				this.parameterName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("UDESCR")){
			} else if (myValues.get(i).get(0).equals("UVERS")){
			} else if (myValues.get(i).get(0).equals("UPSEQ")){
			} else if (myValues.get(i).get(0).equals("U_PTYP")){
			} else if (myValues.get(i).get(0).equals("UDIREC")){
			} else if (myValues.get(i).get(0).equals("U_DTYP")){
			} else if (myValues.get(i).get(0).equals("U_VLAB")){
			} else if (myValues.get(i).get(0).equals("U_TLAB")){
			} else if (myValues.get(i).get(0).equals("UCOMMENT")){
			} else if (myValues.get(i).get(0).equals("UOBJNAM")){

			/* uniface 8 */
			} else if (myValues.get(i).get(0).equals("UHINTERFACE")){

			} else {
				System.out.println("USPARM: No value found for key " + myValues.get(i).get(0));
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

		String myFilename = new String(this.specificationName + "_" + this.operationName + "_" + this.parameterName );

		/* replacing the question mark the \\ are the to escape the character */
		myFilename = myFilename.replaceAll("\\?", "");

		return myFilename;
	}

	/* getters and setters */

}


