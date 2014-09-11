import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class ShowFrameSchedule extends JFrame {
	private static DateFormatSymbols myDateFormatSymbols = new DateFormatSymbols() {

	@Override
	public String[] getMonths() {
		return new String[] { "января", "февраля", "марта", "апреля",
				"мая", "июня", "июля", "августа", "сентября", "октября",
				"ноября", "декабря" };
	}

};
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy kk:mm:ss",
			myDateFormatSymbols);
	static JLabel timerLabel = new JLabel(sdf.format(new java.util.Date()));
	private Image image = Toolkit.getDefaultToolkit().getImage(
			getClass().getResource("/res/owl.png"));
	private static final long serialVersionUID = 1L;
	private ArrayList<String[]> myData;
	private ScheduleModel model; //
	JTabbedPane jTabbedPane1 = new javax.swing.JTabbedPane();
	JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
	JTable jTable1 = new javax.swing.JTable();
	JScrollPane jScrollPane2 = new javax.swing.JScrollPane();
	JTable jTable2 = new javax.swing.JTable();
	JTabbedPane jtPane;
	public static JTable ringsTable;
	public GetCurrentClass currentClassNumber = new GetCurrentClass();
	public static JTable tableFirstWeek;
	public static JTable tableSecondWeek;
	public static JTable tableThirdWeek;
	public JTable tableFourWeek;
	public static TrayIcon trayIcon;
	public String Teacher;
	public static ShowNotificationPopup notification;
	public static ShowFrameDevelopers developers;
	public String message;
	public String messageTray;
	public int weekNumber = WorkWithDate.getWeekNumber();
	final SystemTray systemTray = SystemTray.getSystemTray();
	

	/*
	 * Main constructor of frame
	 */
	public ShowFrameSchedule(String teacher) throws AWTException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException,
			FileNotFoundException, IOException {
		super("Test frame");
		createGUI(teacher);
	}

	/*
	 * Painter of trayicon
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(image, 0, 0, null);
	}

	/*
	 * Method for creating GUI This is main method In this method magic happens
	 */
	public void createGUI(String item) throws AWTException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException,
			FileNotFoundException, IOException {
		UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		String titleWindows = "Расписание преподавателя " + item;
		Teacher = item;
		Image imageApp = Toolkit.getDefaultToolkit().createImage(
				getClass().getResource("/res/clock.png"));
		this.setIconImage(imageApp);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setTitle(titleWindows);
		this.setLayout(new FlowLayout());
		this.setSize(600, 300);
		// adding popup menu
		PopupMenu popup = new PopupMenu();
		// Add button to exit from tray
		MenuItem exitItem = new MenuItem("Выход");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		MenuItem showItem = new MenuItem("Показать уведомление");
		showItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ScheduleThread.doSomething(false);
				} catch (ParseException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		MenuItem settingsTeacherItem = new MenuItem("Изменить преподавателя");
		settingsTeacherItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFrame Frame = new ShowFrameTeacherChoice();
					Frame.setVisible(true);
					dispose();
					systemTray.remove(trayIcon);
				} catch (ClassNotFoundException
						| InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException
						| IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		// adding exit button on popup
		popup.add(showItem);
		popup.add(settingsTeacherItem);
		popup.add(exitItem);

		/*
		 * Now we work with System tray
		 */
		
		trayIcon = new TrayIcon(image, "Расписание", popup);
		trayIcon.setImageAutoSize(true);
		trayIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(true);
				setState(JFrame.NORMAL);
			}
		});

		/*
		 * In this place we add listener for windows folding
		 */
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
				trayIcon.displayMessage("Информер все еще запущен!",
						"Вы все еще будете получать уведомления",
						TrayIcon.MessageType.INFO);
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
				setVisible(false);
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
		// add icon on system tray
		systemTray.add(trayIcon);

		/*
		 * NOW WE WORKS WITH TABLES
		 */
		// renderer for table rings
		DefaultTableCellRenderer jTableRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				Component cell = super.getTableCellRendererComponent(table,
						value, isSelected, hasFocus, row, column);

				cell.setForeground(new Color(9, 4, 8));

				cell.setBackground(new Color(211, 255, 206));

				return cell;

			}
		};

		// renderer for tables of weeks
		DefaultTableCellRenderer jTableRenderer1 = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				Component cell = super.getTableCellRendererComponent(table,
						value, isSelected, hasFocus, row, column);
				cell.setBackground(new Color(211, 255, 206));
				return cell;
			}
		};

		/*
		 * Rings table
		 */
		// create JPanel for Rings table
		JPanel panel = new JPanel();
		AddTableSchedule rg = new AddTableSchedule();
		ringsTable = rg.createTableRings();
		ringsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		// adding Rings table in scrollpane
		JScrollPane scrollRings = new JScrollPane(ringsTable);
		ringsTable.setFocusable(false);
		ringsTable.setColumnSelectionAllowed(false);
		ringsTable.setRowSelectionAllowed(false);
		// getting number of current class for setting selection when frame
		// loads
		try {
			int classNumber = GetCurrentClass.getClassNumber(new java.util.Date());
			if(classNumber>0){
				/*ShowFrameSchedule.ringsTable.setRowSelectionInterval(classNumber - 1, 0);
				ShowFrameSchedule.ringsTable.requestFocus();
				ShowFrameSchedule.ringsTable.changeSelection(classNumber - 1, 0, false, false);
				*/
				ShowFrameSchedule.ringsTable.setRowSelectionAllowed(false);
				RingsThread.changeRingsTableSelecting(classNumber-1);
			}
				
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// setting size of scrollpane for rings table
		scrollRings.setPreferredSize(new Dimension(160, 125));
		scrollRings.setFocusable(false);

		/*
		 * Table with first week lessons
		 */
		AddTableSchedule at1 = new AddTableSchedule();
		tableFirstWeek = at1.createTableSchedule(item, (weekNumber % 2 != 0 ? 2	: 1), WorkWithDate.decrementSevenDays(WorkWithDate.getCurrentWeekBegins()));
		tableFirstWeek.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableFirstWeek.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		/*
		 * Table with second week lessons
		 */
		AddTableSchedule at2 = new AddTableSchedule();
		tableSecondWeek = at2.createTableSchedule(item, (weekNumber % 2 != 0 ? 1 : 2), WorkWithDate.getCurrentWeekBegins());
		tableSecondWeek.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableSecondWeek.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		/*
		 * Table with second week lessons
		 */
		AddTableSchedule at3 = new AddTableSchedule();
		tableThirdWeek = at3.createTableSchedule(item, (weekNumber % 2 != 0 ? 2 : 1), WorkWithDate.incrementSevenDays(WorkWithDate.getCurrentWeekBegins()));
		tableThirdWeek.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableThirdWeek.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		

		JScrollPane scrollFirst = new JScrollPane(tableFirstWeek);
		JScrollPane scrollSecond = new JScrollPane(tableSecondWeek);
		JScrollPane scrollThird = new JScrollPane(tableThirdWeek);

		// color tables with lessons
		
		for (int i = 0; i < tableFirstWeek.getColumnModel().getColumnCount(); i++) {
			tableFirstWeek.getColumnModel().getColumn(i)
					.setCellRenderer(jTableRenderer);
		}

		for (int i = 0; i < tableSecondWeek.getColumnModel().getColumnCount(); i++) {
			tableSecondWeek.getColumnModel().getColumn(i)
					.setCellRenderer(jTableRenderer);
		}
		for (int i = 0; i < tableThirdWeek.getColumnModel().getColumnCount(); i++) {
			tableThirdWeek.getColumnModel().getColumn(i)
					.setCellRenderer(jTableRenderer);
		}
		
		// color table with rings
		for (int i = 0; i < ringsTable.getColumnModel().getColumnCount(); i++) {
			ringsTable.getColumnModel().getColumn(i)
					.setCellRenderer(jTableRenderer1);
		}
		
		// create tabbed pane and adding on it scrollpanes with lessons
		jtPane = new JTabbedPane();
		jtPane.add("Прошлая неделя", scrollFirst);
		jtPane.add("Текущая неделя", scrollSecond);
		jtPane.add("Следующая неделя", scrollThird);

		jtPane.setPreferredSize(new Dimension(800, 300));

		// getting current weeknumber and selecting current week

		// jtPane.setSelectedIndex(weekNumber%2!=0 ? 0 : 1);
		jtPane.setSelectedIndex(1);
		// adding elements on panel
		panel.setLayout(new BorderLayout());
		panel.add(timerLabel, BorderLayout.NORTH);
		panel.add(jtPane, BorderLayout.EAST);
		panel.add(scrollRings, BorderLayout.WEST);
		// Showing frame with elements
		
		// Adding main menu
		try {
			// parsing xml menu
			InputStream in = getClass().getResourceAsStream("/res/menu.xml");

			XMLMenuParser xmlParser = new XMLMenuParser(in);
			xmlParser.parseXML();

			// Pulls generated from the XML menu file by its name (tag
			// <menubar>),
			// and attach to our UI
			JMenuBar mainMenu = xmlParser.getMenuBar("ourMenu");
			// Adding action listeners
			for (int i = 0; i < mainMenu.getMenuCount(); i++) {
				JMenu menu = mainMenu.getMenu(i);
				for (int j = 0; j < menu.getMenuComponentCount(); j++) {
					Component comp = menu.getMenuComponent(j);

					if (comp instanceof JMenuItem) {
						JMenuItem menuItem = (JMenuItem) comp;
						// Adding action listener for EXIT
						if (menuItem.getText().equals("Выход")) {
							menuItem.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									System.exit(0);
								}
							});
						}// exit
							// Adding action listener for Open notice
						if (menuItem.getText().equals("Открыть уведомление")) {
							menuItem.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									try {
										ScheduleThread.doSomething(false);
									} catch (ParseException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
							});
						}// open notice

						// Adding action listener for developers
						if (menuItem.getText().equals("Разработчики")) {
							menuItem.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									if (developers == null) {
										developers = new ShowFrameDevelopers();

									}
								}
							});
						}// developers
							// Adding action listener for change teacher
						if (menuItem.getText().equals("Изменить преподавателя")) {
							menuItem.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									try {
										JFrame Frame = new ShowFrameTeacherChoice();
										Frame.setVisible(true);
										dispose();
										systemTray.remove(trayIcon);
									} catch (ClassNotFoundException
											| InstantiationException
											| IllegalAccessException
											| UnsupportedLookAndFeelException
											| IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
							});
						}// change teacher
					}
				}
			}
			// setting Main menu bar
			setJMenuBar(mainMenu);

		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}

		this.add(panel);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		/*
		 * Starting thread for showing current time and selection current
		 * lessons in tables
		 */
		
		ClockThread clock = new ClockThread();
		clock.start();
		
		RingsThread ringsTh = new RingsThread();
		ringsTh.start();
		
		ScheduleThread scheduleTh = new ScheduleThread();
		scheduleTh.start();
		
	}
}
