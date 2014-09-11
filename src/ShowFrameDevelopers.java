import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.html.HTMLEditorKit;



public class ShowFrameDevelopers extends JFrame{
	public ImageIcon background = new ImageIcon(getClass().getResource("/res/back_notice.png"));
	public ImageIcon javaImg = new ImageIcon(getClass().getResource("/res/java.png"));
	public ImageIcon javaEclipse = new ImageIcon(getClass().getResource("/res/eclipse.png"));
		public ShowFrameDevelopers(){
			super();
			createGUI();
		}
		public void createGUI(){
			setSize(400,200);
			setTitle("Разработчики");
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setUndecorated(true);
			addMouseListener(new MouseListener(){

				@Override
				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub
					ShowFrameSchedule.developers=null;
					dispose();
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}});
			
			setContentPane(new BgPanel());
			
			JLabel labelName = new JLabel("Денис 'Sareth' Зорин");
			JLabel labelJobTitle = new JLabel("программист");
			JLabel labelWithParticipation = new JLabel("при участии кофе, печенек и любимой жены");
			JLabel labelAlso = new JLabel("а так же при помощи");
			JLabel labeltxtEclipse = new JLabel("Eclipse Kepler IDE");
			JLabel labeltxtJava = new JLabel("Java Runtime Environment");
			JLabel labelEclipse = new JLabel(javaEclipse);
			JLabel labelJava = new JLabel(javaImg);
		    
		    GridBagConstraints c=new GridBagConstraints();
		    getContentPane().setLayout(new GridBagLayout ());
		    
		    c.anchor = GridBagConstraints.CENTER;
		    c.weighty = 1.0;  //Button area and message area have equal height.
		    c.gridwidth = GridBagConstraints.REMAINDER;
		    
		    getContentPane().add(labelName,c);
		    getContentPane().add(labelJobTitle,c);
		    getContentPane().add(labelWithParticipation,c);
		    getContentPane().add(labelAlso,c);
		    c.weightx = 1.0;  //Add/remove buttons have equal width.
		    c.gridwidth = 1;
		    getContentPane().add(labelJava,c);
		    c.gridwidth = GridBagConstraints.REMAINDER; 
		    getContentPane().add(labelEclipse,c);
		    c.weightx = 1.0;  //Add/remove buttons have equal width.
		    c.gridwidth = 1;
		    getContentPane().add(labeltxtJava,c);
		    c.gridwidth = GridBagConstraints.REMAINDER; 
		    getContentPane().add(labeltxtEclipse,c);
		    setLocationRelativeTo(null);
		    setVisible(true);
		}
	class BgPanel extends JPanel{
	    public void paintComponent(Graphics g){
	        	        
	        BufferedImage image = new BufferedImage(background.getIconWidth(), background.getIconHeight(),
	        BufferedImage.TYPE_INT_ARGB);
	        image.getGraphics().drawImage(background.getImage(), 0x00, 0x00, null);
	        for (int i = 0x00; i < image.getWidth(); i++) {
	            for (int j = 0x00; j < image.getHeight(); j++) {
	                if (image.getRGB(i, j) == Color.white.getRGB()) {
	                    image.setRGB(i, j, 0x00000000);
	                }
	            }
	        }
	        g.drawImage(image, 0, 0, 500, 500, null); 
	    }
	}
}
