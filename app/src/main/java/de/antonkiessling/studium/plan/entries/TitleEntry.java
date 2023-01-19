package de.antonkiessling.studium.plan.entries;

public class TitleEntry implements Entry {
   private static final TableEntryType TYPE = TableEntryType.TITLE;
   private String title;

    public TitleEntry() {
    }

    public TitleEntry(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String day) {
        this.title = day;
    }

    @Override
    public TableEntryType getType() {
        return TableEntryType.TITLE;
    }
}
