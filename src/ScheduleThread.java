import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class ScheduleThread extends Thread{
	private Thread gettime;
	static int currentClassNumber = 0;
	private static Date lastShow = new java.util.Date(0);
	
	static boolean changed = false;
	public void start() {
		gettime = new Thread(this);
		gettime.setDaemon(true);
		gettime.start();
	}

	public void run() {
		while (true) {
			try {
				gettime.sleep(1000);
			} catch (InterruptedException ie) {
				continue;
			}
			try {
				
				doSomething(true);
				
				

			} catch (ParseException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void doSomething(boolean isThread) throws ParseException, IOException {
		currentClassNumber = GetCurrentClass.getClassNumber(new java.util.Date());
		int row = -1;
		Date nowDate = new java.util.Date();
		ShowFrameSchedule.tableSecondWeek.setRowSelectionAllowed(false);
		row = getRow(nowDate);//2 3
		
		//not now, search next
		if(row==-1){
			row = getNextClassRow(nowDate);
			if (row!=-1){
			changed = true;
			}
			else
			{
				NoClassesInThatWeek(nowDate, isThread);
			}
		}
		else//now class go
		{
			changetableSecondWeek(row);
			setToolTipAndNotifyNow(row, nowDate, isThread);
		}
		
		//if searched next class, set specific notify
		if(changed==true){
		changetableSecondWeek(row);
		setToolTipAndNotify(row, nowDate, isThread);
		}
		
	}

	private static void NoClassesInThatWeek(Date showingDate, boolean isThread) {
		Date tenMinAgo;
		Calendar c = new GregorianCalendar();
		c.setTime(lastShow);
		c.add(Calendar.MINUTE, 30);
		tenMinAgo = c.getTime();
		String message = (String) "У вас больше нет пар на этой неделе!";
		String messageTray = (String) "У вас больше нет пар на этой неделе!";
		// changing tray message
		ShowFrameSchedule.trayIcon.setToolTip(messageTray);
		// show notification popup
		if (isThread==true){
			if (ShowFrameSchedule.notification == null && showingDate.after(tenMinAgo)) {
				ShowFrameSchedule.notification = new ShowNotificationPopup(message,"У вас больше нет пар на этой неделе!");
				lastShow = showingDate;
			}
		}else{
			if (ShowFrameSchedule.notification == null) {
				ShowFrameSchedule.notification = new ShowNotificationPopup(message,"У вас больше нет пар на этой неделе!");
				lastShow = showingDate;
			}
		}
		
	}

	private static void setToolTipAndNotifyNow(int numOfRow, Date showingDate, boolean isThread) {
		Date tenMinAgo;
		Calendar c = new GregorianCalendar();
		c.setTime(lastShow);
		c.add(Calendar.MINUTE, 30);
		tenMinAgo = c.getTime();
		String message = (String) ShowFrameSchedule.tableSecondWeek.getValueAt(numOfRow, 1)
				+ " пара в группе <b> "
				+ (String) ShowFrameSchedule.tableSecondWeek.getValueAt(numOfRow, 4)
				+ "</b> <br/>по дисциплине: <br/> \"<b>"
				+ (String) ShowFrameSchedule.tableSecondWeek.getValueAt(numOfRow, 5)
				+ "\"</b> <br/>в аудитории <b>"
				+ (String) ShowFrameSchedule.tableSecondWeek.getValueAt(numOfRow, 6) + "</b>";
		String messageTray = (String) ShowFrameSchedule.tableSecondWeek.getValueAt(numOfRow, 1)
				+ " пара в группе "
				+ (String) ShowFrameSchedule.tableSecondWeek.getValueAt(numOfRow, 4)
				+ "\nпо дисциплине: \""
				+ (String) ShowFrameSchedule.tableSecondWeek.getValueAt(numOfRow, 5)
				+ "\"\n в аудитории  "
				+ (String) ShowFrameSchedule.tableSecondWeek.getValueAt(numOfRow, 6);
		// changing tray message
		ShowFrameSchedule.trayIcon.setToolTip(messageTray);
		// show notification popup
		if (isThread==true){
			if (ShowFrameSchedule.notification == null && showingDate.after(tenMinAgo)) {
				ShowFrameSchedule.notification = new ShowNotificationPopup(message,"У вас сейчас идет пара!");
				lastShow = showingDate;
			}
		}else{
			if (ShowFrameSchedule.notification == null) {
				ShowFrameSchedule.notification = new ShowNotificationPopup(message,"У вас сейчас идет пара!");
				lastShow = showingDate;
			}
		}
		
	}

	private static void setToolTipAndNotify(int numOfRow, Date showingDate, boolean isThread) throws ParseException {
		Date tenMinAgo;
		Calendar c = new GregorianCalendar();
		c.setTime(lastShow);
		c.add(Calendar.MINUTE, 10);
		tenMinAgo = c.getTime();
		String message = (String) ShowFrameSchedule.tableSecondWeek.getValueAt(numOfRow, 1)
				+ " пара в группе <b> "
				+ (String) ShowFrameSchedule.tableSecondWeek.getValueAt(numOfRow, 4)
				+ "</b> <br/>по дисциплине: <br/> \"<b>"
				+ (String) ShowFrameSchedule.tableSecondWeek.getValueAt(numOfRow, 5)
				+ "\"</b> <br/>в аудитории <b>"
				+ (String) ShowFrameSchedule.tableSecondWeek.getValueAt(numOfRow, 6) + "</b>";
		String messageTray = (String) ShowFrameSchedule.tableSecondWeek.getValueAt(numOfRow, 1)
				+ " пара в группе "
				+ (String) ShowFrameSchedule.tableSecondWeek.getValueAt(numOfRow, 4)
				+ "\nпо дисциплине: \""
				+ (String) ShowFrameSchedule.tableSecondWeek.getValueAt(numOfRow, 5)
				+ "\"\n в аудитории  "
				+ (String) ShowFrameSchedule.tableSecondWeek.getValueAt(numOfRow, 6);
		// changing tray message
		ShowFrameSchedule.trayIcon.setToolTip(messageTray);
		// show notification popup
		String stringtimeOfNextClass = ShowFrameSchedule.tableSecondWeek.getValueAt(numOfRow, 2).toString();
		Date timeOfNextClass = new SimpleDateFormat("dd:MM:yyyy hh:mm:ss").parse(stringtimeOfNextClass);
								
		SimpleDateFormat formatter = new SimpleDateFormat("kk:mm:ss");
		stringtimeOfNextClass = formatter.format(timeOfNextClass);
		
		String messageWhen = "Ближайшая пара будет через пару дней или после выходных";
		if(showingDate.getDay()==timeOfNextClass.getDay()){
			messageWhen = "У вас сегодня в " + stringtimeOfNextClass + " будет пара";
		}else if((showingDate.getDay()+1)==timeOfNextClass.getDay()){
			messageWhen = "У вас завтра в " + stringtimeOfNextClass + " будет пара";
		}
		if (isThread==true){
			if (ShowFrameSchedule.notification == null && showingDate.after(tenMinAgo)) {
				ShowFrameSchedule.notification = new ShowNotificationPopup(message,messageWhen);
				lastShow = showingDate;
			}
		}else{
			if (ShowFrameSchedule.notification == null) {
				ShowFrameSchedule.notification = new ShowNotificationPopup(message,messageWhen);
				lastShow = showingDate;
			}
		}
		
	}
	
	

	private static int getNextClassRow(Date nowDate) throws ParseException {
		String beginTimeDate;
		Date begindate;
		for (int i = 0; i < ShowFrameSchedule.tableSecondWeek.getRowCount(); i++){
			beginTimeDate = ShowFrameSchedule.tableSecondWeek.getValueAt(i, 2).toString();
			begindate = new SimpleDateFormat("dd:MM:yyyy hh:mm:ss").parse(beginTimeDate);
			if(nowDate.before(begindate)){
				return i;
			}
		}
		return -1;
	}

	private static int getRow(Date currentDate) throws ParseException {
		String beginTimeDate;
		String endTimeDate;
		Date begindate;
		Date enddate;
		for (int i = 0; i < ShowFrameSchedule.tableSecondWeek.getRowCount(); i++){
			beginTimeDate = ShowFrameSchedule.tableSecondWeek.getValueAt(i, 2).toString();
			endTimeDate = ShowFrameSchedule.tableSecondWeek.getValueAt(i, 3).toString();
			
			begindate = new SimpleDateFormat("dd:MM:yyyy hh:mm:ss").parse(beginTimeDate);
			enddate = new SimpleDateFormat("dd:MM:yyyy hh:mm:ss").parse(endTimeDate);
			if (currentDate.before(enddate)&&currentDate.after(begindate)){
				return i;
			}
		}
		return -1;
	}

	public static void changetableSecondWeek(final int numOfRow) {
		for (int i = 0; i < ShowFrameSchedule.tableSecondWeek.getColumnModel()
				.getColumnCount(); i++) {
			ShowFrameSchedule.tableSecondWeek.getColumnModel().getColumn(i)
					.setCellRenderer(new DefaultTableCellRenderer() {

						@Override
						public Component getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							Component cell = super
									.getTableCellRendererComponent(table,
											value, isSelected, hasFocus, row,
											column);

							if (row == numOfRow) {
								cell.setBackground(new Color(255, 206, 250));
								cell.setFont(new Font("Serif", Font.BOLD, 12));
								cell.setForeground(new Color(9, 4, 8));
							} else {
								
									cell.setBackground(new Color(211, 255, 206));
								
							}

							return cell;
						}
					});
		}
		ShowFrameSchedule.tableSecondWeek.repaint();
	}
}

