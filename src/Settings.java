import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Settings {
	public static void saveSettings(File f, String user, String pass,
			String address) {
		Properties prop = new Properties();

		prop.put("DB_ADDRESS", address);
		prop.put("DB_USER", user);
		prop.put("DB_PASS", pass);

		//System.out.println(prop.toString());
		try {
			saveProperties(f, prop);
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static Properties getProperties(File f)
			throws FileNotFoundException, IOException {
		InputStream is = new FileInputStream(f);
		Properties pref = new Properties();
		pref.load(is);
		is.close();
		return pref;
	}

	public static void saveProperties(File f, Properties p)
			throws FileNotFoundException, IOException {
		OutputStream os = new FileOutputStream(f);
		p.store(os, null);
		os.close();
	}
	
	public static void saveTeacher(File f, String teacher) {
		Properties prop = new Properties();

		prop.put("TEACHER", teacher);
				
		try {
			saveProperties(f, prop);
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
}
