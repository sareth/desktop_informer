import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import java.awt.Window;
import java.io.IOException;

public class ShowNotificationPopup extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JFrame frame;
	public int counter = 9;
	public ImageIcon headingIcon = new ImageIcon(getClass().getResource("/res/fray.png"));
	public ImageIcon background = new ImageIcon(getClass().getResource("/res/back_notice.png"));
	
	
	public ShowNotificationPopup(String message, String when)
	{
		String header = when;
		setResizable(false); 
        setSize(400,150);
        setContentPane(new BgPanel());
        Container cont = getContentPane();
        setLayout(new GridBagLayout());       
        GridBagConstraints constraints = new GridBagConstraints();
		/*adding heading message label with image*/
        constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1.0f;
		constraints.weighty = 1.0f;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.fill = GridBagConstraints.BOTH;
		
		JLabel headingLabel = new JLabel("<HtMl>"+header);
		BufferedImage image = new BufferedImage(headingIcon.getIconWidth(), headingIcon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        image.getGraphics().drawImage(headingIcon.getImage(), 0x00, 0x00, null);
        for (int i = 0x00; i < image.getWidth(); i++) {
            for (int j = 0x00; j < image.getHeight(); j++) {
                if (image.getRGB(i, j) == Color.white.getRGB()) {
                    image.setRGB(i, j, 0x00000000);
                }
            }
        }
        
		headingLabel.setIcon(headingIcon ); // --- use image icon you want to be as heading image.
		headingLabel.setOpaque(false);
		headingLabel.setFont(new Font("Arial", Font.BOLD, 12));
		cont.add(headingLabel,constraints);
		//heading label adding
		
		/*adding close button*/
        constraints.gridx++;
		constraints.weightx = 0f;
		constraints.weighty = 0f;
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.NORTH;
		JButton closeButton = new JButton("X");
		closeButton.setMargin(new Insets(1, 4, 1, 4));
		closeButton.setFocusable(false);
		closeButton.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e) {
	        	DiscrementOpacity discOpacity = new DiscrementOpacity ();
	    		discOpacity.start ();
	        	
	        }
	    });
		cont.add(closeButton, constraints);
		//end of close button
		
		/* added jlabel for text*/
		constraints.gridx = 0;
		constraints.gridy++;
		constraints.weightx = 1.0f;
		constraints.weighty = 1.0f;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.fill = GridBagConstraints.BOTH;
		JLabel messageLabel = new JLabel("<HtMl>"+message);
		cont.add(messageLabel, constraints);
		//message label
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setUndecorated(true);
		setAlwaysOnTop(true);
		
		setVisible(true);
		
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();// size of the screen
		Insets toolHeight = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());// height of the task bar
		//show frame right bottom of screen
		setLocation(scrSize.width - getWidth(), scrSize.height - toolHeight.bottom - getHeight());
		
		
	}
	/*create JPanel to change background image of frame*/
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
	/* if frame close, frame smoothly fades  */
	class DiscrementOpacity extends Thread{
		private Thread gettime;
		public void start(){
			gettime = new Thread(this);
			gettime.start();
		}
		public void run(){
			while(true){
				try {gettime.sleep (100);}
				catch(InterruptedException ie) {continue;}
				float opacityFrame = getOpacity();
				//
		        if (counter==0) {
		        	ShowFrameSchedule.notification =null;
		        	dispose();
		        	gettime.stop();
		           	
		        }
		        else{
		        	counter--;
		        	opacityFrame = (opacityFrame-0.1f);
			        setOpacity(opacityFrame);
		        }
				
		        
			}
			
		}
	}//end of opacity disc
}




