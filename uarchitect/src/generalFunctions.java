//package src;

import java.util.ArrayList;

/* java file */
import java.io.File;
import java.io.FilenameFilter;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

/* java persistance */
import java.io.Serializable;
/* object output */
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
/* object input */
import java.io.FileInputStream;
import java.io.ObjectInputStream;



public class generalFunctions implements Serializable{

	/* inherited variables */
	String componentName = new String();
	String modelName = new String();
	String libraryName = new String();
	String entityName = new String();


	public ArrayList<String> getValuesByColumnid(ArrayList<ArrayList<String>> myRecords, int columnId){
		/**
		 * This function creates a list of values that are retrieved
		 * by the columnid that is being inserted.
		 */
		int counter = 0;

		ArrayList<String> myList = new ArrayList<String>();
		ArrayList<String> myTableList = new ArrayList<String>();
		String myValue;

		for (counter=1; counter < myRecords.size(); counter++){
			myValue = new String();

			myTableList = myRecords.get(counter);
			myValue = myTableList.get(columnId);

			myList.add(myValue);
		}//endfor

		return myList;
	}//getValuesByColumnid

	public ArrayList<String> getValuesByTagname(ArrayList<ArrayList<String>> myRecords, String tagName){
		/**
		 * This function creates a list of values that are retrieved
		 * by the tagname that is being inserted.
		 */
		ArrayList<String> myList = new ArrayList<String>();

		int counter = getColumnidByTagname(myRecords, tagName);
		myList = getValuesByColumnid(myRecords, counter);

		return myList;
	}//getValuesByTagname

	public ArrayList<ArrayList<String>> getRecordsByTagname(ArrayList<ArrayList<String>> myRecords, String myTag, String myValue){
		/**
		 * This function creates a list of values that are retrieved
		 * by the tagname that is being inserted.
		 */

		// copy the list
		ArrayList<ArrayList<String>> myTempRecords = new ArrayList<ArrayList<String>>();

		for (int counter = 0; myRecords.size() > counter; counter++){
			ArrayList<String> myTempRecord = new ArrayList<String>();
			myTempRecord = myRecords.get(counter);
			myTempRecords.add(myTempRecord);
		}

		// start processing
		int columnId = getColumnidByTagname(myTempRecords, myTag);

		if (columnId < 0){
			System.out.println("Tagname not found in record string!");
			myTempRecords.clear();
			return myTempRecords;
		}

		String myColumnValue = new String();
		ArrayList<String> myRecord = new ArrayList<String>();

		for (int counter = 1; myTempRecords.size() > counter; counter++){
			myRecord = myTempRecords.get(counter);

			myColumnValue = myRecord.get(columnId);

			if (!myColumnValue.equals(myValue)){
				myTempRecords.remove(counter);
				counter--;
			}//endif
		}//endfor

		return myTempRecords;
	}//getRecordsByTagname

	public int getColumnidByTagname(ArrayList<ArrayList<String>> myRecords, String tagName){
		/**
		 * This function retrieves the columnid of the
		 * the tagname that is being inserted.
		 */

		int counter = 0;
		ArrayList<String> myList = new ArrayList<String>();

		// get only the first recors
		myList = myRecords.get(0);

		// search for the column id
		counter = getColumnidByTagnameSingleLine(myList, tagName);

		return counter;
	}//getColumnidByTagname

	public int getColumnidByTagnameSingleLine(ArrayList<String> myRecord, String tagName){
		/**
		 * This function retrieves the columnid of the
		 * the tagname that is being inserted.
		 */
		boolean isFound = false;

		int counter = 0;
		for (counter=0; counter < myRecord.size(); counter++){
			if (myRecord.get(counter).equals(tagName)){
				isFound = true;
				break;
			}
		}//endfor

		if (isFound){
			return counter;
		} else {
			return -1;
		}//endif
	}//getColumnidByTagnameSingleLine

