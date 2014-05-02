import java.util.ArrayList;

/* error handling */
import java.io.IOException;

/* java persistance */
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/* File output */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;


class xmlTable extends xmlGeneral implements Serializable{
	/*
	This class represents the tables definition as it is found in the XML file
	*/

	/* DVO 09-10-2008 */
	private String myTablename = new String();
	private String myModelname = new String();
	ArrayList<xmlField> myFields = new ArrayList<xmlField>();
	private ArrayList<String> myRecords = new ArrayList<String>();
	private ArrayList<xmlOccurence> myOccurences = new ArrayList<xmlOccurence>();

	private ArrayList<ArrayList<String>> myTableDescription = new ArrayList<ArrayList<String>>();
	public ArrayList<ArrayList<String>> myTableFields = new ArrayList<ArrayList<String>>();

	//static String myPath = new String("C:/dvo1/OUT/DICT/XMLTABLE/");

	xmlTable(){
		/* constructor */
	}


	public void setTablename(String myTablename){
		this.myTablename = myTablename;
	}
	public void setModelname(String myModelname){
		this.myModelname = myModelname;

	}
	public String getTablename(){
		return this.myTablename;
	}

	public String getModelName(){
		return this.myModelname;
	}

	public void addFieldDefinition(String myDefinition){
		/* replace any trailing spaces for the = sign */
		myDefinition = myDefinition.replaceAll(" =", "=");
		myDefinition = myDefinition.substring(0, myDefinition.length()-2);

		ArrayList<ArrayList<String>> tmpFields = new ArrayList<ArrayList<String>>();

		tmpFields = this.processStringToArrayList("<FLD", myDefinition);

		this.myFields.add(new xmlField(tmpFields));
	}

	public void addTableDefinition(String myDefinition){
		/* replace any trailing spaces for the = sign */
		myDefinition = myDefinition.replaceAll(" =", "=");
		myDefinition = myDefinition.substring(0, myDefinition.length()-1);

		this.myTableDescription = this.processStringToArrayList("<DSC", myDefinition);
	}

	public void addRecord(String myRecord){
		/* Add the string containing the record information to the arraylist of strings */

		if (myRecord.length()>= 6 && myRecord.substring(0, 6).equals("</OCC>")){
			/* process the record */
			xmlOccurence tmpOccurence = new xmlOccurence();

			tmpOccurence.setOccurence(this.processRecord(this.myRecords));

			this.myOccurences.add(tmpOccurence);

			/* clear the records arraylist */
			this.myRecords = new ArrayList<String>();
		} else {
			this.myRecords.add(myRecord);

		}
	}

	public void processTablecontent(String myOutputPath){
		/* this function determines the table content and creates the appropriate object */
		for (int i=0;i < this.myOccurences.size(); i++){
			myOccurences.get(i).createObject(this.myTablename, myOutputPath);
		}

	}

	public void printToFile(String myOutputPath, int counter) throws IOException{
		/* this function writes the class content to a file */
		myOutputPath += this.myModelname + "/" + this.myTablename + "/";
		createFolder(myOutputPath);

		System.out.println("Writing to file...");

		BufferedWriter myOutput = new BufferedWriter(new FileWriter(myOutputPath + this.myTablename +"."+ this.myModelname + "_" + counter  + ".out"));

		/* write the descriptor fields */
		for (int i = 0; i < this.myTableDescription.size(); i++){

			myOutput.write(this.myTableDescription.get(i).get(0));
			myOutput.write(" ==> ");
			myOutput.write(this.myTableDescription.get(i).get(1));
			myOutput.newLine();
		}

		for (int i = 0; i < this.myFields.size(); i++){
			ArrayList<ArrayList<String>> tmpField = new ArrayList<ArrayList<String>>();
			tmpField = this.myFields.get(i).getProperties();

			for (int j = 0; j < tmpField.size(); j++){
				myOutput.write(tmpField.get(j).get(0));
				myOutput.write(" ==> ");
				myOutput.write(tmpField.get(j).get(1));
				myOutput.write(" |DVO| ");
			}
			myOutput.newLine();
		}

		for (int i = 0; i < this.myOccurences.size(); i++){
			ArrayList<ArrayList<String>> tmpRecord = new ArrayList<ArrayList<String>>();
			tmpRecord = this.myOccurences.get(i).getOccurence();

			for (int j = 0; j < tmpRecord.size(); j++){
				myOutput.write(tmpRecord.get(j).get(0));
				myOutput.write(" ==> ");
				myOutput.write(tmpRecord.get(j).get(1));
				myOutput.write(" |DVO| ");
			}
			myOutput.newLine();
		}

		myOutput.close();
	}//printToFile

