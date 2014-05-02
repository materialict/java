import java.util.ArrayList;

/* Error logging */
import java.io.IOException;

/* java persistance */
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class usIlink extends generalFunctions implements Serializable{

	String implementationName = new String();

	static String myPath = new String("C:/dvo1/OUT/DICT/USILINK/");

	usIlink(ArrayList<ArrayList<String>> myValues){

		/* add the values to the variables */
		for (int i=0; i<myValues.size(); i++){

			if (myValues.get(i).get(0).equals("UTIMESTAMP")){

			} else if (myValues.get(i).get(0).equals("UTIMESTAMP")){
			} else if (myValues.get(i).get(0).equals("UIMPLNAMCAL")){
				this.implementationName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("UUPATHCAL")){
			} else if (myValues.get(i).get(0).equals("USPECNAMDES")){
			} else if (myValues.get(i).get(0).equals("UOPERNAMDES")){
			} else if (myValues.get(i).get(0).equals("UCALSEQ")){
			} else if (myValues.get(i).get(0).equals("UDESCR")){
			} else if (myValues.get(i).get(0).equals("UVERS")){
			} else if (myValues.get(i).get(0).equals("USPECNAMCAL")){
			} else if (myValues.get(i).get(0).equals("UTYPE")){
			} else if (myValues.get(i).get(0).equals("UCALMOD")){
			} else if (myValues.get(i).get(0).equals("UCALENT")){
			} else if (myValues.get(i).get(0).equals("UCALFLD")){
			} else if (myValues.get(i).get(0).equals("UCALTRG")){
			} else if (myValues.get(i).get(0).equals("UCALLINE")){
			} else if (myValues.get(i).get(0).equals("UCALSTAT")){
			} else if (myValues.get(i).get(0).equals("UDESTYPE")){
			} else if (myValues.get(i).get(0).equals("UCALENTY")){
			} else if (myValues.get(i).get(0).equals("UCALINST")){
			} else if (myValues.get(i).get(0).equals("UCALLIB")){
			} else if (myValues.get(i).get(0).equals("UDESINST")){
			} else if (myValues.get(i).get(0).equals("UDESLIB")){
			} else if (myValues.get(i).get(0).equals("UCALPROC")){
			} else if (myValues.get(i).get(0).equals("UCOMMENT")){

			} else {
				System.out.println("USILINK: No value found for key " + myValues.get(i).get(0));
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

		String myFilename = new String(this.implementationName);

		/* replacing the question mark the \\ are the to escape the character */
		myFilename = myFilename.replaceAll("\\?", "");

		return myFilename;
	}

	/* getters and setters */
}