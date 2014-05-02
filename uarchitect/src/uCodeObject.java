/** Object that contains the name of the codeobject and the code section */
/* java persistance */
import java.io.Serializable;
/* Arraylist */
import java.util.ArrayList;

/**
Class containing code objects. A code object is:
- an entry
- an operation
- a trigger

But allways a self contained set of code snippets. All the snippets combined in the right order,
make the code of the entry, operation, trigger, include or global proc.
*/

class uCodeObject extends uCode implements Serializable{



	private ArrayList<uCodeSnippet> codeSnippets = new ArrayList<uCodeSnippet>();

	uCodeObject(){}

	/**
	Method for creating the individual snippets
	*/
	private void createSnippets(ArrayList<uCodeLine> codeLines){
		boolean isVariables = false;
		boolean isParams	= false;
		boolean isIf		= false;
		boolean isWhile 	= false;
		boolean isSelect	= false; //select case statement

		/* step through the codelines */
		if (int i = 0; i < codeLines.size(); i++){
			if(codeLines.get(i).getProcessedLine.equals("PARAMS")){
				/* fill the snippet until ENDPARAMS is reached */
				while(!codeLines.get(i).getProcessedLine.equals("ENDPARAMS")){

				}


			} else if (codeLines.get(i).getProcessedLine.equals("VARIABLES")){
				/* fill the snippet until ENDVARIABLES is reached */
				while(!codeLines.get(i).getProcessedLine.equals("ENDVARIABLES")){

				}

			} else if (codeLines.get(i).getProcessedLine.substring(0,2).equals("IF")){
				/* fill the snippet until ENDIF is reached */
				while(!codeLines.get(i).getProcessedLine.equals("ENDIF")){

				}

			} else if (codeLines.get(i).getProcessedLine.substring(0,2).equals("WHILE")){
				/* fill the snippet until ENDWHILE is reached */
				while(!codeLines.get(i).getProcessedLine.equals("ENDWHILE")){

				}

			} else if (codeLines.get(i).getProcessedLine.substring(0,2).equals("SELECTCASE")){
				/* fill the snippet until END SELECTCASE is reached */
				while(!codeLines.get(i).getProcessedLine.equals("END SELECTCASE")){

			} else {


			}
		}



	}
}