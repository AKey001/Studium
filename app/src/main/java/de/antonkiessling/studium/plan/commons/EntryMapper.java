package de.antonkiessling.studium.plan.commons;

import android.app.Activity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import de.antonkiessling.studium.R;
import de.antonkiessling.studium.commons.SettingsManager;
import de.antonkiessling.studium.plan.timetable.Plan;
import de.antonkiessling.studium.plan.entries.Entry;
import de.antonkiessling.studium.plan.entries.InfoEntry;
import de.antonkiessling.studium.plan.entries.TableHeadEntry;
import de.antonkiessling.studium.plan.entries.TimeTableEntry;
import de.antonkiessling.studium.plan.entries.TitleEntry;

public class EntryMapper {
    private final CalendarMapper calendarMapper;
    private Activity activity;
    private SettingsManager settingsManager;

    public EntryMapper(CalendarMapper calendarMapper, Activity activity) {
        this.calendarMapper = calendarMapper;
        this.activity = activity;
        settingsManager = new SettingsManager(activity, activity.getApplicationContext());
    }

    public List<Entry> mapPlanToStandardEntryList(Plan plan) {
        List<Entry> plans = new ArrayList<>();

        if (settingsManager.loadEntireList()) {

            plans.add(new TitleEntry("Montag"));
            if (plan.getMonday().isEmpty()) {
                InfoEntry infoEntry = new InfoEntry();
                infoEntry.setInfo(activity.getString(R.string.list_entry_no_data));
                plans.add(infoEntry);
            } else {
                plans.add(new TableHeadEntry());
                plans.addAll(plan.getToday());
            }

            plans.add(new TitleEntry("Dienstag"));
            if (plan.getTuesday().isEmpty()) {
                InfoEntry infoEntry = new InfoEntry();
                infoEntry.setInfo(activity.getString(R.string.list_entry_no_data));
                plans.add(infoEntry);
            } else {
                plans.add(new TableHeadEntry());
                plans.addAll(plan.getTuesday());
            }

            plans.add(new TitleEntry("Mittwoch"));
            if (plan.getWednesday().isEmpty()) {
                InfoEntry infoEntry = new InfoEntry();
                infoEntry.setInfo(activity.getString(R.string.list_entry_no_data));
                plans.add(infoEntry);
            } else {
                plans.add(new TableHeadEntry());
                plans.addAll(plan.getWednesday());
            }

            plans.add(new TitleEntry("Donnerstag"));
            if (plan.getThursday().isEmpty()) {
                InfoEntry infoEntry = new InfoEntry();
                infoEntry.setInfo(activity.getString(R.string.list_entry_no_data));
                plans.add(infoEntry);
            } else {
                plans.add(new TableHeadEntry());
                plans.addAll(plan.getThursday());
            }

            plans.add(new TitleEntry("Freitag"));
            if (plan.getFriday().isEmpty()) {
                InfoEntry infoEntry = new InfoEntry();
                infoEntry.setInfo(activity.getString(R.string.list_entry_no_data));
                plans.add(infoEntry);
            } else {
                plans.add(new TableHeadEntry());
                plans.addAll(plan.getFriday());
            }

        } else {
            plans.add(new TitleEntry(calendarMapper.getDay()));
            if (plan.getToday().isEmpty()) {
                InfoEntry infoEntry = new InfoEntry();
                infoEntry.setInfo(activity.getString(R.string.list_entry_no_data));
                plans.add(infoEntry);
            } else {
                plans.add(new TableHeadEntry());
                plans.addAll(plan.getToday());
            }

            plans.add(new TitleEntry(calendarMapper.getNextDay()));
            if (plan.getTomorrow().isEmpty()) {
                InfoEntry infoEntry = new InfoEntry();
                infoEntry.setInfo(activity.getString(R.string.list_entry_no_data));
                plans.add(infoEntry);
            } else {
                plans.add(new TableHeadEntry());
                plans.addAll(plan.getTomorrow());

            }

        }

        return plans;
    }

    public List<Entry> mapPlanToDayEntryList(Plan plan, LocalDate localDate) {
        List<Entry> plans = new ArrayList<>();

        List<TimeTableEntry> tableEntries = plan.getOther();
        switch (localDate.getDayOfWeek()) {
            case MONDAY:
                tableEntries = plan.getMonday();
                break;
            case TUESDAY:
                tableEntries = plan.getTuesday();
                break;
            case WEDNESDAY:
                tableEntries = plan.getWednesday();
                break;
            case THURSDAY:
                tableEntries = plan.getThursday();
                break;
            case FRIDAY:
                tableEntries = plan.getFriday();
                break;
            }

        plans.add(new TitleEntry(calendarMapper.getSpecificDay(localDate)));
        if (tableEntries.isEmpty()) {
            InfoEntry infoEntry = new InfoEntry();
            infoEntry.setInfo(activity.getString(R.string.list_entry_no_data));
            plans.add(infoEntry);
        } else {
            plans.add(new TableHeadEntry());
            plans.addAll(tableEntries);
        }

        return plans;
    }

    public String mapTimeEntry(String time) {
        return time.replace("Uhr", "");
    }

    public Spanned mapModuleEntry(String entryType, String module) {
        return Html.fromHtml("<u>" + entryType + "</u><br/>" + module, Html.FROM_HTML_MODE_COMPACT);
    }
}
