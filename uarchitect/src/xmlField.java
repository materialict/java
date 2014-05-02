import java.util.ArrayList;

/* java persistance */
import java.io.Serializable;

class xmlField extends xmlGeneral implements Serializable{
	private ArrayList<ArrayList<String>> fieldProperties = new ArrayList<ArrayList<String>>();

	/* This class contains the properties of the <FLD string */
	xmlField(){

	}
	xmlField(ArrayList<ArrayList<String>> myProperties){
		this.setProperties(myProperties);

	}

	public void setProperties(ArrayList<ArrayList<String>> myProperties){
			this.fieldProperties = myProperties;
	}
	public ArrayList<ArrayList<String>> getProperties(){
			return this.fieldProperties;
	}

}