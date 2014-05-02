import java.util.ArrayList;

/* Error logging */
import java.io.IOException;

/* java persistance */
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class ucKey extends generalFunctions implements Serializable{

	static String myPath = new String("C:/dvo1/OUT/DICT/UCKEY/");

	String schemaName = new String();
	String entityName = new String();
	String keyType = new String();
	String fieldName = new String();
	//Int sequenceNumber = 0;
	String sequenceNumber = new String();


	ucKey(ArrayList<ArrayList<String>> myValues){
		/* add the values to the variables */
		for (int i=0; i<myValues.size(); i++){


			if (myValues.get(i).get(0).equals("UTIMESTAMP")){
			} else if (myValues.get(i).get(0).equals("U_STAT")){
			} else if (myValues.get(i).get(0).equals("U_VLAB")){
				this.schemaName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("U_TLAB")){
				this.entityName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("U_KSEQ")){
				//this.sequenceNumber = (int)myValues.get(i).get(1);
				this.sequenceNumber = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("U_KTYP")){
				this.keyType = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("UVERS")){
			} else if (myValues.get(i).get(0).equals("UDESCR")){
			} else if (myValues.get(i).get(0).equals("U_DOC")){
			} else if (myValues.get(i).get(0).equals("U_FLABS")){
				this.fieldName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("UVALKEY")){
			} else if (myValues.get(i).get(0).equals("UML_DATA")){
			} else {
				System.out.println("UCKEY: No value found for key " + myValues.get(i).get(0));
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

		String myFilename = new String(this.schemaName + "_" + this.entityName +"_"+ this.sequenceNumber);

		return myFilename;
	}

	/* getters and setters */
	public String getSchemaName(){
		return this.schemaName;
	}
	public String getEntityName(){
		return this.entityName;
	}
	public String getFieldName(){
		return this.fieldName;
	}
	public static String getPath(){
		return myPath;
	}

}


