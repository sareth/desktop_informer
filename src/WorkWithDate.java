import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class WorkWithDate {
	/* gets now week number */
	public static int getWeekNumber() {
		Calendar calendar = new GregorianCalendar();
		int weekOfMonth = calendar.get(calendar.WEEK_OF_MONTH);

		return weekOfMonth;
	}

	/* gets string name of current day of week */
	public static String getDayName() {
		Calendar calendar = new GregorianCalendar();
		int dayOfWeekNumber = calendar.get(calendar.DAY_OF_WEEK);
		String dayOfTheWeek = null;
		if (dayOfWeekNumber == 2) {
			dayOfTheWeek = "Понедельник";
		} else if (dayOfWeekNumber == 3) {
			dayOfTheWeek = "Вторник";
		} else if (dayOfWeekNumber == 4) {
			dayOfTheWeek = "Среда";
		} else if (dayOfWeekNumber == 5) {
			dayOfTheWeek = "Четверг";
		} else if (dayOfWeekNumber == 6) {
			dayOfTheWeek = "Пятница";
		} else if (dayOfWeekNumber == 7) {
			dayOfTheWeek = "Суббота";
		} else if (dayOfWeekNumber == 1) {
			dayOfTheWeek = "Воскресение";
		}
		return dayOfTheWeek;
	}

	/* gets name of day */
	public static String getDayNameOfNumber(int number) {

		String dayOfTheWeek = null;
		if (number == 2) {
			dayOfTheWeek = "Понедельник";
		} else if (number == 3) {
			dayOfTheWeek = "Вторник";
		} else if (number == 4) {
			dayOfTheWeek = "Среда";
		} else if (number == 5) {
			dayOfTheWeek = "Четверг";
		} else if (number == 6) {
			dayOfTheWeek = "Пятница";
		} else if (number == 7) {
			dayOfTheWeek = "Суббота";
		} else if (number == 1) {
			dayOfTheWeek = "Воскресение";
		}
		return dayOfTheWeek;
	}

	/* gets number of current day */
	public static int getDayNumber() {
		Calendar calendar = new GregorianCalendar();
		int dayOfWeekNumber = calendar.get(calendar.DAY_OF_WEEK);

		return dayOfWeekNumber;
	}

	public static Date getCurrentWeekBegins() {
		Calendar calendar = new GregorianCalendar();
		//calendar = getFirstWeekDayTime(calendar);
		Date dayOfWeekBegins = getFirstWeekDayTime(calendar);;

		return dayOfWeekBegins;
	}
	public static Date getCurrentWeekEnds() {
		Calendar calendar = new GregorianCalendar();
		//calendar = getFirstWeekDayTime(calendar);
		Date dayOfWeekBegins = getLastWeekDayTime(calendar);;

		return dayOfWeekBegins;
	}

	public static Date getFirstWeekDayTime(Calendar calendar) {
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd"); // getting current day of week
															
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);		
		// getting first day of current week
		switch (weekDay) {
		case 1:
			weekDay = -6;
			break;
		case 2:
			weekDay = 0;
			break;
		case 3:
			weekDay = -1;
			break;
		case 4:
			weekDay = -2;
			break;
		case 5:
			weekDay = -3;
			break;
		case 6:
			weekDay = -4;
			break;
		case 7:
			weekDay = -5;
			break;
		}
		// ~
		calendar.add(Calendar.DAY_OF_MONTH, weekDay);
		
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		//setting time of 0:00:00
		calendar.set(year, month, day, 0,
				0, 0);
		
		return calendar.getTime();
	}/*
	 * Output: getTime() : Mon Jul 25 00:00:00 MSD 2011 
	 * in Millis : 1311537600000
	 */// :~
	//method gets date and time of last in week day of given calendar date
	public static Date getLastWeekDayTime(Calendar calendar) {
		// first day
		Date firstDay = getFirstWeekDayTime(calendar);
		calendar = new GregorianCalendar();
		calendar.setTime(firstDay);
		// Получаем последний день недели. Ниженаписанную строку необходимо
		// будет удалить, если захотите получить максимальное время.
		// Получаем последний день недели +1 день. Если сразу необходимо
		// получить воскресенье, то ставим число 6 вместо 7.
		calendar.add(Calendar.DAY_OF_MONTH, 6);
		// gets last week day
		long lastWeekDay = calendar.getTimeInMillis() - 1;
		calendar.setTimeInMillis(lastWeekDay);
		Date lastDay = calendar.getTime();
		return lastDay;
	}/*
	 * Output: getTime() : Sun Jul 31 59:59:59 MSD 2011 
	 * in Millis : 1312142399999
	 */// :~
	//function adds 7 days to given date
	public static Date incrementSevenDays(Date date){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		calendar.set(year, month, day, 0,
				0, 0);
		return calendar.getTime();
	}
	//function subtracts 7 days of given date
	public static Date decrementSevenDays(Date date){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -7);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		calendar.set(year, month, day, 0,
				0, 0);
		return calendar.getTime();
	}
}
