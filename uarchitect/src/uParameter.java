//package src;

/* java persistance */
import java.io.Serializable;


public class uParameter implements Serializable{
	/**
	 * A parameter
	 *
	 */

	private String parameterType = new String();
	private String parameterName = new String();
	private String parameterDirection = new String();

	uParameter(String myLine){
		/*
		 * Process the info
		 */
		this.getParameterDefinition(myLine);
	}

	private void getParameterDefinition(String myLine){
		/**
		 * This function strips the string in several datatypes
		 */

		int pointer = -1;
		/*
		 * Search for the : colon to determine direction
		 */
		pointer = myLine.indexOf(":", 0);
		this.setParameterDirection(myLine.substring(pointer + 1).trim());

		myLine = myLine.substring(0, pointer);

		/*
		 * Look for the space between type and name
		 */
		pointer = myLine.indexOf(" ", 0);
		this.setParameterName(myLine.substring(pointer).trim());
		this.setParameterType(myLine.substring(0, pointer));
	}

	/*** getters and setters ***/
	public void setParameterType(String myType){
		this.parameterType = myType;
	}
	public String getParameterType(){
		return this.parameterType;
	}

	public void setParameterName(String myName){
		this.parameterName = myName;
	}
	public String getParameterName(){
		return this.parameterName;
	}

	public void setParameterDirection(String myDirection){
		this.parameterDirection = myDirection;
	}
	public String getParameterDirection(){
		return this.parameterDirection;
	}
}
