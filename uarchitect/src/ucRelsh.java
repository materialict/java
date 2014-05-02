import java.util.ArrayList;

/* Error logging */
import java.io.IOException;

/* java persistance */
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class ucRelsh extends generalFunctions implements Serializable{

	static String myPath = new String("C:/DVO1/OUT/DICT/UCRELSH/");

	String parentEntity = new String();
	String parentSchema = new String();
	String childEntity = new String();
	String childSchema = new String();
	//int sequenceNumber = 0;
	String sequenceNumber = new String();

	ucRelsh(ArrayList<ArrayList<String>> myValues){

		/* add the values to the variables */
		for (int i=0; i<myValues.size(); i++){

			if (myValues.get(i).get(0).equals("UTIMESTAMP")){
			} else if (myValues.get(i).get(0).equals("U_STAT")){
			} else if (myValues.get(i).get(0).equals("U_GLAB")){
				this.parentEntity = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("U_VLAB")){
				this.parentSchema = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("U_RGLAB")){
				this.childEntity = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("U_RVLAB")){
				this.childSchema = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("UVERS")){
			} else if (myValues.get(i).get(0).equals("UDESCR")){
			} else if (myValues.get(i).get(0).equals("U_DELC")){
			} else if (myValues.get(i).get(0).equals("U_UPDC")){
			} else if (myValues.get(i).get(0).equals("U_KSEQ")){
				//this.sequenceNumber = (int)myValues.get(i).get(1);
				this.sequenceNumber = myValues.get(i).get(1);

			} else if (myValues.get(i).get(0).equals("U_FIDX")){
			} else if (myValues.get(i).get(0).equals("U_INTEGRITY")){
			} else if (myValues.get(i).get(0).equals("U_PROCEDURE")){
			} else if (myValues.get(i).get(0).equals("U_OPTIONAL")){
			} else if (myValues.get(i).get(0).equals("U_ARITY")){
			} else if (myValues.get(i).get(0).equals("U_DOC")){
			} else if (myValues.get(i).get(0).equals("U_FLABS")){
			} else if (myValues.get(i).get(0).equals("URELTYPE")){
			} else if (myValues.get(i).get(0).equals("U_ARITYMAX")){
			} else if (myValues.get(i).get(0).equals("UML_DATA")){


			} else {
				System.out.println("UCRELSH: No value found for key " + myValues.get(i).get(0));
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

		String myFilename = new String(this.parentSchema + "_" + this.parentEntity +"_"+ this.childSchema + "_" + this.childEntity +"_"+ this.sequenceNumber);

		return myFilename;
	}

	/* getters and setters */
	public static String getPath(){
		return myPath;
	}
	public String getParententity(){
		return this.parentEntity;
	}
	public String getParentschema(){
		return this.parentSchema;
	}
	public String getChildentity(){
		return this.childEntity;
	}
	public String getChildschema(){
		return this.childSchema;
	}

}