	public ArrayList<ArrayList<String>> addCommentToArrayList(ArrayList<ArrayList<String>> myList){
		/**
		 * This function adds a comment line to the array list that is given in the variable myList.
		 * This function assumes that the first line contains the header information
		 * and that the other lines contain the actual records
		 */
		int counter = 0;
		ArrayList<String> myValue = new ArrayList<String>();


		for (counter = 0; myList.size() > counter; counter++){
			String myNewValue = new String();
			myValue = myList.get(counter);

			if (counter == 0){
				myNewValue = "COMMENT";
			} else {
				myNewValue = "";
			}//endif
			myValue.add(myNewValue);
			myList.set(counter, myValue);
		}//endfor

		return myList;
	}//addCommentToArrayList

	public String cutValueFromString(String myLine, String myValue){
		int myPointer = myLine.indexOf(myValue, 0);

		if (myPointer < 0){
			// not found
			return myLine;
		} else {
			myLine = myLine.substring(0, myPointer) + myLine.substring(myPointer + myValue.length(), myLine.length());
		}

		return myLine.trim();
	}


	public ArrayList<uCodeLine> mergeLines(ArrayList<uCodeLine> codeLines, ArrayList<uCodeLine> addLines, int pointer){
		/** add the addLines object to the codeLines object starting at the pointer */
		ArrayList<uCodeLine> tempLines = new ArrayList<uCodeLine>();


		/* move to the pointer */
		for (int i =0; i < pointer; i++){
			tempLines.add(codeLines.get(i));
		}

		/* add the addLines */
		for (int i =0; i < addLines.size(); i++){
			tempLines.add(addLines.get(i));
		}

		/* add the rest after the pointer */
		for (int i = pointer + 1; i < codeLines.size(); i++){
			tempLines.add(codeLines.get(i));
		}

		//System.out.println("***************************");
		//for(int x=pointer - 2 ; x < pointer+4; x++){
		//	System.out.println(tempLines.get(x).getProcessedLine());
		//}


		return tempLines;
	}







	public String processCode(ArrayList<String> myRecord, ArrayList<String> myHeaders, String myTag){
		/**
		 * This function retrieves the codeline string from the record arraylist. First the actual column
		 * number is determined on basis of the tag that is given. Then that column number is used to extract
		 * the string tag from the record arraylist.
		 *
		 */

		String myValue = new String();
		ArrayList<String> myCodeLine = new ArrayList<String>();

		int counter = getColumnidByTagnameSingleLine(myHeaders, myTag);
		myValue = myRecord.get(counter);

		return myValue;
	}

	public ArrayList<String> splitString(String myLine, String mySeparator){
		/**
		 * This function splits the string into a paramter and a value part. Both values are
		 * returned in an ArrayList where the first value (0) is the parameter and the
		 * second (1) is the value part.
		 */
		ArrayList<String> myList = new ArrayList<String>();
		myLine = myLine.trim();

		String myParameter = new String();
		String myValue = new String();

		// search for a space
		int myPointer = myLine.indexOf(mySeparator, 0);

		if (myPointer >=0){
			myParameter = myLine.substring(0, myPointer);
			myParameter = myParameter.trim();

			myValue = myLine.substring(myPointer + 1, myLine.length());
			myValue = myValue.trim();

			myList.add(myParameter);
			myList.add(myValue);
		}//endif

		return myList;
	}//splitString


