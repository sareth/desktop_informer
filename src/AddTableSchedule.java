import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTable;


public class AddTableSchedule {
	
	public AddTableSchedule(){
		super();
	}
	
	public JTable createTableSchedule(String item, int week, Date weekdate) throws FileNotFoundException, IOException{
        
        //System.out.print(weekdate+"\n");
        
        ArrayList<String[]> myData = GetSchedule.getSchedule(item, week, weekdate);
        
        
        ScheduleModel model = new ScheduleModel(myData);//create model for data of timetable
        JTable table = new JTable(model);//create jtable for timetable
        return table;
        
    }
	public JTable createTableRings() throws FileNotFoundException, IOException{
		GetRings ringsCL = new GetRings();
		ArrayList<String[]> rings = ringsCL.getRings();
		
		RingsModel model = new RingsModel(rings);//create model for data of rings
		JTable table = new JTable(model);//create jtable for rings
		
		return table;
		
	}
}
