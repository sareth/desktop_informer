import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GetTeachersList {
	private final File cfg = new File("properties.cfg");
	Properties prop = new Properties();
	
			
	private final String DRIVER = "com.mysql.jdbc.Driver";
	
	public String[] getTeachersList() throws FileNotFoundException, IOException{
		prop = Settings.getProperties(cfg);
		final String CONNECTION = "jdbc:mysql:"+prop.getProperty("DB_ADDRESS")+"?useUnicode=true&characterEncoding=utf8";
		
		
		String[] teachers=new String[1];
		List<String> where = new ArrayList<String>();
		
		
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
		      String query = "select distinct Teacher from timetable ORDER BY Teacher";
		            		       
		      // execute the query, and get a java resultset
		      ResultSet rs = statement.executeQuery(query);
		       
		      // iterate through the java resultset
		      while (rs.next())
		      {
		        String id = rs.getString("Teacher");
		        	        		         
		        // print the results
		        where.add(id);
		      }
		      teachers = new String[where.size()];
		      where.toArray( teachers );	
	            	
			}catch(SQLException e){
				e.printStackTrace();
			}
		return teachers;
		}
		
}