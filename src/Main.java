import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.swing.UnsupportedLookAndFeelException;

public class Main {
	public static String TEACHER_VALUE;
	public static String DB_ADDRESS;
	public static String DB_USER;
	public static String DB_PASS;
	public static File cfg = new File("./properties.cfg");
	public static File teachercfg = new File("./teacher.cfg");


	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, AWTException, UnsupportedLookAndFeelException {

		
		boolean exists = cfg.exists();
		if (exists != true) {
			new PropertiesFrame(cfg);
		} else {
			Properties p = null;
			Properties t = null;
			try {
				p = Settings.getProperties(cfg);
				t = Settings.getProperties(teachercfg);
			} catch (IOException e) {
				cfg.createNewFile();
				teachercfg.createNewFile();
			}
			
			new ShowFrameSchedule(t.get("TEACHER").toString());
		}

	}
}
