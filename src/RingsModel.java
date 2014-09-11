import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.Component;
import java.util.ArrayList;
 

public class RingsModel extends AbstractTableModel implements TableModel{
 	private static final long serialVersionUID = 1L;
 	
	private ArrayList<String[]> myData; // ��������� ������ �� ������
                                        // ������� �������� �������
    final String[] colNames = {"Номер", "Начало", "Окончание"};
    @Override
    public String getColumnName(int column) {
    	return colNames[column];
    }
 
    public RingsModel(ArrayList<String[]> myData){
        this.myData = myData;
    }
 
    @Override
    public int getRowCount(){
        return myData.size();//���������� ����� = ������ ArrayList myData
    }
 
    @Override
    public int getColumnCount(){
        return colNames.length;
    	//return myData.get(0).length;//���������� ����� = ������ ������� String � ArrayList myData
    }
 
    @Override
    public Object getValueAt(int rowIndex, int columnIndex){
        return myData.get(rowIndex)[columnIndex];
    }
 
    public void setValueAt(String[] newData){
        myData.add(newData);//��������� � ��� ArrayList ����� ������
        fireTableDataChanged();//��������� ������
    }
    
    
}