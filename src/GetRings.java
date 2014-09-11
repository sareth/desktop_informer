import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class GetRings {
	private static final File cfg = new File("properties.cfg");
	static Properties prop = new Properties();
	
			
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	
	public static ArrayList<String[]> getRings() throws FileNotFoundException, IOException{
		prop = Settings.getProperties(cfg);
		final String CONNECTION = "jdbc:mysql:"+prop.getProperty("DB_ADDRESS")+"?useUnicode=true&characterEncoding=utf8";
		
		//init
		ArrayList<Rings> rings = new ArrayList<Rings>();
		ArrayList<String[]> ringsOut = new ArrayList<String[]>();
		DateFormat dateFormat = new SimpleDateFormat("HH:mm"); 
		
		//get
		rings = getRingsArray();
		
		//rewrite
		
		for(Rings item:rings)
		{
			ringsOut.add(
					new String[]
						{
						String.valueOf(item.getNumber()), 
						String.valueOf(dateFormat.format(item.getBeginTime())), 
						String.valueOf(dateFormat.format(item.getEndTime()))
						}
					);
		}
		
		return ringsOut;
		}
	
	public static ArrayList<Rings> getRingsArray() throws FileNotFoundException, IOException{
		prop = Settings.getProperties(cfg);
		final String CONNECTION = "jdbc:mysql:"+prop.getProperty("DB_ADDRESS")+"?useUnicode=true&characterEncoding=utf8";
		ArrayList<Rings> ringslist = new ArrayList<Rings>();
		try{
			Class.forName(DRIVER).newInstance();
		}catch (InstantiationException e){
			e.printStackTrace();
		}catch (IllegalAccessException e){
			e.printStackTrace();
		}catch (ClassNotFoundException e){
				e.printStackTrace();
		}
		
		try(Connection connection = DriverManager.getConnection(CONNECTION, prop.getProperty("DB_USER"), prop.getProperty("DB_PASS"));
	            Statement statement = connection.createStatement()){
				// our SQL SELECT query. 
		      // if you only need a few columns, specify them by name instead of using "*"
		      String query = "select * from rings";
		            		       
		      // execute the query, and get a java resultset
		      ResultSet rs = statement.executeQuery(query);
		      DateFormat dateFormat = new SimpleDateFormat("HH:mm"); 
		      // iterate through the java resultset
		      
		      
		      while (rs.next())
		      {
		        int number = rs.getInt("number");
		        Date beginTime = rs.getTime("begin_time");
		        Date endTime = rs.getTime("end_time");
		        
		        // add results
		        Rings ringsObj = new Rings(number, beginTime, endTime);
		        ringslist.add(ringsObj);
		      }
		      	
	            	
			}catch(SQLException e){
				e.printStackTrace();
			}
				
		
		return ringslist;
	}
}
