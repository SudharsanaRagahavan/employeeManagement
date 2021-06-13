package com.emp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.emp.domain.Schedule;

public class EmployeeUtil {

	public static Date convertDate(String date) throws ParseException {
		SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd");
		Date dt_1 = objSDF.parse(date);
		return dt_1;
	}

	public static List<Schedule> updateSchedule(List<Schedule> schedules) {
		List<Schedule> scheduleList = new ArrayList<Schedule>();
		for (Schedule schedule : schedules) {
			for (Schedule schedule2 : scheduleList) {
				if (!(schedule.getStartDate().equals(schedule2.getStartDate())
						&& schedule.getTime().equals(schedule2.getTime()))) {
					scheduleList.add(schedule);
					break;
				}
			}
			if (scheduleList.isEmpty()) {
				scheduleList.add(schedule);
			}
		}
		return scheduleList;
	}

}
