import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class timeObject{

	private String objectTime  = new String();

	timeObject(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		this.setTime(dateFormat.format(date));
	}

	public String getTime(){
		return objectTime;
	}

	public void setTime(String time){
		objectTime = time;
	}


}
