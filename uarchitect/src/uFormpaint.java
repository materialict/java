// <BIN>1A</BIN> = the seperator of the individual objects, both open and close tags
// 1E = Entity
// 1F = Field
// 3 = start of width
// 4 = height
// 5 = vertical repetition
// 8 = horizontal repetition
// 9 == unknown
// 2 = name of entity or field
// Attention: labels are not inclosed in 1A

import java.util.ArrayList;
/* java persistance */
import java.io.Serializable;

class uFormpaint implements Serializable{

	/* the graphical definition of the component */
	private ArrayList<String> myLines = new ArrayList<String>();

	uFormpaint(String myPaint){
		/* replace the XML specific tags like &lt;*/
		myPaint = this.cleanupPaint(myPaint);

		/* 1. remove the carriage returns and stuff */
		myPaint = myPaint.replaceAll("<CR/><BR>", "");

		/* 2. create individuel lines */
		this.createPaintlines(myPaint);
	}

	private String cleanupPaint(String myLine){
		/* some XML specific translations are also made in the formpaint
		they need to be corrected first
		*/
		myLine = myLine.replaceAll("&lt;", "<");
		myLine = myLine.replaceAll("&gt;", ">");
		myLine = myLine.replaceAll("&amp;", "&");
		myLine = myLine.replaceAll("&quot;", "\"");
		myLine = myLine.replaceAll("&apos;", "'");
		myLine = myLine.replaceAll("&LT;", "<");
		myLine = myLine.replaceAll("&GT;", ">");
		myLine = myLine.replaceAll("&AMP;", "&");
		myLine = myLine.replaceAll("&QUOT;", "\"");
		myLine = myLine.replaceAll("&APOS;", "'");

		return myLine;
	}

	private void createPaintlines(String myPaint){
		/* Step 1: create an array of uCodeLine objects from the string of code */
		String myValue = null;
		String searchStringBreak = "<BR>";

		while (!myPaint.isEmpty()){
			int myBreakPointer = myPaint.indexOf(searchStringBreak, 0);

			//remove the breaks
			if (myBreakPointer >= 0){
				myValue = new String(myPaint.substring(0, myBreakPointer).toUpperCase());
				myPaint =  myPaint.substring(myBreakPointer + 4, myPaint.length());
			} else {
				/* the last line */
				myValue = new String(myPaint.toUpperCase());
				myPaint = "";
			}//endif

			/* Fill the array */
			this.myLines.add(myValue);
		}//endwhile
	}

	public void getChildren(ArrayList<uxGroup> myEntities){
		/* after processing the data we need to get the parents their children  */


		/* step through the entities */
		for (int i = 0; i < myEntities.size(); i++){
			uxGroup myEntity = myEntities.get(i);

			/* Satrt looking for children */
			for (int j = 0; j < myEntities.size(); j++){
				uxGroup myChild = myEntities.get(j);

				/* if the entities are not the same start comparing */
				if (!myEntity.getEntityName().equals(myChild.getEntityName())){

					/* if the child has similar or smaller dimensions then it might be a child of this entity */
					if (myEntity.getX() <= myChild.getX()
						&& myEntity.getY() <= myChild.getY()
						&& myEntity.getEndX() >= myChild.getEndX()
						&& myEntity.getEndY() >= myChild.getEndY()){

						myEntity.addChild(myChild);
					}//endif
				}//endif
			}//endfor
		}//endfor
	}


	public void checkPaintinfo(ArrayList<uxGroup> myEntities){

		/* step through all the entities' children looking for duplicates */
		for (int i = 0; i < myEntities.size(); i++){
			uxGroup myEntity1 = myEntities.get(i);

			for (int j = 0; j < myEntity1.getChildren().size(); j++){
				uxGroup myChild1 = myEntity1.getChildren().get(j);

				for (int k = 0; k < myEntities.size(); k++){
					uxGroup myEntity2 = myEntities.get(k);

					for (int l = 0; l < myEntity2.getChildren().size(); l++){
						uxGroup myChild2 = myEntity2.getChildren().get(l);

						/* if the parents are not identical and the children are the we have a problem */
						if (!myEntity1.getEntityName().equals(myEntity2.getEntityName())
							&& myChild1.getEntityName().equals(myChild2.getEntityName())){

							/* delete the child from the entity that is seperated further */
							if ((myChild1.getPaintedorder() - myEntity1.getPaintedorder()) >
								(myChild2.getPaintedorder() - myEntity2.getPaintedorder())){
								myEntity1.removeChild(myChild1);
							} else {
								myEntity2.removeChild(myChild2);
							}
						}
					}
				}
			}
		}
	}


