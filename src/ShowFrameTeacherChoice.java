import java.awt.AWTException;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.UnsupportedLookAndFeelException;


@SuppressWarnings("serial")

public class ShowFrameTeacherChoice extends JFrame {
	
	public JComboBox<Object> comboBox;
	
	
	public ShowFrameTeacherChoice() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, FileNotFoundException, IOException {
        super("Test frame");
        createGUI();
   }
	
	public void createGUI() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, FileNotFoundException, IOException{
	GetTeachersList teachersCL = new GetTeachersList();
	String[] teachers = teachersCL.getTeachersList();
	comboBox = new JComboBox<Object>(teachers);
	JButton button = new JButton("OK");
	this.add(comboBox);
	ActionListener actionListener = new TestActionListener();
	
	
	button.addActionListener(actionListener);
	
	
	//UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
	
	
   
	this.add(button);
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.setTitle("Выберите преподавателя");
	this.setLayout(new FlowLayout());
	this.setSize(300, 110);
	pack();
	this.setVisible(true);
	
	
	}
	
	public class TestActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	final String item = (String)comboBox.getSelectedItem();
        	File tecaherFile = new File(Main.teachercfg.getPath());
        	/*EventQueue.invokeLater(new Runnable() {
			    @Override public void run() {
			    	try {
						new ShowFrameSchedule();
					} catch (AWTException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnsupportedLookAndFeelException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			});*/
        	//if in config teacher is not exists we write teacher in config
        	Settings.saveTeacher(tecaherFile, item);
        	Properties prop = new Properties();
        	try {
				prop = Settings.getProperties(tecaherFile);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	String teacher = prop.getProperty("TEACHER");
        	try {
				new ShowFrameSchedule(teacher);
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | AWTException | UnsupportedLookAndFeelException
					| IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	ShowFrameTeacherChoice nya = ShowFrameTeacherChoice.this;   
            nya.dispose();
        }
   }
	
	

}