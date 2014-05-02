import java.util.ArrayList;

/* Error logging */
import java.io.IOException;

/* java persistance */
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class uMessages extends generalFunctions implements Serializable{

	private ArrayList<uMessage> myMessages = new ArrayList<uMessage>();
	static String myPath = new String("C:/dvo1/OUT/DICT/USOURCE/");


	uMessages(){

	}

	public void saveObject(){
		try {
			this.saveObject(myPath, "uMessages.dvo");
		} catch (IOException e) {
			e.printStackTrace();
		}//endtry
	}

	/* getters and setters */
	public void addMessage(uMessage myMessage){
		myMessages.add(myMessage);
	}

	public ArrayList<String> getLanguages(){
		/* this function reads all the messages and filters the language */

		/* init */
		ArrayList<String> myLanguages = new ArrayList<String>();
		ArrayList<uMessage> myMessages = this.getAllMessages();

		/* Collect all files that are messages  */
		for (int i=0; i< myMessages.size(); i++) {
			myLanguages.add(myMessage.getLanguageid());
		}

		/* sort the list */
		myMessages.sort(myLanguages);

		/* now filter the unique values */
		for (int i=0; i< myLanguages.size(); i++) {

			for (int j=i+1; j< myLanguages.size(); j++) {
				if (myLanguages.get(i).equals(myLanguages.get(j))){
					myLanguages.remove(j);
					j--;
				}
			}
		}
		return myLanguages;
	}

	public ArrayList<String> getLabels(){
		/* this function reads all the messages and filters the labels  */

		/* init */
		ArrayList<String> myLabels = new ArrayList<String>();
		ArrayList<uMessage> myMessages = this.getAllMessages();

		/* Collect all files that are messages  */
		for (int i=0; i< myMessages.size(); i++) {
			myLabels.add(myMessage.getLanguageid());
		}

		/* sort the list */
		myMessages.sort(myLabels);

		/* now filter the unique values */
		for (int i=0; i< myLabels.size(); i++) {

			for (int j=i+1; j< myLabels.size(); j++) {
				if (myLabels.get(i).equals(myLabels.get(j))){
					myLabels.remove(j);
					j--;
				}
			}
		}
		return myLabels;
	}

	public static ArrayList<uMessage> getAllMessages(){
		/* this function retrieves a list of messages from the folder.
		Loads these messages into an object, puts them in an arraylist and
		then returns that list. */

		/* init */
		ArrayList<uMessage> myMessages = new ArrayList<uMessage>();

		/* 1. Collect all files that contain triggers */
		String[] myFiles = getFolderContent(uMessage.myPath, null);


		if (myFiles != null) {
			for (int i=0; i< myFiles.length; i++) {
				/* load the object */
				uMessage myMessage = null;
				myMessage = (uMessage)loadObject(uMessage.myPath, myFiles[i]);

				myMessages.add(myMessage);
			}
		}
	}



	public static void checkAllMessages(){
		/* this function checks all messages */

		/* init */
		boolean isFound = false;

		ArrayList<String> myLanguages	= this.getLanguages();
		ArrayList<String> myLabels		= this.getLabels();
		ArrayList<uMessage> myMessages	= this.getAllMessages();

		ArrayList<String> myLine = new ArrayList<String>();
		ArrayList<ArrayList<String>> myLines = new ArrayList<ArrayList<String>>();



		/* check if every label has a message per language */
		/* stepping through the labels */
		for (int i = 0; i < myLabels.size(); i++){
			myLine = new ArrayList<String>();

			String myLabel = myLabels.get(i).getMessagename();
			myLine.add(myLabel);

			/* stepping through the languages */
			for (int j = 0; j < myLanguages.size(); j++){
				String myLanguage = myLabels.get(i).getLanguageid();

				/* step through the messages looking for a match */
				for (int k = 0; k < myMessages.size(); k++){
					uMessage myMessage = myMessages.get(i);
					if (myMessage.getLanguageid().equals(myLanguage)
						&& myMessage.getMessagename().equals(myLabel)){

						myLine.add(myMessage.getMessagetext());

						/* for speed purposes remove the message from the list */
						myMessages.remove(i);

						/* found it */
						isFound = true;
						break;
					}
				}
				if (!isFound){
					myLine.add("Error (8000): Message with label " + myLabel + " not found for " + myLanguage + "!");
				}
				isFound = false;
			}
		}

		/* check if the text for language1 is not the same as language 2
		Two scenario's
			- same label different language
			- different label
		*/
		for (int i = 0; i < myMessages.size(); i++){
			uMessage myMessage1 = myMessages.get(i);
			for (int j = 0; j < myMessages.size(); j++){
				uMessage myMessage2 = myMessages.get(j);

				/* if the message text is the same raise a warning */
				if (myMessage1.getMessagetext().equals(myMessage2.getMessagetext())){
					myMessage2.setWarning("Suggestion (8001): Messagetext is same as text in <LIBRARY>.<LANGUAGE>.<LABEL>: " + myMessage1.getLibraryname() +  "." + myMessage1.getLanguageid() + "." + myMessage1.getMessagename() + "!");
					myMessage2.saveObject();
				}
			}
		}
	}

	public void checkUsage(){
		/* this function checks if a label is actually used in a proc */
	}
}
