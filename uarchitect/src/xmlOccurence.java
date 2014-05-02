import java.util.ArrayList;
/* java persistance */
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
/* Error logging */
import java.io.IOException;


class xmlOccurence extends xmlGeneral implements Serializable{
	/* This class contains individual occurences as they are found in the export files. */

	private ArrayList<ArrayList<String>> myOccurence = new ArrayList<ArrayList<String>>();

	xmlOccurence(){

	}

	public void setOccurence(ArrayList<ArrayList<String>> myOccurence){
		this.myOccurence = myOccurence;
	}


	public ArrayList<ArrayList<String>> getOccurence(){
		return this.myOccurence;
	}

	public void createObject(String myTablename, String myOutputPath){


		/* This function creates a specific object from the data in the occurence */
		/* ************************ */
		/* FORM DEFINITION */
		if (myTablename.equals("UFORM")){
			/* component definitions */
			uForm myObject = new uForm(this.myOccurence);

		} else if (myTablename.equals("UXGROUP")){
			/* painted entity definitions */
			uxGroup myObject = new uxGroup(this.myOccurence);

		} else if (myTablename.equals("UXFIELD")){
			/* painted field definitions */
			uxField myObject = new uxField(this.myOccurence);

		} else if (myTablename.equals("UXREGS")){
			/* Component variables */
			uxRegs myObject = new uxRegs(this.myOccurence);

		} else if (myTablename.equals("USUBS")){
			uSubs myObject = new uSubs(this.myOccurence);

		} else if (myTablename.equals("USSPEC")){
			usSpec myObject = new usSpec(this.myOccurence);

		} else if (myTablename.equals("USOPER")){
			usOper myObject = new usOper(this.myOccurence);

		} else if (myTablename.equals("USIMPL")){
			usImpl myObject = new usImpl(this.myOccurence);

		} else if (myTablename.equals("USIOPER")){
			usIoper myObject = new usIoper(this.myOccurence);

		} else if (myTablename.equals("USLINK")){
			usLink myObject = new usLink(this.myOccurence);

		/* ************************ */
		/* CONCEPTUAL SCHEMA DEFINITION */
		} else if (myTablename.equals("UCSCH")){
			/* Conceptual Schema */
			ucSch myObject = new ucSch(this.myOccurence);

		} else if (myTablename.equals("UCGROUP")){
			/* conceptual entity */
			ucGroup myObject = new ucGroup(this.myOccurence);

		} else if (myTablename.equals("UCFIELD")){
			/* conceptual fields */
			ucField myObject = new ucField(this.myOccurence);

		} else if (myTablename.equals("UCKEY")){
			/* conceptual key */
			ucKey myObject = new ucKey(this.myOccurence);

		} else if (myTablename.equals("UCRELSH")){
			/* conceptual relationships */
			ucRelsh myObject = new ucRelsh(this.myOccurence);

		/* ************************ */
		} else if (myTablename.equals("ULIBR")){
			/* Library definition */
			uLibr myObject = new uLibr(this.myOccurence);

		} else if (myTablename.equals("UGREGS")){
			/* Global registers */
			ugRegs myObject = new ugRegs(this.myOccurence);

		} else if (myTablename.equals("USMENU")){
			/* Menu definition */
			//globalMenu myObject = new globalMenu(this.myOccurence);

		} else if (myTablename.equals("UGLYPH")){
			/* Glyph - image - definition */
			//globalGlyph myObject = new globalGlyph(this.myOccurence);

		/* UNDEFINED */
		} else if (myTablename.equals("USOURCE")){
			uSource myObject = new uSource(this.myOccurence);

		} else if (myTablename.equals("UCTABLE")){
			ucTable myObject = new ucTable(this.myOccurence);

		} else if (myTablename.equals("USPARM")){
			usParm myObject = new usParm(this.myOccurence);

		} else if (myTablename.equals("USIPARM")){
			usIparm myObject = new usIparm(this.myOccurence);

		} else if (myTablename.equals("UAPPL")){

		} else if (myTablename.equals("UTPLFLD")){

		} else if (myTablename.equals("USITEM")){

		} else if (myTablename.equals("UGFLAY")){
			/* field layout definition */
			ugFlay myObject = new ugFlay(this.myOccurence);

		} else if (myTablename.equals("UGFSYN")){
			/* field syntax definition */
			ugFsyn myObject = new ugFsyn(this.myOccurence);

		} else if (myTablename.equals("UTPLFLD")){
		} else if (myTablename.equals("USILINK")){
			usIlink myObject = new usIlink(this.myOccurence);


		/* uniface 8 */
		} else if (myTablename.equals("UGFIF")){
		} else if (myTablename.equals("UAPLFRM")){



		} else {
			System.out.println("No object found for " + myTablename);
			//return null;
		}
	}
}