	public void processPaintinfo(ArrayList<uxGroup> myEntities){
		/* this function processes the individual lines of the formpaint and adds
		the info to the painted fields and entities */

		/* init */
		String myLine = null;
		String myPaintedthing = new String();
		String myProperty = null;
		String entname = new String();
		String modelname = new String();

		int width = 0;
		int height = 0;
		int totalHeight = 0; // total height of an object
		int totalWidth = 0; // total width of an object
		String name = null;
		String type = null;
		int vPointer = 0;
		int hPointer = 0;
		int entCounter = 0; // keeps track of the order the entities are found in the text

		/* get the individual lines */
		for (int i = 0; i < this.myLines.size(); i++){
			myLine = this.myLines.get(i);

			/* step through the indivual lines looking for "A1" */
			while (!myLine.isEmpty()){

				/* look for the <BIN> seperator */
				if (myLine.indexOf("<BIN>")>=0){
					/* We have found the first occurence of a separator.
					let's find out if the item is 1A or 1A1A */
					int p = myLine.indexOf("<BIN>");

					if (myLine.length() >= (p + "<BIN>1A1A</BIN>".length())
						&& myLine.substring(p, p + "<BIN>1A1A</BIN>".length()).equals("<BIN>1A1A</BIN>")){
						/* items situated next to each other */
						myPaintedthing = myLine.substring(0, myLine.indexOf("<BIN>1A1A</BIN>"));
						myLine = myLine.substring(myLine.indexOf("<BIN>1A1A</BIN>") + "<BIN>1A1A</BIN>".length());

					} else if (myLine.length() >= (p + "<BIN>1A</BIN>".length())
							&& myLine.substring(p, p + "<BIN>1A</BIN>".length()).equals("<BIN>1A</BIN>")){

						/* items situated with space in between  */
						myPaintedthing = myLine.substring(0, myLine.indexOf("<BIN>1A</BIN>"));
						myLine = myLine.substring(myLine.indexOf("<BIN>1A</BIN>")+"<BIN>1A</BIN>".length());

					}

				} else {
					/* last one */
					myPaintedthing = myLine;
					myLine = "";
				}

				/* start looking for the # values */
				while (!myPaintedthing.isEmpty()){

					if (myPaintedthing.indexOf("#", 0)>=0){
						/* found one */
						myProperty = myPaintedthing.substring(0, myPaintedthing.indexOf("#", 0));
						myPaintedthing = myPaintedthing.substring(myPaintedthing.indexOf("#", 0)+1);

					} else {
						/* last one */
						myProperty = myPaintedthing;
						myPaintedthing = "";
					}

					/* if the first character of the property is filled */
					if (myProperty.length() > 1
						&& !myProperty.substring(0,1).trim().isEmpty()){

						if(myProperty.substring(0,1).equals("1")){
							/*
							F = field
							E = entity
							A = Area Frame
							*/
							type = new String(myProperty.substring(1));
						} else if(myProperty.substring(0,1).equals("2")){
							/* name */
							name = new String(myProperty.substring(1));

							/* number 2 is the last one in the line so we put the stuff together */
							/* put the values in the entity */
							if (name != null){

								} else if (type.equals("A")){
									/* area frame */

								if (type.equals("E")){
									/* found an entity */
									entCounter++;

									/* if the entity contains a dot is is fully qualified */
									if (name.indexOf(".", 0) >=0){
										entname = name.substring(0, name.indexOf(".", 0));
										modelname = name.substring(name.indexOf(".", 0)+1);
									} else {
										entname = name;
									}

									/* add the properties and save the name */
									this.setEntityProperties(myEntities, modelname, entname, entCounter, hPointer, vPointer, width, height, totalWidth, totalHeight);
								} else if (type.equals("F")){
									/* set the field properties in the painted field object */
									this.setFieldProperties(myEntities, modelname, entname, name, hPointer, vPointer, totalWidth, totalHeight);
									/* fields and labels are solid obejcts so we need to add the width */
									hPointer += totalWidth;
								} else if (type.equals("L")){
									/* fields and labels are solid obejcts so we need to add the width */
									hPointer += totalWidth;
								} else if (type.equals("H")){
									/* header frame */
								} else if (type.equals("B")){
									/* break frame - part of print form  */
								} else if (type.equals("P")){
									/* push button */


								} else if (type.equals("C")){
									/* check box */
								} else {
									System.out.println("UFORMPAINT: Undefined type "+ type );
								}
							}

						} else if(myProperty.substring(0,1).equals("3")){
							/* horizontal repetition */
							totalWidth = Integer.valueOf(myProperty.substring(1));

						} else if(myProperty.substring(0,1).equals("4")){
							/* vertical repetition */
							totalHeight = Integer.valueOf(myProperty.substring(1));
						} else if(myProperty.substring(0,1).equals("5")){
							/* height */
							height = Integer.valueOf(myProperty.substring(1));
						} else if(myProperty.substring(0,1).equals("8")){
							/* width */
							width = Integer.valueOf(myProperty.substring(1));
						} else if(myProperty.substring(0,1).equals("9")){
							/* undefined */
						} else if(myProperty.substring(0,1).equals("L")){
							/* entity label */
						} else if(myProperty.substring(0,1).equals(":")){
							/* in case  of an area frame */


						} else if(myProperty.substring(0,1).equals(";")){
							/* unknown type */

						} else if(myProperty.substring(0,1).equals("G")){
							/* Label Font */
						} else if(myProperty.substring(0,1).equals("A")){
							/* Horizontal alignment in case of a label
							C - Center
							R - Right

							*/
						} else if(myProperty.substring(0,1).equals("V")){
							/* Vertical alignment in case of a label
							B = Bottom
							T = Top ?
							*/


						} else if(myProperty.substring(0,1).equals("R")){
							/* Repeat
							C - Repeat if not empty

							*/

						} else if(myProperty.substring(0,1).equals(">")){
							/* entity scrollbar
							N = No
							Y = Yes
							No value = default
							R = Right
							*/
						} else if(myProperty.substring(0,1).equals("<")){
							/* surpress printing when empty
							Y = Yes
							No value = default
							*/


						} else {
							/* if there are no tags than this is a component text */


							System.out.println("UFORMPAINT: No definition found for painted property |" + myProperty + "|!");
						}
					} else {
						/* count the spaces in the property and add them */
						hPointer += myProperty.length() - myProperty.trim().length();
					}//endif
				}//endwhile
			}//endwhile
			/* going down one level */
			vPointer++;
			hPointer=0;

		}//endfor
	}

