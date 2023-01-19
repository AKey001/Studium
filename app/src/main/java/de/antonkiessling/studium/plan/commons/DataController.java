package de.antonkiessling.studium.plan.commons;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import de.antonkiessling.studium.commons.SettingsManager;
import de.antonkiessling.studium.plan.entries.TimeTableEntry;
import de.antonkiessling.studium.plan.timetable.Plan;
import de.antonkiessling.studium.plan.timetable.PlanAdapter;
import de.antonkiessling.studium.plan.entries.Entry;
import de.antonkiessling.studium.plan.entries.InfoEntry;

import static de.antonkiessling.studium.R.string.list_entry_no_data;

public class DataController {
    private static final String URL = "https://service.hszg.de/stundenplan/index.php?fsel=1&sel=Stundenplan&ViewType=0";

    public void setup(Activity activity, Context context, RecyclerView recyclerView, ProgressBar progressBar,
                             EntryListType listType, LocalDate localDate, boolean loadNextWeek) {
        CalendarMapper calendarMapper = new CalendarMapper();
        SettingsManager settingsManager = new SettingsManager(activity, context);

        String url = URL + "&ActiveCollege==" + calendarMapper.mapSemesterURLPart(localDate); //"Wintersemester+2021%2F22";  // ActiveCollege==Sommersemester+2021%2F22
        url += "&StudentSets=" + settingsManager.loadMatrikel();
        url += "&SemWeek=";

        progressBar.setVisibility(View.VISIBLE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        EntryMapper entryMapper = new EntryMapper(calendarMapper, activity);
        DataLoader dataLoader = new DataLoader(entryMapper, calendarMapper, settingsManager);

        InternetThread internetThread = new InternetThread();
        int currentWeek = calendarMapper.mapLocalDateToWeek(localDate);
        int nextWeek = currentWeek + 1;
        if (calendarMapper.mapLocalDateToWeek(LocalDate.of(LocalDate.now().getYear(), 12, 31)) == currentWeek) {
            nextWeek = 1;
        }

        String currentWeekUrl = url + currentWeek;
        String nextWeekUrl = url + nextWeek;
        internetThread.run(() -> {
            Plan plan;
            List<Entry> entries;
            try {
                if (loadNextWeek) {
                    plan = dataLoader.loadCurrentAndNextWeekPlan(currentWeekUrl, nextWeekUrl);
                } else {
                    plan = dataLoader.loadCurrentWeekPlan(currentWeekUrl);
                }
                switch (listType) {
                    case DAY:
                        entries = entryMapper.mapPlanToDayEntryList(plan, localDate);
                        break;
                    case STANDARD:
                    default:
                        entries = entryMapper.mapPlanToStandardEntryList(plan);
                        break;
                }

            } catch (IOException e) {
                entries = new ArrayList<>();
                InfoEntry infoEntry = new InfoEntry();
                infoEntry.setInfo(activity.getString(list_entry_no_data));
                entries.add(new InfoEntry());
            }

            List<Entry> finalEntries = entries;
            activity.runOnUiThread(() -> {
                PlanAdapter planAdapter = new PlanAdapter(finalEntries, entryMapper);
                planAdapter.setHasStableIds(true);
                recyclerView.setAdapter(planAdapter);
                progressBar.setVisibility(View.GONE);
            });
        });
    }

}
