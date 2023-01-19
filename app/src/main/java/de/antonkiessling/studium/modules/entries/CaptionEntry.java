package de.antonkiessling.studium.modules.entries;

public class CaptionEntry implements ListEntry {
    private String caption;

    public CaptionEntry(String caption) {
        this.caption = caption;
    }

    @Override
    public ListItemType getType() {
        return ListItemType.CAPTION;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
