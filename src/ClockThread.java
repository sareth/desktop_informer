
public class ClockThread extends Thread {
	private Thread gettime;
	public void start() {
		gettime = new Thread(this);
		gettime.start();
	}
	public void run() {
		while(true){
			try {
				gettime.sleep(1000);
			} catch (InterruptedException ie) {
				continue;
			}
			ShowFrameSchedule.timerLabel.setText(ShowFrameSchedule.sdf.format(new java.util.Date()));
		}
	}
}
