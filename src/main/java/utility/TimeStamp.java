package utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStamp {
	
	public static String getTimeStamp() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyhhmmss");
		String timestamp = dateFormat.format(date);
		return timestamp;
	}
}
