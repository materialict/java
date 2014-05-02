import java.util.ArrayList;

/* Error logging */
import java.io.IOException;

/* java persistance */
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class uLibr extends generalFunctions implements Serializable{

	String libraryName = new String();
	String sourceName = new String();

	static String myPath = new String("C:/dvo1/OUT/DICT/ULIBR/");

	uLibr(ArrayList<ArrayList<String>> myValues){

		/* add the values to the variables */
		for (int i=0; i<myValues.size(); i++){


			if (myValues.get(i).get(0).equals("UTIMESTAMP")){

			} else if (myValues.get(i).get(0).equals("ULIBR")){
			} else if (myValues.get(i).get(0).equals("ULIBRARY")){
				this.libraryName = myValues.get(i).get(1);
			} else if (myValues.get(i).get(0).equals("UDESCR")){

			} else {
				System.out.println("ULIBR: No value found for key " + myValues.get(i).get(0));
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

		String myFilename = new String(this.libraryName);

		/* replacing the question mark the \\ are the to escape the character */
		myFilename = myFilename.replaceAll("\\?", "");

		return myFilename;
	}

	public static ArrayList<uLibr> getAllLibraries(){
		/* this function retrieves a list of libraries from the folder.
		Loads these libraris into an object, puts them in an arraylist and
		then returns that list. */

		/* init */
		ArrayList<uLibr> myLibraries = new ArrayList<uLibr>();

		/* 1. Collect all files that contain triggers */
		String[] myFiles = getFolderContent(myPath, null);

		if (myFiles != null) {
			for (int i=0; i< myFiles.length; i++) {

				uLibr myLibrary = null;
				myLibrary = (uLibr)loadObject(myPath, myFiles[i]);
				myLibraries.add(myLibrary);
			}//endfor
		}//endif
		return myLibraries;
	}

	/* getters and setters */
	public String getLibraryname(){
		return this.libraryName;
	}
}


