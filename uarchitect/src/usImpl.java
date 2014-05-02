import java.util.ArrayList;

/* Error logging */
import java.io.IOException;

/* java persistance */
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class usImpl extends generalFunctions implements Serializable{

	String specificationName = new String();
	String implementationName = new String();

	static String myPath = new String("C:/dvo1/OUT/DICT/USIMPL/");

	usImpl(ArrayList<ArrayList<String>> myValues){

		/* add the values to the variables */
		for (int i=0; i<myValues.size(); i++){

			if (myValues.get(i).get(0).equals("UTIMESTAMP")){

			} else if (myValues.get(i).get(0).equals("USPECNAM")){
				this.specificationName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("UIMPLNAM")){
				this.implementationName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("UPATH")){
			} else if (myValues.get(i).get(0).equals("UDESCR")){
			} else if (myValues.get(i).get(0).equals("UVERS")){
			} else if (myValues.get(i).get(0).equals("UIMPLID")){
			} else if (myValues.get(i).get(0).equals("UIMPLSUB")){
			} else if (myValues.get(i).get(0).equals("UCNTXT")){
			} else if (myValues.get(i).get(0).equals("USET")){
			} else if (myValues.get(i).get(0).equals("UVERSCHECKING")){
			} else if (myValues.get(i).get(0).equals("UTRANSATTR")){
			} else if (myValues.get(i).get(0).equals("UIMPLATTR")){
			} else if (myValues.get(i).get(0).equals("UCOMMENT")){
			} else if (myValues.get(i).get(0).equals("USDATA")){

			} else {
				System.out.println("USIMPL: No value found for key " + myValues.get(i).get(0));
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


