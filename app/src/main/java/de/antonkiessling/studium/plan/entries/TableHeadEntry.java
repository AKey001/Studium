package de.antonkiessling.studium.plan.entries;

public class TableHeadEntry implements Entry {
    private static final TableEntryType TYPE = TableEntryType.TABLE_HEAD;
    private String time;
    private String module;
    private String room;
    private String teacher;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public TableEntryType getType() {
        return TableEntryType.TABLE_HEAD;
    }
}
