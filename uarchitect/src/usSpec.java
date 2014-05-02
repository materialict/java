import java.util.ArrayList;

/* Error logging */
import java.io.IOException;

/* java persistance */
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class usSpec extends generalFunctions implements Serializable{

	String specificationName = new String();
	String libraryName = new String();

	static String myPath = new String("C:/dvo1/OUT/DICT/USUBS/");

	usSpec(ArrayList<ArrayList<String>> myValues){

		/* add the values to the variables */
		for (int i=0; i<myValues.size(); i++){

			if (myValues.get(i).get(0).equals("UTIMESTAMP")){

			} else if (myValues.get(i).get(0).equals("USPECNAM")){
				this.specificationName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("UDESCR")){
			} else if (myValues.get(i).get(0).equals("UVERS")){
			} else if (myValues.get(i).get(0).equals("UVAR")){
				this.libraryName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("USPECID")){
			} else if (myValues.get(i).get(0).equals("USUBSYSTEM")){
			} else if (myValues.get(i).get(0).equals("UDEFIMPLNAM")){
			} else if (myValues.get(i).get(0).equals("UDEFPATH")){
			} else if (myValues.get(i).get(0).equals("USYNCALLOWED")){
			} else if (myValues.get(i).get(0).equals("USCOPE")){
			} else if (myValues.get(i).get(0).equals("UEXECDEF")){
			} else if (myValues.get(i).get(0).equals("USELFCONT")){
			} else if (myValues.get(i).get(0).equals("UNOSTATE")){
			} else if (myValues.get(i).get(0).equals("UTRANS")){
			} else if (myValues.get(i).get(0).equals("UTRANSATTR")){
			} else if (myValues.get(i).get(0).equals("UCOMMENT")){
			} else if (myValues.get(i).get(0).equals("UMWATTR")){
			} else if (myValues.get(i).get(0).equals("UHIDDDATA")){
			} else if (myValues.get(i).get(0).equals("URESDATA")){
			} else if (myValues.get(i).get(0).equals("UHIDCDATA")){

			} else {
				System.out.println("USUBS: No value found for key " + myValues.get(i).get(0));
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

		String myFilename = new String(this.libraryName +"_"+this.specificationName);

		/* replacing the question mark the \\ are the to escape the character */
		myFilename = myFilename.replaceAll("\\?", "");

		return myFilename;
	}

	/* getters and setters */
}