import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

public class GetSchedule {
	
	
	private static final File cfg = new File("properties.cfg");
	static Properties prop = new Properties();
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	static ArrayList<Rings> rings = new ArrayList<Rings>();
	
	
	public static ArrayList<String[]> getSchedule(String item, int weekNum, Date weekbegins) throws FileNotFoundException, IOException{
		prop = Settings.getProperties(cfg);
		final String CONNECTION = "jdbc:mysql:"+prop.getProperty("DB_ADDRESS")+"?useUnicode=true&characterEncoding=utf8";
		//String[] teachers=new String[1];
		ArrayList<String[]> where = new ArrayList<String[]>();
		rings = GetRings.getRingsArray();
		
		try{
			Class.forName(DRIVER).newInstance();
		}catch (InstantiationException e){
			e.printStackTrace();
		}catch (IllegalAccessException e){
			e.printStackTrace();
		}catch (ClassNotFoundException e){
				e.printStackTrace();
		}
		//get data from database table timetable
		try(Connection connection = DriverManager.getConnection(CONNECTION, prop.getProperty("DB_USER"), prop.getProperty("DB_PASS"));
	            Statement statement = connection.createStatement()){
			// our SQL SELECT query. 
		      // if you only need a few columns, specify them by name instead of using "*"
		      String query = "select * from timetable where Teacher=\"" + item + "\" and WeekNumber=" + weekNum + " ORDER BY DayNumber, LessonNumber";
		            		       
		      // execute the query, and get a java resultset
		      ResultSet rs = statement.executeQuery(query);
		       
		      // iterate through the java resultset
		      while (rs.next())
		      {
		        String group = rs.getString("Group");
		        String dayName = rs.getString("DayName");
		        String classSubject = rs.getString("ClassSubject");
		        String room = rs.getString("room");
		        String lessonNumber = rs.getString("LessonNumber");
		        int dayNumber = rs.getInt("DayNumber")-1;
		        int lessonInt = Integer.valueOf(lessonNumber);
		        
		        Calendar c = new GregorianCalendar();
				c.setTime(weekbegins);
				GregorianCalendar currentLessonCal = new GregorianCalendar();
				currentLessonCal = (GregorianCalendar) c;
				
				int day = c.get(Calendar.DAY_OF_MONTH);
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				
				Date currentLessonBegins = null;
				Date currentLessonEnds = null;
				
				
				
				int BeginTimeHour = rings.get(lessonInt - 1).getBeginTime().getHours();
				int BeginTimeMinute = rings.get(lessonInt - 1).getBeginTime().getMinutes();
				int EndTimeHour = rings.get(lessonInt - 1).getEndTime().getHours();
				int EndTimeMinute = rings.get(lessonInt - 1).getEndTime().getMinutes();
				
				currentLessonCal.set(year, month, day, BeginTimeHour,
						BeginTimeMinute, 0);
				currentLessonCal.add(Calendar.DAY_OF_MONTH, dayNumber);
				currentLessonBegins = currentLessonCal.getTime();
				
				currentLessonCal.set(year, month, day, EndTimeHour,
						EndTimeMinute, 0);
				currentLessonCal.add(Calendar.DAY_OF_MONTH, dayNumber);
				currentLessonEnds = currentLessonCal.getTime();
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy kk:mm:ss",myDateFormatSymbols);
		        // add results
		        where.add(new String[]{dayName, lessonNumber, sdf.format(currentLessonBegins), sdf.format(currentLessonEnds), group, classSubject, room});
		      }
		      	
	            	
			}catch(SQLException e){
				e.printStackTrace();
			}
		return where;
		}
	private static DateFormatSymbols myDateFormatSymbols = new DateFormatSymbols() {

		@Override
		public String[] getMonths() {
			return new String[] { "января", "февраля", "марта", "апреля",
					"мая", "июня", "июля", "августа", "сентября", "октября",
					"ноября", "декабря" };
		}

	};
}
