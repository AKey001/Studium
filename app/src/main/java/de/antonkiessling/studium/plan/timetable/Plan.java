package de.antonkiessling.studium.plan.timetable;

import android.util.Log;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import de.antonkiessling.studium.plan.commons.CalendarMapper;
import de.antonkiessling.studium.plan.entries.TimeTableEntry;

public class Plan {
    private List<TimeTableEntry> monday;
    private List<TimeTableEntry> tuesday;
    private List<TimeTableEntry> wednesday;
    private List<TimeTableEntry> thursday;
    private List<TimeTableEntry> friday;
    private List<TimeTableEntry> other;
    private List<TimeTableEntry> nextMonday;
    private CalendarMapper calendarMapper;

    public Plan() {
        calendarMapper = new CalendarMapper();
        monday = new ArrayList<>();
        tuesday = new ArrayList<>();
        wednesday = new ArrayList<>();
        thursday = new ArrayList<>();
        friday = new ArrayList<>();
        other = new ArrayList<>();
        nextMonday = new ArrayList<>();
    }

    public List<TimeTableEntry> getToday() {
        int day = calendarMapper.mapLocalDateToDay(LocalDate.now());

        switch (day) {
            case 1:
                return monday;
            case 2:
                return tuesday;
            case 3:
                return wednesday;
            case 4:
                return thursday;
            case 5:
                return friday;
            default:
                return other;

        }
    }

    public List<TimeTableEntry> getTomorrow() {
        int day = calendarMapper.mapLocalDateToDay(LocalDate.now());

        switch (day) {
            case 1:
                return tuesday;
            case 2:
                return wednesday;
            case 3:
                return thursday;
            case 4:
                return friday;
            default:
                return nextMonday;

        }
    }

    public List<TimeTableEntry> getMonday() {
        return monday;
    }

    public void setMonday(List<TimeTableEntry> monday) {
        this.monday = monday;
    }

    public List<TimeTableEntry> getTuesday() {
        return tuesday;
    }

    public void setTuesday(List<TimeTableEntry> tuesday) {
        this.tuesday = tuesday;
    }

    public List<TimeTableEntry> getWednesday() {
        return wednesday;
    }

    public void setWednesday(List<TimeTableEntry> wednesday) {
        this.wednesday = wednesday;
    }

    public List<TimeTableEntry> getThursday() {
        return thursday;
    }

    public void setThursday(List<TimeTableEntry> thursday) {
        this.thursday = thursday;
    }

    public List<TimeTableEntry> getFriday() {
        return friday;
    }

    public void setFriday(List<TimeTableEntry> friday) {
        this.friday = friday;
    }

    public List<TimeTableEntry> getOther() {
        return other;
    }

    public void setOther(List<TimeTableEntry> other) {
        this.other = other;
    }

    public List<TimeTableEntry> getNextMonday() {
        return nextMonday;
    }
}
