package de.antonkiessling.studium.plan.commons;

import android.util.Log;

import androidx.annotation.NonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class CalendarMapper {

    public int mapLocalDateToWeek(LocalDate localDate) {
        TemporalField temporalWeek = WeekFields.of(Locale.GERMANY).weekOfYear();
        return localDate.get(temporalWeek);
    }

    public int mapLocalDateToDay(LocalDate localDate) {
        TemporalField temporalWeek = WeekFields.of(Locale.GERMANY).dayOfWeek();
        return localDate.get(temporalWeek);
    }

    public String mapSemesterURLPart(LocalDate localDate) {
        int year = localDate.getYear();

        int month = localDate.getMonth().getValue();

        if (month < 8 && month > 2) {
            Log.d("QWE1RTZ", "Sommersemester+" + year);
            return  "Sommersemester+" + year; // +2021%2F22
        } else {
            int followingYear = (year + 1) % 100;
            Log.d("QWE1RTZ", "Wintersemester+" + year + "%2F" + followingYear);
            return  "Wintersemester+" + year + "%2F" + followingYear;
        }
    }

    public String getDay() {
        CalendarMapper calendarMapper = new CalendarMapper();
        int day = calendarMapper.mapLocalDateToDay(LocalDate.now());
        switch (day) {
            case 1: return "Montag";
            case 2: return "Dienstag";
            case 3: return "Mittwoch";
            case 4: return "Donnerstag";
            case 5: return "Freitag";
            case 6: return "Samstag";
            case 7: return "Sonntag";
            default: return null;
        }
    }

    public String getSpecificDay(LocalDate localDate) {
        String date = "";
        switch (localDate.getDayOfWeek()) {
            case MONDAY:
                date = "Montag";
                break;
            case TUESDAY:
                date = "Dienstag";
                break;
            case WEDNESDAY:
                date = "Mittwoch";
                break;
            case THURSDAY:
                date = "Donnerstag";
                break;
            case FRIDAY:
                date = "Freitag";
                break;
            case SATURDAY:
                date = "Samstag";
                break;
            case SUNDAY:
                date = "Sonntag";
                break;
        }

        return date + ", " + mapToDateString(localDate);
    }

    public String mapToDateString(LocalDate localDate) {
        return localDate.getDayOfMonth() + "." + localDate.getMonth().getValue() + "." + localDate.getYear();
    }

    public String getNextDay() {
        CalendarMapper calendarMapper = new CalendarMapper();
        int day = calendarMapper.mapLocalDateToDay(LocalDate.now());
        switch (day) {
            case 5:
            case 6:
            case 7:
                return "Montag";
            case 1: return "Dienstag";
            case 2: return "Mittwoch";
            case 3: return "Donnerstag";
            case 4: return "Freitag";
            default: return null;
        }
    }

}