	public String cleanupText(String myLine){

		/* Trim the result */
		myLine = myLine.trim();

		if (myLine.isEmpty()){

		} else {
			/* Clean up the lines */
			myLine = myLine.replaceAll("\t", " ");

			while (myLine.indexOf("  ", 0) >= 0){
				myLine = myLine.replaceAll("  ", " ");
			}

			/* Trim the result */
			myLine = myLine.trim();

			// replace XML tags
			myLine = myLine.replaceAll("&lt;", "<");
			myLine = myLine.replaceAll("&gt;", ">");
			myLine = myLine.replaceAll("&amp;", "&");
			myLine = myLine.replaceAll("&quot;", "\"");
			myLine = myLine.replaceAll("&apos;", "'");
			myLine = myLine.replaceAll("&LT;", "<");
			myLine = myLine.replaceAll("&GT;", ">");
			myLine = myLine.replaceAll("&AMP;", "&");
			myLine = myLine.replaceAll("&QUOT;", "\"");
			myLine = myLine.replaceAll("&APOS;", "'");
		}//endif

		return myLine;
	}//cleanupText


	public String convertToXml(String myLine){

		/* Trim the result */
		myLine = myLine.trim();

		if (myLine.isEmpty()){

		} else {
			// process the ampersand sign first
			myLine = myLine.replaceAll("&", "&amp;");

			// replace XML tags
			myLine = myLine.replaceAll("<", "&lt;");
			myLine = myLine.replaceAll(">", "&gt;");
			myLine = myLine.replaceAll("\"", "&quot;");
			myLine = myLine.replaceAll("'", "&apos;");
			myLine = myLine.replaceAll("\\\\", "&#92;");
			myLine = myLine.replaceAll("/", "&#47;");


		}//endif

		return myLine;
	}//cleanupText


	public String generateXmlFromString(String myLine){

		/* Trim the result */
		myLine = myLine.trim();

		if (myLine.isEmpty()){

		} else {
			// make myline uppercase
			myLine = myLine.toUpperCase();

			// replace XML tags
			myLine = myLine.replaceAll("<", "&lt;");
			myLine = myLine.replaceAll(">", "&gt;");
			myLine = myLine.replaceAll("\"", "&quot;");
			myLine = myLine.replaceAll("'", "&apos;");
			myLine = myLine.replaceAll("&#92;", "\\\\");
			myLine = myLine.replaceAll("&#47;", "/");


			// process the ampersand sign last
			myLine = myLine.replaceAll("&", "&amp;");
		}//endif

		return myLine;
	}//cleanupText

	public static void cleanFolder(String fileName){
		/* this function deletes all files in the myPath folder */

		// A File object to represent the filename
		File f = new File(fileName);

		// Make sure the file or directory exists and isn't write protected
		if (!f.exists()){
			throw new IllegalArgumentException("Delete: no such file or directory: " + fileName);
		}

		if (!f.canWrite()){
			throw new IllegalArgumentException("Delete: write protected: " + fileName);
		}


		// If it is a directory, make sure it is empty
		if (f.isDirectory()) {
			String[] files = f.list();
			if (files.length > 0){
				throw new IllegalArgumentException(	"Delete: directory not empty: " + fileName);
			}
		}

		// Attempt to delete it
		boolean success = f.delete();

		if (!success){
			throw new IllegalArgumentException("Delete: deletion failed");
		}

	}

