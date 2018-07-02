package com.summer_course.utils;

import com.summer_course.database_classes.ScheduleEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DateUtils {

    public static Date getDateFromTimestamp(Long timestamp) {

        Calendar calendar = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getTimeZone("Europe/Bucharest");
        calendar.setTimeInMillis(timestamp);
        calendar.add(Calendar.MILLISECOND, timeZone.getOffset(calendar.getTimeInMillis()));

        Date date = calendar.getTime();

        return date;
    }

    public static String[] getAvailableDaysFromEventsList(List<ScheduleEvent> eventsList) {

        List<String> daysList = new ArrayList<String>();

        for (ScheduleEvent event : eventsList) {
            Date eventStartDate = event.getStartTime();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String day = dateFormat.format(eventStartDate);

            if (daysList.contains(day) == false) {
                daysList.add(day);
            }
        }

        String[] daysArray = new String[daysList.size()];
        daysArray = daysList.toArray(daysArray);

        for (int i = 0; i < daysArray.length; ++i) {
            int currentDayIndex = i + 1;
            daysArray[i] = "Day " + currentDayIndex + " - " + daysArray[i];
        }

        return daysArray;
    }

    public static ArrayList<ArrayList<ScheduleEvent>> getOrderedListOfEvents(List<ScheduleEvent> eventsList) {

        ArrayList<ArrayList<ScheduleEvent>> orderedEventsList = new ArrayList<>();

        String currentDay = "";

        for (ScheduleEvent event : eventsList) {
            Date eventStartDate = event.getStartTime();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String day = dateFormat.format(eventStartDate);

            if (day.equals(currentDay) == false) {
                ArrayList<ScheduleEvent> oneDayEventsList = new ArrayList<>();
                orderedEventsList.add(oneDayEventsList);
                currentDay = day;
            }

            orderedEventsList.get(orderedEventsList.size() - 1).add(event);
        }

        return orderedEventsList;

    }

}
