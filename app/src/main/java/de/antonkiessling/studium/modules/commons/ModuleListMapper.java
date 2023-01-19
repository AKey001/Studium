package de.antonkiessling.studium.modules.commons;

import java.util.ArrayList;
import java.util.List;

import de.antonkiessling.studium.db.Module;
import de.antonkiessling.studium.modules.entries.CaptionEntry;
import de.antonkiessling.studium.modules.entries.ListEntry;

public class ModuleListMapper {

    public List<ListEntry> mapModulesToEntries(List<Module> modules) {
        List<ListEntry> entries = new ArrayList<>();

        for (int i = 0; i < modules.size(); i++) {
            Module currentModule = modules.get(i);

            if (i == 0) {
                String caption = modules.get(i).getSemester() + ". Semester";
                entries.add(new CaptionEntry(caption));
            } else if (currentModule.getSemester() != modules.get(i - 1).getSemester()) {
                String caption = modules.get(i).getSemester() + ". Semester";
                entries.add(new CaptionEntry(caption));
            }
            entries.add(currentModule);
        }

        return entries;
    }

}
