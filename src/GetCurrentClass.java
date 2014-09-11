import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class GetCurrentClass {
	static GetRings ringsCL = new GetRings();
	
	
	public GetCurrentClass(){
		
	}
	public static int getClassNumber(Date date) throws ParseException, FileNotFoundException, IOException{
		ArrayList<Rings> ringslist = ringsCL.getRingsArray();
		
		DateFormat df = new SimpleDateFormat("HH:mm");

		// Get the date today using Calendar object.
		Date today = date;        
		String reportDate = df.format(today);
		Date dateNew = new SimpleDateFormat("HH:mm").parse(reportDate);
		
		String beginHour = String.valueOf(ringslist.get(0).getBeginTime().getHours());
		String beginMinute = String.valueOf(ringslist.get(0).getBeginTime().getMinutes()-1);
		String beginDate = beginHour+":"+beginMinute;
		Date dateBegin = new SimpleDateFormat("hh:mm").parse(beginDate);
		
		String endHour = String.valueOf(ringslist.get(ringslist.size()-1).getEndTime().getHours());
		String endMinute = String.valueOf(ringslist.get(ringslist.size()-1).getEndTime().getMinutes()-1);
		String endDate = endHour+":"+endMinute;
		Date dateEnd = new SimpleDateFormat("hh:mm").parse(endDate);
		
		if(dateNew.before(dateBegin)) return 0;
	    if(dateNew.after(dateEnd)) return 8;
		
		for (int i=1;i<ringslist.size();i++){
			
			
			endHour = String.valueOf(ringslist.get(i).getEndTime().getHours());
			endMinute = String.valueOf(ringslist.get(i).getEndTime().getMinutes()-1);
			endDate = endHour+":"+endMinute;
			dateEnd = new SimpleDateFormat("hh:mm").parse(endDate);
			
			
			if(dateNew.after(dateBegin)&&dateNew.before(dateEnd)){
				return ringslist.get(i).getNumber();	
			}
			
			beginHour = String.valueOf(ringslist.get(i).getBeginTime().getHours());
			beginMinute = String.valueOf(ringslist.get(i).getBeginTime().getMinutes()-1);
			beginDate = beginHour+":"+beginMinute;
			dateBegin = new SimpleDateFormat("hh:mm").parse(beginDate);
		}
		
		
		return 0;
	}
	
	
	
	
}
