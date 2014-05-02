/* uRegister can only be inherited from therefore it is abstract */
/**
 * Abstract class for global, component and other variables
 * @author Dennis Vorst
 * @version 1.0
 */

abstract class uRegister extends generalFunctions {
	String variableType = new String();
	String variableName = new String();

	uRegister(String myType, String myName){
		this.variableType = myType;
		this.variableName = myName;
	}
	uRegister(){
	}

	/* getters and setters */
	/**
	 * Return the name of the variable
	 */
	public String getVariablename(){
		return this.variableName;
	}
	/**
	 * Set the name of the variable
	 */
	public void setVariablename(String myName){
		this.variableName = myName;
	}

	/**
	 * Set the datatype of the variable
	 */
	public void setVariabletype(String myType){
		this.variableType = myType;
	}
	/**
	 * Get the datatype of the variable
	 */
	public String getVariabletype(){
		return this.variableType;
	}
}


