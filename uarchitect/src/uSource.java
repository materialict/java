import java.util.ArrayList;

/* Error logging */
import java.io.IOException;

/* java persistance */
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class uSource extends generalFunctions implements Serializable{

	String libraryName = new String();
	String sourceName = new String();
	String language = new String();
	String type = new String();
	String text = new String();

	uLines codeObject = null;

	private static int occurenceNumber = 0;

	static String myPath = new String("C:/dvo1/OUT/DICT/USOURCE/");

	uSource(ArrayList<ArrayList<String>> myValues){

		uSource.occurenceNumber++;


		/* add the values to the variables */
		for (int i=0; i<myValues.size(); i++){

			if (myValues.get(i).get(0).equals("UTIMESTAMP")){
			} else if (myValues.get(i).get(0).equals("UCOMPSTAMP")){
			} else if (myValues.get(i).get(0).equals("USTAT")){
			} else if (myValues.get(i).get(0).equals("USUB")){
				this.type = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("UVAR")){
				this.libraryName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("ULABEL")){
				this.sourceName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("ULAN")){
				this.language = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("MSGTYPE")){
			} else if (myValues.get(i).get(0).equals("UVERS")){
			} else if (myValues.get(i).get(0).equals("UDESCR")){
			} else if (myValues.get(i).get(0).equals("UVPOS")){
			} else if (myValues.get(i).get(0).equals("UHPOS")){
			} else if (myValues.get(i).get(0).equals("UVSIZ")){
			} else if (myValues.get(i).get(0).equals("UHSIZ")){
			} else if (myValues.get(i).get(0).equals("AUTHORIZ")){
			} else if (myValues.get(i).get(0).equals("UCLASS")){
			} else if (myValues.get(i).get(0).equals("LOCREF")){
			} else if (myValues.get(i).get(0).equals("UCONFIRM")){
			} else if (myValues.get(i).get(0).equals("UAUDIO")){
			} else if (myValues.get(i).get(0).equals("UCOMMENT")){
			} else if (myValues.get(i).get(0).equals("UTEXT")){
				this.text = myValues.get(i).get(1);
			} else {
				System.out.println("USOURCE: No value found for key " + myValues.get(i).get(0));
			}
		}

		/* after gathering the information start putting it in the appropriate classes */

		if (this.type.equals("I")){
			/* I = include proc */
			//super(this.sourceName, this.text, this.type);
			codeObject = new uLines(this.text);
		} else if (this.type.equals("P")){
			/* P = Global proc */
			//super(this.sourceName, this.text, this.type);
			codeObject = new uLines(this.text);
		} else if (this.type.equals("M")){
			/* M = Message */
		} else if (this.type.equals("D")){
			/* D = device translation tables */
		} else if (this.type.equals("T")){
			/* T = Keyboard translation table */
		} else if (this.type.equals("C")){
			/* C = Unknown type */
		} else {
			System.out.println("USOURCE: unsupported type |" + this.type + "| found!");
		}

		this.saveObject();
	}


	public void saveObject(){
		//this.versionNumber++;

		try {
			this.saveObject(this.myPath + this.type + "/", this.getFilename());
		} catch (IOException e) {
			e.printStackTrace();
		}//endtry
	}


	private String getFilename(){
		/* create a string of filenames and strip the non-authorized characters */

		//String myFilename = new String(this.type + "_" + this.libraryName + "_" + this.sourceName + "_" + this.language);
		String myFilename = new String(this.libraryName + "_" + this.language + "_" + uSource.occurenceNumber);
		/* replacing the question mark the \\ are the to escape the character */
		myFilename = myFilename.replaceAll("\\?", "");

		return myFilename;
	}

	/** Return an arraylist of Usource objects that contain only include procs*/
	public static ArrayList<uSource> getAllIncludeProcs(){
		return uSource.getAllUsource(uSource.myPath + "I/");
	}

	/** Return an arraylist of Usource objects that contain only global procs*/
	public static ArrayList<uSource> getAllGlobalProcs(){
		return uSource.getAllUsource(uSource.myPath + "P/");
	}

	public static ArrayList<uSource> getAllUsource(String pathName){

		/* init */
		ArrayList<uSource> allProcs = new ArrayList<uSource>();
		ArrayList<Object> allObjects = generalFunctions.getAllObjects(pathName);

		for (int i = 0; i < allObjects.size(); i++){
			allProcs.add((uSource) allObjects.get(i));
		}

		return allProcs;
	}

	public static void lookForDuplicates(String type){
		/*init*/
		ArrayList<uCodeLine> mainSnippet = new ArrayList<uCodeLine>();
		ArrayList<uCodeLine> snippet = new ArrayList<uCodeLine>();
		boolean isDuplicate = false;
		boolean isTrue = false;

		/* get all the folder content */
		String[] allProcs = getFolderContent(uSource.myPath + type + "/", null);
		String sourceType = "(include proc)";
		if (type.equals("P")){
			sourceType = "(global proc)";
		}

		/* while their are still forms in the directory */
		if (allProcs != null) {

			/* step through all the objects */
			for (int i = 0; i < allProcs.length; i++){
				/* load the object */
				uSource proc = (uSource)loadObject(uSource.myPath + type + "/", allProcs[i]);

				/* get the triggername */
				String sourceName = proc.getSourcename();

				new printLogging("**************************");
				new printLogging("*** " + sourceName + " " + sourceType + " ***");
				new printLogging("**************************");

				/* load the codeobject*/
				uLines codeObject = proc.getCodeObject();
				ArrayList<uCodeLine> actualCode = codeObject.getActualCode();

				new printLogging("*** actual code ***");
				new printLogging(actualCode);

				/* hack the code into snippets */
				ArrayList<codeSnippet> allSnippets = codeSnippet.createSnippets(actualCode, "USOURCE", sourceName, sourceType, 0);

				/* step through the snippets, looking for duplicates */
				for (int n = 0; n < allSnippets.size(); n++){
					if (allSnippets.get(n).lookForDuplicates()){

					}
				}
				proc.saveObject();
			}
		}
	}//lookForDuplicates


	/* getters and setters */
	/*** library ***/
	public String getLibraryname(){
		return this.libraryName;
	}
	public void setLibraryname(String myLibrary){
		this.libraryName = myLibrary;
	}
	/*** sourcename ***/
	public String getSourcename(){
		return this.sourceName;
	}
	public void setSourcename(String mySource){
		this.sourceName = mySource;
	}

	/*** source object ***/
	public uLines getCodeObject(){
		return this.codeObject;
	}
	public void setCodeObject(uLines codeObject){
		this.codeObject = codeObject;
	}

	/*** source object ***/
	public String getObjectType(){
		return this.type;
	}
	public void setObjectType(String type){
		this.type = type;
	}

}


