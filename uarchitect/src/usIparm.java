import java.util.ArrayList;

/* Error logging */
import java.io.IOException;

/* java persistance */
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class usIparm extends generalFunctions implements Serializable{

	String specificationName = new String();
	String implementationName = new String();

	static String myPath = new String("C:/dvo1/OUT/DICT/USIPARM/");

	usIparm(ArrayList<ArrayList<String>> myValues){

		/* add the values to the variables */
		for (int i=0; i<myValues.size(); i++){

			if (myValues.get(i).get(0).equals("UTIMESTAMP")){

			} else if (myValues.get(i).get(0).equals("USPECNAM")){
				this.specificationName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("UIMPLNAM")){
				this.implementationName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("UPATH")){
			} else if (myValues.get(i).get(0).equals("UOPERNAM")){

			} else if (myValues.get(i).get(0).equals("UPARMNAM")){
			} else if (myValues.get(i).get(0).equals("UVERS")){
			} else if (myValues.get(i).get(0).equals("UDESCR")){
			} else if (myValues.get(i).get(0).equals("U_INTF")){
			} else if (myValues.get(i).get(0).equals("UARRAYSIZ")){
			} else if (myValues.get(i).get(0).equals("TPLINTF")){
			} else if (myValues.get(i).get(0).equals("UCTYP")){
			} else if (myValues.get(i).get(0).equals("ULITPARMNAM")){
			} else if (myValues.get(i).get(0).equals("U_GLAB")){
			} else if (myValues.get(i).get(0).equals("UIPATTR")){
			} else if (myValues.get(i).get(0).equals("UDTYPQUALIF")){
			} else if (myValues.get(i).get(0).equals("UCOMMENT")){
			} else if (myValues.get(i).get(0).equals("USDATA")){
			} else {
				System.out.println("USIPARM: No value found for key " + myValues.get(i).get(0));
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


