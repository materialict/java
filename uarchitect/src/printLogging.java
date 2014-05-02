import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
/* error handling */
import java.io.IOException;


public class printLogging {

	String logFile = new String("C:/dvo1/out/log/output.log");
	String snippetFile = new String("C:/dvo1/out/log/snippet.log");
	String levelFile = new String("C:/dvo1/out/log/level.log");


	printLogging(ArrayList<uCodeLine> codeLines){
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(this.logFile, true));

			for (int i = 0; i < codeLines.size(); i++){
				out.write(codeLines.get(i).getLinenumberInString() + " : (" + codeLines.get(i).getAtIndentations() + ") " + codeLines.get(i).getIndentations() + codeLines.get(i).getProcessedLine());
				out.newLine();
			}

			out.close();
		} catch (IOException e) {
			System.out.println("[Logging] Error writing to logfile!! (24)");
		}
	}

	printLogging(String message, boolean append){
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(this.logFile, append));

			out.write(message);
			out.newLine();

			out.close();
		} catch (IOException e) {
			System.out.println("[Logging] Error writing [String] to logfile!! (37)");
		}
	}

	printLogging(String message, boolean append, String snippetFile){
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(snippetFile, append));

			out.write(message);
			out.newLine();

			out.close();
		} catch (IOException e) {
			System.out.println("[Logging] Error writing [String] to snippetfile!! (37)");
		}
	}



	printLogging(String message){
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(this.logFile, true));

			out.write(message);
			out.newLine();

			out.close();
		} catch (IOException e) {
			System.out.println("[Logging] Error writing [String] to logfile!! (50)");
		}
	}

	printLogging(String message, String file){
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(this.levelFile, true));

			out.write(message);
			out.newLine();

			out.close();
		} catch (IOException e) {
			System.out.println("[Logging] Error writing [String] to logfile!! (63)" + file);
		}
	}

	printLogging(ArrayList<uCodeLine> codeLines, String fileName, String objectName, String pathName ){
		try {

			//String myFile = new String("C:/dvo1/out/log/include/" + fileName + ".log");
			String myFile = new String(pathName + fileName + ".log");
			BufferedWriter out = new BufferedWriter(new FileWriter(myFile, true));

			/* print the additional information */
 			out.write("Gevonden in : " + objectName);
			out.newLine();

			/* write a seperator */
 			out.write("********** code lines **********");
			out.newLine();

			/* write the snippetlines */
			for (int i = 0; i < codeLines.size(); i++){
 				out.write(codeLines.get(i).getLinenumberInString() + " : (" + codeLines.get(i).getAtIndentations() + ") " + codeLines.get(i).getIndentations() + codeLines.get(i).getProcessedLine());
				out.newLine();
			}

			out.close();
		} catch (IOException e) {
			System.out.println(pathName + fileName + ".log");
			System.out.println("[Logging] Error writing to logfile!! (78)");
		}
	}

}
