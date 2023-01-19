package de.antonkiessling.studium.plan.entries;

public enum TableEntryType {
    TITLE(1),
    TABLE_HEAD(2),
    ENTRY(3),
    INFO(4);

    private int id;

    TableEntryType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