	public void saveObject(String filePath, int counter) throws IOException {
		/* this function writes an object to a folder */
		filePath += this.myModelname + "/" + this.myTablename + "/";
		createFolder(filePath);

		if (!filePath.isEmpty()){
			/* Write to disk with FileOutputStream */
			FileOutputStream f_out = new FileOutputStream(filePath + this.myModelname +"_"+ this.myTablename + "_" + counter + ".dvo");

			/* Write object with ObjectOutputStream */
			ObjectOutputStream obj_out = new ObjectOutputStream (f_out);

			/* Write object out to disk */
			obj_out.writeObject ( this );

			obj_out.close();
			f_out.close();
		}

	}//saveObject

	public ArrayList<ArrayList<String>> processStringToArrayList(String myType, String myLine){
		/*
		This function translates the string with values into individual key value pairs in
		an arraylist
		*/
		ArrayList<ArrayList<String>> myList = new ArrayList<ArrayList<String>>();

		/* first strip the header tag */
		myLine = myLine.substring(myType.length(), myLine.length());
		myLine = myLine.trim();

		/* while myline is not empty search for spaces, they seperate the key-value pairs */
		String myValue = new String();
		while (!myLine.isEmpty()){
			if (myLine.indexOf(" ", 0) >0){
				/* spaces found */
				myValue = myLine.substring(0, myLine.indexOf(" ", 0));
				myLine = myLine.substring(myLine.indexOf(" ", 0)+1, myLine.length());

			} else {
				/* one more to go */
				if (!myLine.isEmpty()){
					myValue = myLine;
					myLine = "";
				}
			}
			myList.add(this.separateKeyValue(myValue));
		}
		return myList;
	}


	private ArrayList<String> separateKeyValue(String keyValue){
		/*
		this functions strips the key and the value part and puts
		them in an arraylist
		*/
		String myKey = new String();
		String myValue = new String();
		ArrayList<String> myList = new ArrayList<String>();

		myKey = keyValue.substring(0, keyValue.indexOf("=", 0));
		myValue = keyValue.substring(keyValue.indexOf("=", 0)+1, keyValue.length());
		/* remove the " quotes */
		myValue = myValue.replaceAll("\"", "");

		myList.add(myKey);
		myList.add(myValue);

		return myList;
	}


	private ArrayList<ArrayList<String>> processRecord(ArrayList<String> myRecord){
		/* This function processes individual records <OCC to objects */
		ArrayList<ArrayList<String>> tmpRecord = new ArrayList<ArrayList<String>>();

		for (int i=0; i < myRecord.size(); i++){
			String myLine = new String(myRecord.get(i));
			ArrayList<String> myList = new ArrayList<String>();

			String myKey = new String();
			String myValue = new String();

			myKey = myLine.substring(0, myLine.indexOf(">",0));
			myKey = myKey.substring(myLine.indexOf(":",0) + 1, myKey.length());

			//myValue = myLine.substring(myLine.indexOf(">",0) + 1, myLine.indexOf("</",0));

			/* print some stuff */
			myValue = myLine.substring(myLine.indexOf(">",0) + 1, myLine.lastIndexOf("</",myLine.length()));

			myList.add(myKey);
			myList.add(myValue);

			tmpRecord.add(myList);
		}

		return tmpRecord;

	}
}