	private void setEntityProperties(ArrayList<uxGroup> myEntities, String model, String entity, int counter, int x, int y, int width, int heigth, int totalWidth, int totalHeight){
		/* this function retrieves the entity by name and sets the properties like width, heigth and stuff */

		for (int i = 0; i < myEntities.size(); i++){
			if (myEntities.get(i).getEntityName().equals(entity)
				&& myEntities.get(i).getSchemaName().equals(model)){
				/* found the right entity */

				/* set the values */
				myEntities.get(i).setProperties(counter, x, y, width, heigth, totalWidth, totalHeight);
				/* leave the function */
				return;
			}

		}
		/* when we reach here something went wrong */
		System.out.println("UFORMPAINT: entity " + entity + " not found in painted objects!" );

	}

	private void setFieldProperties(ArrayList<uxGroup> myEntities, String model, String entity, String field, int x, int y, int width, int heigth){
		/* this function retrieves the field by name and sets the properties like width, heigth and stuff */

		for (int i = 0; i < myEntities.size(); i++){

			if (myEntities.get(i).getEntityName().equals(entity)
				&& myEntities.get(i).getSchemaName().equals(model)){

				/* found the right entity */
				uxGroup myEntity = myEntities.get(i);
				for (int j =0; j < myEntity.getFields().size(); j++){

					if (myEntity.getFields().get(j).getFieldName().equals(field)){

						/* fieldname is found */
						uxField myField = myEntity.getFields().get(j);
						myField.setProperties(x, y, width, heigth);
						/* leave the function */
						return;
					}
				}
			}
		}

		/* in case the combination model, entiy, field doesnot match just search for the field */
		for (int i = 0; i < myEntities.size(); i++){
			uxGroup myEntity = myEntities.get(i);
			for (int j =0; j < myEntity.getFields().size(); j++){
				if (myEntity.getFields().get(j).getFieldName().equals(field)){

					/* fieldname is found */
					uxField myField = myEntity.getFields().get(j);
					myField.setProperties(x, y, width, heigth);

					/* leave the function */
					return;
				}//endif
			}//endfor
		}//endfor

		/* when we reach here something went wrong */
		System.out.println("UFORMPAINT: field " + field + "." + entity + "." + model + " not found in painted objects!" );
	}

}