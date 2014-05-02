import java.util.ArrayList;
/* File classes */
import java.io.File;


/* object input */
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/* error handling */
import java.io.IOException;


class xmlGeneral{
	/* This class contains the general functions for extracting the text */

	xmlGeneral(){}

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
			//System.out.println("myNewPath : " + myNewPath);
			File nextFile = new File(myNewPath);

			boolean fileExists = nextFile.exists();

			if (!fileExists){
				// create the folder
				nextFile.mkdirs();
			}//endif

		}//endfor

	}//createFolder



}