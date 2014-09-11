import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RingsThread extends Thread {
	private Thread gettime;
	int classNumber = 0;

	public void start() {
		gettime = new Thread(this);
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
				classNumber = GetCurrentClass
						.getClassNumber(new java.util.Date());
				if (classNumber > 0) {
					/*
					 * ShowFrameSchedule.ringsTable.setRowSelectionInterval(
					 * classNumber - 1, 0);
					 * 
					 * ShowFrameSchedule.ringsTable.changeSelection(classNumber
					 * - 1, 0, false, false);
					 */
					ShowFrameSchedule.ringsTable.setRowSelectionAllowed(false);
					changeRingsTableSelecting(classNumber - 1);
					ShowFrameSchedule.ringsTable.requestFocus();
				}
				else{
					changeRingsTableSelecting(-1);
				}

			} catch (ParseException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void changeRingsTableSelecting(final int numOfRow) {
		for (int i = 0; i < ShowFrameSchedule.ringsTable.getColumnModel()
				.getColumnCount(); i++) {
			ShowFrameSchedule.ringsTable.getColumnModel().getColumn(i)
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
		ShowFrameSchedule.ringsTable.repaint();
	}
}
