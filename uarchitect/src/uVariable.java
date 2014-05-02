/* java persistance */
import java.io.Serializable;

public class uVariable implements Serializable{
	/**
	 * A local variable is the one defined in an opperation or an entry.
	 * This variable has two properties.
	 * - A name
	 * - a datatype
	 *
	 * local variable - defined in entry or operation
	 * global variable - defined in the application
	 * component variable - defined in the component
	 * parameter - defined in the operation
	 *
	 * All have a name and a datatype
	 * Some have preceding and following characters $$ and $
	 * Some have a direction parameters
 	 * Some have a component name
	 *
	 */

	private String variableType = new String();
	private String variableName = new String();

	uVariable(String myVariable, String myDatatype){
		this.setVariableName(myVariable);
		this.setVariableType(myDatatype);
	}

	public void setVariableName(String myVariableName){
		this.variableName = myVariableName;

	}
	public String getVariableName(){
		return this.variableName;

	}

	public void setVariableType(String myVariableType){
		this.variableType = myVariableType;

	}
	public String setVariableType(){
		return this.variableType;
	}
}