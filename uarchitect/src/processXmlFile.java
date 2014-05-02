//package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import java.io.BufferedWriter;
import java.io.FileWriter;

/* java persistance */
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
/* java file */
import java.io.File;
import java.io.FilenameFilter;



public class processXmlFile extends generalFunctions implements Serializable{
	static String myLine;

	static String myPath = new String("C:/dvo1/");

	static String myHtmlPath = new String(myPath + "html/");
	static String mySrcPath = new String(myPath + "src/");
	static String myInputPath = new String(myPath + "input/");
	static String myOutputPath = new String(myPath + "out/");

	static String myLogFile = new String(myPath + "Matches_log.txt");
	static String myInputFile = new String();
	static String myOutputFile = new String();


	processXmlFile(){
		new printLogging("*** start ***", false);

		/* constructor */
		try {
			this.readXmlFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//endtry
	}

	public static void main(String[] args) throws IOException {
		/*
		This method displays a menu on the screen where the user can choose
		what action to take
		*/

		BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Folder " + myPath);
		System.out.println("HTML Folder " + myHtmlPath);
		System.out.println("Source Folder " + mySrcPath);
		System.out.println("Input Folder " + myInputPath);
		System.out.println("Logfile 2 " + myLogFile);
		System.out.println("");

		System.out.println("CodeRuiters Uniface Analysis tool");

		/* create an array list of a total of 10 files */
		File dir = new File(myInputPath);
		fileExtensionFilterEnds filter = new fileExtensionFilterEnds(".exp");
		String[] myFiles = dir.list(filter);

		/* process the files */
	    if (myFiles == null) {
			System.out.println("No files found in the input folder!");
			return;
		}

		for (int i=0; i< myFiles.length; i++) {
			System.out.println(" "+ i + ". Analyze " + myFiles[i]);
		}

		System.out.println("");
		System.out.println("Enter your choice : ");

		String myChoice = userInput.readLine();

		if (myChoice.length() == 0){
			System.out.println("empty value entered");
			return;
		} else {
			/* convert it to integer */

			Integer i = new Integer(myChoice);

			myInputFile = myInputPath + myFiles[i];
			myOutputFile = myInputPath + myFiles[i].substring(0, (myFiles[i].length()-4)) + ".dvo";

			System.out.println("Processing... " + myFiles[i]);

			try {
//				editXmlFileFirstTime();

//				/* if no errors in first edit continue */
//				if (checkAfterFirstEdit() >= 0){
//					readXmlFile();
//					fillObjects();
//					processObjects();
//				}

				/* display the actual menu */
				displayMenu();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//endtry
		}//endif
	}// end main


	public static void displayMenu() throws IOException{
		/*
		This method displays a second menu, after all the data has been read where the user can choose
		the action he likes to see.
		*/

		BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("CodeRuiters Uniface Analysis tool");
		System.out.println("1. Generate Proc listing ");
		System.out.println("2. Search for duplicate code ");
		System.out.println("3. List the fields (entiy, name, layout, syntax etc.");
		System.out.println("4. Count the conceptual entities used ");
		System.out.println("5. Count the conceptual fields used");


		System.out.println("");
		System.out.println("Enter your choice : ");

		String myChoice = userInput.readLine();

		if (myChoice.length() == 0){
			System.out.println("empty value entered");
			return;
		}//endif

		if (myChoice.equals("1")){
			generateProcListing();
		}//endif

		if (myChoice.equals("2")){
			checkForDuplicates();
		}//endif

		if (myChoice.equals("3")){
			ucField.listAllFields();
		}//endif
	}


	public static void generateProcListing(){
		/* This function creates files of codelines with the corresponding comments with a pro extension */
		/* Determine the starting time */
		int counter = 0;
		Calendar myStartCalender = Calendar.getInstance();
		System.out.println("[generateProcListing] Starttime: " + myStartCalender.getTime());

		/* get the files */
		File dir = new File(uForm.myPath);
		fileExtensionFilterEnds filter = new fileExtensionFilterEnds(".dvo");
		String[] myFiles = dir.list(filter);

		/* process the files */
	    if (myFiles != null) {

	        for (int i=0; i< myFiles.length; i++) {
				/* init */
				uForm myForm = null;
				FileInputStream f_in = null;
				ObjectInputStream obj_in = null;

				/* step through the list */
	            String filename = myFiles[i];

				try{
					/* load the object */
					//System.out.println("Writing... " + filename);
					f_in = new FileInputStream(uForm.myPath + filename);
					obj_in = new ObjectInputStream(f_in);

					myForm = (uForm)obj_in.readObject();

					/* generate the proc listing */
					myForm.generateProclisting();

					obj_in.close();
					f_in.close();

				} catch (ClassNotFoundException c) {
					/* catch the ClassNotFoundException */
					c.printStackTrace();
    			} catch (IOException e){
					/* catch the other errors */
					e.printStackTrace();
				}
	        }
	    }

		/* And... we are done. */
		Calendar myEndCalender = Calendar.getInstance();
		System.out.println("[generateProcListing] Endtime  : " + myEndCalender.getTime());
	}


	private static Object getObject(String myPath, String myFilename){

		/* this function opens a proeviously saved java object. And returns it */

		uForm myForm = null;
		FileInputStream f_in = null;
		ObjectInputStream obj_in = null;

		try{
			/* load the object */
			f_in = new FileInputStream(myPath + myFilename);
			obj_in = new ObjectInputStream(f_in);

			return obj_in.readObject();

		} catch (ClassNotFoundException c) {
			/* catch the ClassNotFoundException */
			c.printStackTrace();
    	} catch (IOException e) {
			/* catch the other errors */
			e.printStackTrace();
		}

		return null;
	}

	public static void checkForDuplicates(){
		/* This function checks all the available codelines. Searching for duplicates. */

		/* Determine the starting time */
		int counter = 0;
		Calendar myStartCalender = Calendar.getInstance();
		System.out.println("[checkForDuplicates] Starttime: " + myStartCalender.getTime());

		/************************************
		 * 1. Collect all forms
		 ************************************/
		new printLogging("*************");
		new printLogging("*** Forms ***");
		new printLogging("*************");
		uForm.lookForDuplicates();

		new printLogging("***************");
		new printLogging("*** ucGroup ***");
		new printLogging("***************");
		ucGroup.lookForDuplicates();

		new printLogging("***************");
		new printLogging("*** ucField ***");
		new printLogging("***************");
		ucField.lookForDuplicates();

		new printLogging("***************");
		new printLogging("*** uxGroup ***");
		new printLogging("***************");
		uxGroup.lookForDuplicates();

		new printLogging("***************");
		new printLogging("*** uxField ***");
		new printLogging("***************");
		uxField.lookForDuplicates();

		new printLogging("***************");
		new printLogging("*** Include ***");
		new printLogging("***************");
		uSource.lookForDuplicates("I");

		new printLogging("***************");
		new printLogging("*** Global ***");
		new printLogging("***************");
		uSource.lookForDuplicates("P");

		/* And... we are done. */
		Calendar myEndCalender = Calendar.getInstance();
		System.out.println("[checkForDuplicates] Endtime  : " + myEndCalender.getTime());
	}


	public static void editXmlFileFirstTime() throws IOException {
		/* this function creates a new XML where al the lines between tags are combined in one single line */

		/* Determine the starting time */
		Calendar myStartCalender = Calendar.getInstance();
		System.out.println("[editXmlFileFirstTime] Starttime: " + myStartCalender.getTime());

		/*** 0. if the outputfile allready exists skip this step ***/
		File file = new File(myOutputFile);
		if(!file.exists()){


			/*** 1. Reading input by lines: ***/
			BufferedReader myInput = new BufferedReader(new FileReader(myInputFile));
			BufferedWriter myOutput = new BufferedWriter(new FileWriter(myOutputFile));

			/* While there are still lines */
			String myString = new String();
			String myTag = new String();

			while ((myLine = myInput.readLine()) != null){

				/* First remove some thags that will otherwise cause problems when processing */
				/* remove the GOLD tags <GLD>;</GLD>*/
				myLine = myLine.replaceAll("<GLD>","");
				myLine = myLine.replaceAll("</GLD>","");

				/* Bold */
				myLine = myLine.replaceAll("<B>","");
				myLine = myLine.replaceAll("</B>","");

				/* Inverse */
				myLine = myLine.replaceAll("<I>","");
				myLine = myLine.replaceAll("</I>","");

				/* Unterline */
				myLine = myLine.replaceAll("<U>","");
				myLine = myLine.replaceAll("</U>","");

				/* Binary */
				//myLine = myLine.replaceAll("<BIN>","");
				//myLine = myLine.replaceAll("</BIN>","");



				/* every tag that can be spread amond multiple lines consists of <TABLENAME:FIELDNAME>*/
				if (myTag.isEmpty()){
					myTag = GetXmlTagFromLine(myLine);

					/* make sure there is a : present if not clear the tag */
					if (myTag.indexOf(":", 0) < 0){
						myTag = new String();
					}
				}


				/* if the string is empty just add the string otherwise add a break */
				if (myString.isEmpty()){
					myString += myLine;
				} else {
					/* where the descriptor starts with FLD we donot need to add a <br>*/
					if((myString.length() >=4 && myString.substring(0, 4).equals("<FLD"))
						||(myString.length() >=4 && myString.substring(0, 4).equals("<DSC"))){
						myString += myLine;

						/* if the end of the line consisits of -" >- then we need to replace that in -">-*/
						if (myLine.length() >=3 && myLine.substring(myLine.length()-3, myLine.length()).equals("\" >")){
							myLine = myLine.substring(0, myLine.length()-3) +"\">";
						}
					} else {
						/* add the <br> tag*/
						myString += "<BR>" + myLine;
					}
				}

				/* while the last character in the string doesnot equal a > sign we keep adding strings */
				if (!myLine.isEmpty() && myLine.substring(myLine.length()-1, myLine.length()).equals(">")){
					/* if it does contain a > we only write the line in certain instances */


					/* remove the carriage return */
					if(myLine.length() >=5 && myLine.substring(myLine.length()-5, myLine.length()).equals("<CR/>")){
						myLine = myLine.substring(0, myLine.length()-5);
					}

					if ((myLine.length() >=2 && myLine.substring(myLine.length()-2, myLine.length()).equals("/>"))
						||(myLine.length() >=2 && myLine.substring(myLine.length()-2, myLine.length()).equals("?>"))
						||(myLine.length() >=2 && myLine.substring(myLine.length()-2, myLine.length()).equals("->"))
						||(myLine.length() >=2 && myLine.substring(myLine.length()-2, myLine.length()).equals("\">"))
						||(myLine.length() >=5 && myLine.substring(myLine.length()-5, myLine.length()).equals("<OCC>"))
						||(myLine.length() >=6 && myLine.substring(myLine.length()-6, myLine.length()).equals("</OCC>"))
						||(myLine.length() >=6 && myLine.substring(myLine.length()-6, myLine.length()).equals("</DSC>"))
						||(myLine.length() >=8 && myLine.substring(myLine.length()-8, myLine.length()).equals("</TABLE>"))
						) {
							/* write the line */
							myOutput.write(myString);
							myOutput.newLine();

							/* start with a new string */
							myString = new String();
							myTag = new String();
					}

					/* or when the start and endtag are the same */
					if (!myTag.isEmpty()
						&& myLine.length() >= (myTag.length() + 3)
						&& myLine.substring(myLine.length()-(myTag.length() + 3), myLine.length()).equals("</"+myTag+">")
						){

							/* write the line */
							myOutput.write(myString);
							myOutput.newLine();

							/* start with a new string */
							myString = new String();
							myTag = new String();
					}
				}//endif
			}// endwhile

			/* close files */
			myInput.close();
			myOutput.close();
		}//endif


		/* And... we are done. */
		//System.out.println("[editXmlFileFirstTime] Starttime: " + myStartCalender.getTime());
		Calendar myEndCalender = Calendar.getInstance();
		System.out.println("[editXmlFileFirstTime] Endtime  : " + myEndCalender.getTime());
	}//editXmlFileFirstTime


	public static int checkAfterFirstEdit() throws IOException {
		/* this function checks if the first character of every line is a < */

		/* Determine the starting time */
		int counter = 0;
		Calendar myStartCalender = Calendar.getInstance();
		System.out.println("[checkAfterFirstEdit] Starttime: " + myStartCalender.getTime());

		/*** 1. Reading input by lines: ***/
		BufferedReader myInput = new BufferedReader(new FileReader(myOutputFile));

		while ((myLine = myInput.readLine()) != null){
			if (myLine.length() >=1
				&& !myLine.substring(0,1).equals("<")){
				/* add 1 to the number of faulty lines */
				counter++;
			}
		}

		/* close files */
		myInput.close();

		/* And... we are done. */
		Calendar myEndCalender = Calendar.getInstance();
		System.out.println("[checkAfterFirstEdit] Endtime  : " + myEndCalender.getTime());

		if (counter > 0){
			System.out.println(counter + " errors found. You need to adjust the software!");
			return -1;
		} else {
			System.out.println("Excellent job!! Proceeding to the next step!");
			return 0;
		}
	}//checkAfterFirstEdit


	public static void readXmlFile() throws IOException {
		/*
		 * Determine the starting time
		 */
		Calendar myStartCalender = Calendar.getInstance();
		System.out.println("[readXmlFile] Starttime: " + myStartCalender.getTime());

		/*** 1. Reading input by lines: ***/
		BufferedReader myInput = new BufferedReader(new FileReader(myOutputFile));

		/* While there are still lines */

		/* ************************************
		 * Gather table information
		 * ************************************/
		String myTag = new String();
		String myTablename = new String();

		boolean isRecord = false;
		int counter = 0;

		/* read the file */
		xmlTable myTable = new xmlTable();
		while ((myLine = myInput.readLine()) != null){

			/* get the tags from the xml file */
			myTag = GetXmlTagFromLine(myLine);

			//************************************************
			//*** Process the Table tags
			//************************************************
			if (myTag.length() >= 5 && myTag.substring(0, 5).equals("TABLE")){

				/* if there already was a tablebname than this is not the first record. */
				if (!myTablename.isEmpty()){
					/* process the table contents */
					myTable.processTablecontent(myOutputPath);
				}//endif

				/* create a new table object */
				myTable = new xmlTable();
				counter++;

				/* Strip the string and tell the object what the table and model is */
				myTablename = myTag.substring(myTag.indexOf("\"", 0)+1, myTag.length()-1);
				myTable.setTablename(myTablename.substring(0, myTablename.indexOf(".",0)));
				myTable.setModelname(myTablename.substring(myTablename.indexOf(".",0)+1, myTablename.length()));
			}//endif

			/* process the tabledescriptor */
			if (myLine.length() >= 4 && myLine.substring(0, 4).equals("<DSC")){
				myTable.addTableDefinition(myLine);
			}

			/* Process the field definition */
			if (myLine.length() >= 4 && myLine.substring(0, 4).equals("<FLD")){
				myTable.addFieldDefinition(myLine);
			}

			if (myLine.length() >= 6 && myLine.substring(0, 6).equals("</OCC>")){
				/* add the seperator */
				myTable.addRecord(myLine);
				/* no more records, the next line will be a DSC or TABLE */
				isRecord = false;
			}

			/* Process the record */
			if (isRecord){
				myTable.addRecord(myLine);
			}

			if (myLine.length() >= 5 && myLine.substring(0, 5).equals("<OCC>")){
				isRecord = true;
			}
		}// endwhile

		// close files
		myInput.close();

		/*
		 * And... we are done.
		 */
		Calendar myEndCalender = Calendar.getInstance();
		System.out.println("[readXmlFile] Endtime  : " + myEndCalender.getTime());
	}


	public static void fillObjects(){
		/* gather additional data for the objects */
		/*
		 * Determine the starting time
		 */
		Calendar myStartCalender = Calendar.getInstance();
		System.out.println("[fillObjects] Starttime: " + myStartCalender.getTime());

		/* this function reads the saved objects, puts them together and saves them again */

		/* ************************************
		 * 1. filling the model
		 * ************************************/

		/* 1a. filling the conceptual entities */
		System.out.println("1a. filling the conceptual entities");
		//ucGroup.fillAllEntities();

		System.out.println("1b. filling the conceptual schemas");
		//ucSch.fillAllModels();

		System.out.println("1c. removing obsolete folders ");
		//processXmlFile.deleteFolder(ucGroup.myPath);
		//processXmlFile.deleteFolder(ucField.myPath);
		//processXmlFile.deleteFolder(ucKey.myPath);
		//processXmlFile.deleteFolder(ucRelsh.myPath);

		/* ************************************
		 * 2. filling the component
		 * ************************************/
		System.out.println("2a. filling the painted entities ");
		uxGroup myGroup = new uxGroup();
		//myGroup.fillAllEntities();

		/* test */
		System.out.println("2a. intermission");
//		String[] myFiles = getFolderContent(uxGroup.myPath);
//
//		if (myFiles != null) {
//			for (int i=0; i< myFiles.length; i++) {
//
//				uxGroup myEntity = null;
//				myEntity = (uxGroup)loadObject(uxGroup.myPath, myFiles[i]);
//
//				/* put the object in the list */
///				System.out.println("Conceptual entity for " + myEntity.getEntityName() + " is " + myEntity.getEntityDefinition());
//
//			}//endfor
//		}//endif
//
		System.out.println("2b. filling the components");
//		uForm.fillAllComponents();
//
		/*
		 * And... we are done.
		 */
		Calendar myEndCalender = Calendar.getInstance();
		System.out.println("[fillObjects] Endtime  : " + myEndCalender.getTime());

	}

	public static void processObjects(){
		/* after gathering the information start processing the info */

		/*
		 * Determine the starting time
		 */
		Calendar myStartCalender = Calendar.getInstance();
		System.out.println("[processObjects] Starttime: " + myStartCalender.getTime());

		/* ************************************
		 * 1. processing the component
		 * ************************************/
		System.out.println("1a. processing the components");
		//uForm.processAllComponents();

		/*
		 * And... we are done.
		 */
		Calendar myEndCalender = Calendar.getInstance();
		System.out.println("[processObjects] Endtime  : " + myEndCalender.getTime());


	}

	public static String GetXmlTagFromLine(String myLine){
		/**
		 * This function strips the XML tag from a string.
		 * Input: myLline: line the be searched
		 * Output: myText: if found the begin tag from the xml string. If not found it will be empty.
		 */
		int charPosition;
		String myText;

		// myLine must be filled and first character must be a <
		if (myLine.isEmpty()){
			return "";
		} else {
			/* check for a bracket on the first character */
			if (!myLine.substring(0,1).equals("<")){
				return "";
			}
		}

		// scan the string for > if found then extract string from 0 to there
		charPosition = myLine.indexOf(">", 0);
		if (charPosition < 0) {
			return "";
		} else {
			// then remove the < of </
			// remove the sting but leave the < behind
			myText = myLine.substring(1, charPosition);

			if (myText.substring(0,1).equals("/")){
				// we have found an end tag
				return "";
			}
		}
		return myText;
		// check if it is not in the array already

	}// end GetXmlTagFromLine


	private void copyFile(String sourceFile, String targetFile){
		/* copy the sourcefile to the target file */
	}
}
