package de.antonkiessling.studium.plan.entries;

import de.antonkiessling.studium.modules.entries.ListEntry;
import de.antonkiessling.studium.modules.entries.ListItemType;

public class InfoEntry implements Entry {
    private String info;

    @Override
    public TableEntryType getType() {
        return TableEntryType.INFO;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
