package org.geeckodev.edtdroid.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Lesson {
	private Model model;
	private String begin;
	private String end;
	private String subgroup;
	private String name;
	private String prof;
	private String classroom;
	

	public Lesson(Model model, String begin, String end, String subgroup,
			String name,String prof,String classroom) {
		this.model = model;
		this.begin = begin.split(" ")[1];
		this.end = end.split(" ")[1];
		this.subgroup = subgroup;
		this.name = name;
		this.prof = prof;
		this.classroom = classroom;
	}

	public String getProf() {
		return prof;
	}

	public void setProf(String prof) {
		this.prof = prof;
	}

	public String getClassroom() {
		return classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	public boolean matchesSubgroup() {
		if (this.model.getSubgroup().contains("0")) {
			return true;
		}
		
		if (this.subgroup.length() == 0) {
			return true;
		}

		if (this.subgroup.endsWith(this.model.getSubgroup())) {
			return true;
		}

		return false;
	}

	public String getBegin() {
		return this.begin;
	}

	public String getEnd() {
		return this.end;
	}

	public String getSubgroup() {
		return this.subgroup;
	}

	public String getName() {
		return this.name;
	}

	private int[] getCurrentMinuteHour() {
		int[] values = new int[2];

		Date date = new Date();
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		values[0] = calendar.get(Calendar.HOUR_OF_DAY);
		values[1] = calendar.get(Calendar.MINUTE);

		return values;
	}

	public boolean isFinished() {
		String[] s = this.end.split(":");
		int hour = Integer.parseInt(s[0]);
		int minute = Integer.parseInt(s[1]);

		int[] values = getCurrentMinuteHour();
		int current_hour = values[0];
		int current_minute = values[1];

		return (current_hour > hour)
				|| (current_hour == hour && current_minute > minute);
	}

	public boolean isOngoing() {
		String[] b = this.begin.split(":");
		int begin_hour = Integer.parseInt(b[0]);
		int begin_minute = Integer.parseInt(b[1]);

		String[] e = this.end.split(":");
		int end_hour = Integer.parseInt(e[0]);
		int end_minute = Integer.parseInt(e[1]);

		int[] values = getCurrentMinuteHour();
		int current_hour = values[0];
		int current_minute = values[1];

		return ((begin_hour < current_hour && current_hour < end_hour)
				|| (begin_hour == current_hour && begin_minute <= current_minute) || (end_hour == current_hour && end_minute >= current_minute));

	}
	
	public boolean theNext() {
		String[] b = this.begin.split(":");
		int begin_hour = Integer.parseInt(b[0]);
		int begin_minute = Integer.parseInt(b[1]);

		String[] e = this.end.split(":");
		int end_hour = Integer.parseInt(e[0]);
		int end_minute = Integer.parseInt(e[1]);

		int[] values = getCurrentMinuteHour();
		int current_hour = values[0];
		int current_minute = values[1];

		// Il est 13h30
		// Prochain 14h30
		return ((current_hour-1 >= begin_hour)  );

	}
}
