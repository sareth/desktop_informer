import java.util.Date;


public class Rings {
	
	public Rings(int n, Date bt, Date et){
		this.beginTime = bt;
		this.endTime = et;
		this.Number = n;
	}
	
	int Number;
	Date beginTime;
	Date endTime;
	public int getNumber() {
		return Number;
	}
	public void setNumber(int number) {
		Number = number;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
