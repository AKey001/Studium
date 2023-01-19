package de.antonkiessling.studium.plan.entries;

public class TimeTableEntry implements Entry {
    private String day;
    private String entryType;
    private String time;
    private String week;
    private String module;
    private String lecturer;
    private String room;

    public String getDay() {
        return day;
    }

    public String getEntryType() {
        return entryType;
    }

    public String getTime() {
        return time;
    }

    public String getWeek() {
        return week;
    }

    public String getModule() {
        return module;
    }

    public String getLecturer() {
        return lecturer;
    }

    public String getRoom() {
        return room;
    }

    public TimeTableEntry(String day, String entryType, String time, String week, String module, String lecturer, String room) {
        this.day = day;
        this.entryType = entryType;
        this.time = time;
        this.week = week;
        this.module = module;
        this.lecturer = lecturer;
        this.room = room;
    }

    @Override
    public TableEntryType getType() {
        return TableEntryType.ENTRY;
    }

}
