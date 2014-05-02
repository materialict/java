//package src;

public class keyValuePair{
	/*
	This class is basically a container for a keypart and a value part.
	combined with an order number. Using this it should be possible to retrieve
	the tag information and the tag content.
	*/

	private String myKey = new String();
	private String myValue = new String();
	private int myOrder = 0;

	keyValuePair(int myOrder, String myKey, String myValue){
		System.out.println("Wegschrijven van keyValuePair " + myKey +" -> "+ myValue);

		this.setOrder(myOrder);
		this.setKey(myKey);
		this.setValue(myValue);


	}

	public void setValue(String myValue){
		this.myValue = myValue;
	}

	public String getValue(){
		return(this.myValue);
	}
	public void setKey(String myKey){
		this.myKey = myKey;
	}

	public String getKey(){
		return(this.myKey);
	}

	public void setOrder(int myOrder){
		this.myOrder = myOrder;
	}

	public int getOrder(){
		return(this.myOrder);
	}

}
