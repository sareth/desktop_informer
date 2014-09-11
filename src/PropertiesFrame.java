import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;

public class PropertiesFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	final JTextField user = new JTextField(30);
	final JTextField pass = new JTextField(30);
	final JTextField address = new JTextField(30);
	public File file;
	
	public PropertiesFrame(File f) {
		file = f;
		frame = new JFrame("Настройки");
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		
		
		JButton button = new JButton("OK");
		
		button.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e) {
	        	
	        	Settings.saveSettings(file, user.getText(), pass.getText(),
						address.getText());
	        	
	        	try {
					new ShowFrameTeacherChoice();
					
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException | UnsupportedLookAndFeelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        	frame.dispose();
	        	
	        	
	        	
	            
	        }});
		
		user.setText("denchik");
		pass.setText("E4cfYcvZA7pLpD9z");
		address.setText("//46.61.143.135/denchik");
		frame.setContentPane(panel);
		panel.add(new JLabel("Пользователь базы"));
		panel.add(user);
		panel.add(Box.createHorizontalGlue());
		panel.add(new JLabel("Пароль пользователя базы"));
		panel.add(pass);
		panel.add(Box.createHorizontalGlue());
		panel.add(new JLabel("Адрес базы"));
		panel.add(address);
		panel.add(button);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowListener() {

			public void windowActivated(WindowEvent event) {

			}

			public void windowClosed(WindowEvent event) {

			}

			public void windowClosing(WindowEvent event) {
				Object[] options = { "Да", "Нет!", "Отмена" };
				int n = JOptionPane.showOptionDialog(event.getWindow(),
						"Сохранить настройки перед выходом?", "Подтверждение",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if (n == 0) {
					event.getWindow().setVisible(false);
					Settings.saveSettings(file, user.getText(), pass.getText(),
							address.getText());
					frame.dispose();
					System.exit(0);
				}
				if (n == 1){
					frame.dispose();
					System.exit(0);
				}
				
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		frame.setSize(new Dimension(400, 300));
		pack();
		frame.setVisible(true);
	}

}