	public static void deleteFolder(String myPath){
		/* this function deletes a folder */
		File dir = new File(myPath);

		if (!deleteDir(dir)){
			System.out.println("ERROR deleting " + myPath);
		}

	}

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }




	public void checkFolder(String myPath){
		/* this function filters the different folders in a path string and then
		checks if they exist. If not the function creates them
		*/

		ArrayList<String> myFolders = new ArrayList<String>();

		File file = new File(myPath);
		String pathSeparator = "/";

		while (!myPath.isEmpty()){
			int myPointer = myPath.indexOf(pathSeparator, 0);
			if (myPointer >=0){
				myFolders.add(myPath.substring(0, myPointer));
				myPath = myPath.substring(myPointer+1);
			} else {
				// we have reached the end
				if (myPath.length()>0){
					myFolders.add(myPath);
					myPath = "";
				}//endif
			}//endif
		}//endwhile

		// the first is the disk
		String myNewPath = new String(myFolders.get(0) + pathSeparator);

		// check the individual folders
		for (int counter = 1; myFolders.size() > counter; counter++){
			myNewPath += myFolders.get(counter) + pathSeparator;
			File nextFile = new File(myNewPath);

			boolean fileExists = nextFile.exists();

			if (!fileExists){
				// create the folder
				nextFile.mkdirs();
			}//endif

		}//endfor

	}//checkFolder

  	public boolean checkCharacter(String myChar){
  		/**
  		 * Check the character before or after the entname/fieldname, to make sure it contains
		 * - " as in retrieve "ENTNAME"
		 * - . as in FIELDNAME.ENTNAME
		 * - ( as in if (FIELDNAME.ENTNAME = 1)
		 * - ) as in if (1 = FIELDNAME.ENTNAME)
		 * - = as in if (IDCLING.ROBAR="N") OR if (IDCLING="N")
		 * - ! as in if (IDCLING.ROBAR!="N") OR if (IDCLING!="N")
		 * - , as in fieldsyntax PCKORT.ROBAR, "NED"
		 * - % as in scan $LISTDEFECTUITV$, "%%CDDEFECT.ROBAR%%%"
		 * - plus (+), minus (-), times (*), division (/), Square (unknown), Square root (^) as in pBDAFSPRTOT = pBDAFSPRTOT+BDAFSPR.ROBAR or pBDAFSPRTOT = BDAFSPR.ROBAR+pBDAFSPRTOT
		 * - <space> as in the same retrieve statement
		 * - <tab> Tabs should not be present in the myProcessedClassLine.  *
		 *
		 * otherwise it is not the entname/fieldname but the string is part of something else.
		 * Like a variable starting and/or ending with a $
		 *
		 *
		 */
  		ArrayList<String> myChars = new ArrayList<String>();
  		myChars.add(" ");
  		myChars.add("\"");
  		myChars.add(".");
  		myChars.add("(");
  		myChars.add(")");
  		myChars.add("=");
  		myChars.add("!");
  		myChars.add(",");
  		myChars.add("%");
  		myChars.add("+");
  		myChars.add("-");
  		myChars.add("/");
  		myChars.add("*");

  		boolean isMatch = false;
  		/*
  		 * Step through the myChars list looking for a match
  		 */
  		for (int counter = 0; myChars.size() > counter; counter++){
  			if (myChars.get(counter).equals(myChar)){
  				/*
  				 * We have a match
  				 */
  				isMatch = true;
  				break;
  			}//endif
  		}//endfor

  		return isMatch;
  	}//checkFirstCharacter


  	public ArrayList<String> getUniqueValues(ArrayList<ArrayList<String>> myList, int columnNumber){
  		/**
  		 * This function creates a list of unique values from the arraylist. The columnNumber determines which
  		 * values are uniquely represented.
  		 *
  		 */

  		System.out.println("------------------------------------");
  		System.out.println("-- Start retrieving unique values --");
  		System.out.println("------------------------------------");
        ArrayList<String> myItems = new ArrayList<String>();

        /*
         * Put the pointer to 1 because the 0-row contains the header info
         * */
        for (int counter = 1; myList.size() > counter; counter++){

        	String myValue = new String();
        	myValue = myList.get(counter).get(columnNumber);

        	if (!myItems.contains(myValue)){
        		myItems.add(myValue);
        	}
        }

  		System.out.println("List of values " + myItems);

  		System.out.println("------------------------------------");
  		System.out.println("--  End retrieving unique values  --");
  		System.out.println("------------------------------------");

  		return myItems;
  	}


	public void dumpToFile(String fileName, ArrayList<String> myRecords, ArrayList<String> myHeaders) throws IOException{
		/**
		 * This function dumps the content of a list to an outputfile
		 */

		int counter = 0;
		BufferedWriter myOutput = new BufferedWriter(new FileWriter(fileName));

		myOutput.write("Logfile ");
		myOutput.newLine();

		//myOutput.write(myHeaders);
		myOutput.newLine();

		for (int i = 0; i < myRecords.size(); i++){
			myOutput.write(myRecords.get(i));
			myOutput.newLine();
		}

		myOutput.close();

	}//dumpToFile


	public void createFolder(String myPath){
		/* this function filters the different folders in a path string and then
		checks if they exist. If not the function creates them
		*/
		ArrayList<String> myFolders = new ArrayList<String>();

		File file = new File(myPath);
		String pathSeparator = "/";

		while (!myPath.isEmpty()){
			int myPointer = myPath.indexOf(pathSeparator, 0);
			if (myPointer >=0){
				myFolders.add(myPath.substring(0, myPointer));
				myPath = myPath.substring(myPointer+1);
			} else {
				// we have reached the end
				if (myPath.length()>0){
					myFolders.add(myPath);
					myPath = "";
				}//endif
			}//endif
		}//endwhile

		// the first is the disk
		String myNewPath = new String(myFolders.get(0) + pathSeparator);

		// check the individual folders
		for (int counter = 1; myFolders.size() > counter; counter++){
			myNewPath += myFolders.get(counter) + pathSeparator;
			File nextFile = new File(myNewPath);

			boolean fileExists = nextFile.exists();

			if (!fileExists){
				// create the folder
				nextFile.mkdirs();
			}//endif

		}//endfor

	}//createFolder


	public void saveObject(String filePath, String myComponent) throws IOException {
		/* this function writes an object to a folder */
		createFolder(filePath);

		if (!filePath.isEmpty()){

			// Write to disk with FileOutputStream
			FileOutputStream f_out = new FileOutputStream(filePath + myComponent + ".dvo");

			// Write object with ObjectOutputStream
			ObjectOutputStream obj_out = new ObjectOutputStream (f_out);

			// Write object out to disk
			obj_out.writeObject ( this );

			/* close the files */
			obj_out.close();
			f_out.close();
		}
	}

	public static Object loadObject(String myPath, String myFilename) {

		/* this function opens a previously saved java object. And returns it */
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
		} finally{

			try {
				obj_in.close();
				f_in.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally{

			}


		};
		return null;
	}


	public static String[] getFolderContent(String myPath, String myFilter){
			/* This function returns a list of files in the path given
			with the file type */

			myFilter = "." + myFilter;

			/* get the files in the folder */
			File dir = new File(myPath);

			//if (!dir.exists()){
			//	System.out.println("GENERAL: no files found for " + myPath);
			//}

		    // The list of files can also be retrieved as File objects
		    File[] files = dir.listFiles();

			if (!myFilter.isEmpty()){
//				FilenameFilter fileFilter = new FilenameFilter() {
//					public boolean accept(File file, String s, String myFilter) {
//						if (s.endsWith(myFilter)){
//							return true;
//						}
//						// others?
//						return false;
//					}
//				}

//				String[] myFiles = dir.list(fileFilter);
//				return dir.list(fileFilter);
				return dir.list();
			} else {
				return dir.list();
			}
	}

	static ArrayList<Object> getAllObjects(String pathName){

		/* init */
		ArrayList<Object> objects = new ArrayList<Object>();

		/* Collect all files that are forms */
		String[] allFiles = getFolderContent(pathName, null);

		if (allFiles != null) {
			for (int i=0; i< allFiles.length; i++) {
				Object curObject = null;
				curObject = (Object)loadObject(pathName, allFiles[i]);

				objects.add(curObject);
			}//endfor
		}//endif
		return objects;
	}

	/* getters and setters */
	public String getEntityName(){
		return this.entityName;
	}

	/** get the pointer in the object list */
	public int getObjectPointer(String[] objects, String name){

		if (objects != null){
			//new printLogging("objects " + objects.length);
			for (int i = 0; i < objects.length; i++){
				if (name.equals(objects[i])){
					return i;
				}
			}
		}
		return 0;
	}
}
