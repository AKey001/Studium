package de.antonkiessling.studium.plan.commons;

import android.content.Context;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

import de.antonkiessling.studium.commons.SettingsManager;
import de.antonkiessling.studium.plan.timetable.Plan;
import de.antonkiessling.studium.plan.entries.TimeTableEntry;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class DataLoader {
    private CalendarMapper calendarMapper;
    private final EntryMapper entryMapper;
    private SettingsManager settingsManager;

    public DataLoader(EntryMapper entryMapper,
                      CalendarMapper calendarMapper,
                      SettingsManager settingsManager) {
        this.entryMapper = entryMapper;
        this.calendarMapper = calendarMapper;
        this.settingsManager = settingsManager;
    }

    public Plan loadCurrentWeekPlan(String url) throws IOException {
        Plan plan = new Plan();
        return loadCurrentWeekPlanFromWeb(plan, url);
    }

    public Plan loadCurrentAndNextWeekPlan(String urlCurrent, String urlNext) throws IOException {
        Plan plan = new Plan();
        plan = loadCurrentWeekPlanFromWeb(plan, urlCurrent);
        if (!settingsManager.loadEntireList()
                && (calendarMapper.getDay().equals("Freitag")) || calendarMapper.getDay().equals("Samstag") || calendarMapper.getDay().equals("Sonntag")) {
            plan = loadNextWeekPlanFromWeb(plan, urlNext);
        }
        return plan;
    }

    private Plan loadCurrentWeekPlanFromWeb(Plan plan, String currentWeekUrl) throws IOException {
        Document planDocCurrent = Jsoup.connect(currentWeekUrl).get();
        List<Element> siteElementsCurrent = planDocCurrent.getElementsByAttributeValue("bgcolor", "#ffffff").get(0).children();
        for (int i = 3; i < siteElementsCurrent.size(); i += 2) {
            String day = siteElementsCurrent.get(i).getElementsByTag("img").get(0).attr("alt");

            for (Element element : siteElementsCurrent.get(i).child(0).child(0).child(3).attr("cellpadding", "3").children()) {
                TimeTableEntry timeTableEntry = processDayData(day, element);

                if (timeTableEntry != null) {
                    switch (day) {
                        case "Montag":
                            plan.getMonday().add(timeTableEntry);
                            break;
                        case "Dienstag":
                            plan.getTuesday().add(timeTableEntry);
                            break;
                        case "Mittwoch":
                            plan.getWednesday().add(timeTableEntry);
                            break;
                        case "Donnerstag":
                            plan.getThursday().add(timeTableEntry);
                            break;
                        case "Freitag":
                            plan.getFriday().add(timeTableEntry);
                            break;
                        default:
                            plan.getOther().add(timeTableEntry);
                            break;
                    }
                }

            }
        }
        return plan;
    }

    private Plan loadNextWeekPlanFromWeb(Plan plan, String url) throws IOException {
        Document planDocNext = Jsoup.connect(url).get();
        List<Element> siteElementsNext = planDocNext.getElementsByAttributeValue("bgcolor", "#ffffff").get(0).children();
        for (int i = 3; i < siteElementsNext.size(); i += 2) {
            String day = siteElementsNext.get(i).getElementsByTag("img").get(0).attr("alt");
            if (day.equals("Montag")) {
                for (Element element : siteElementsNext.get(i).child(0).child(0).child(3).attr("cellpadding", "3").children()) {
                    TimeTableEntry timeTableEntry = processDayData(day, element);

                    if (timeTableEntry != null) {
                        plan.getNextMonday().add(timeTableEntry);
                    }
                }
                break;
            }

        }
        return plan;
    }

    private TimeTableEntry processDayData(String day, Element element) {
        try {
            String entryType = element.child(0).child(1).child(2).text();
            String time = element.child(0).child(0).child(0).text();
            String week = element.child(0).child(2).child(0).text();
            String module = element.child(0).child(2).child(1).text();
            String teacher = element.child(0).child(2).child(2).text();
            String room;
            try {
                room = element.child(0).child(2).child(3).text();
            } catch (NullPointerException e) {
                room = "web";
            }
            if (room.isEmpty()) {
                room = "web";
            }

            return new TimeTableEntry(
                    day,
                    entryType,
                    time,
                    week,
                    module,
                    teacher,
                    room
            );

        } catch (IndexOutOfBoundsException e) {
            return null;
        }

    }
}
