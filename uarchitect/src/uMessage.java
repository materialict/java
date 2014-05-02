import java.util.ArrayList;

/* Error logging */
import java.io.IOException;

/* java persistance */
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class uMessage extends generalFunctions implements Serializable{

	static String myPath = new String("C:/dvo1/OUT/DICT/UMESSAGE/");

	String libraryName = new String();
	String messageName = new String();
	String languageId = new String();
	String messageText = new String();

	ArrayList<String> myWarnings = new ArrayList<String>();

	uMessage(String library, String label, String language, String message){
		this.libraryName = library;
		this.messageName = label;
		this.languageId = language;
		this.messageText = message;

		this.saveObject();
	}

	public void saveObject() {
		try {
			this.saveObject(this.myPath, this.getFilename());
		} catch (IOException e) {
			e.printStackTrace();
		}//endtry
	}

	private String getFilename(){
		/* create a string of filenames and strip the non-authorized characters */

		String myFilename = new String(this.libraryName +"_"+ this.languageId +"_"+ this.messageName);

		/* replacing the question mark the \\ are the to escape the character */
		myFilename = myFilename.replaceAll("\\?", "");

		return myFilename;
	}

	public String getLanguageid(){
		return this.languageId;
	}
	public String getMessagename(){
		return this.messageName;
	}
	public String getMessagetext(){
		return this.messageText;
	}
	public String getLibraryname(){
		return this.libraryName;
	}

	public void setWarning(String myWarning){
		myWarnings.add(myWarning);
	}


}