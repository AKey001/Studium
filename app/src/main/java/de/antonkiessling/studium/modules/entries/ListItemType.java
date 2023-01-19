package de.antonkiessling.studium.modules.entries;

public enum ListItemType {
    CAPTION(1),
    MODULE(2);

    private int id;

    ListItemType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
