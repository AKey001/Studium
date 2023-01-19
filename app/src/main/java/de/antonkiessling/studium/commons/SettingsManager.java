package de.antonkiessling.studium.commons;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import de.antonkiessling.studium.R;

public class SettingsManager {
    private final Activity activity;
    private SharedPreferences prefs;

    public SettingsManager(Activity activity, Context context) {
        this.activity = activity;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void synchronise() {
        String darkmode = prefs.getString(activity.getString(R.string.key_darkmode), "system");
        switch (darkmode) {
            case "dark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "system":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }
    }

    public String loadMatrikel() {
        return prefs.getString(activity.getString(R.string.key_matrikel), "IIb20");
    }

    public boolean loadEntireList() {
        return prefs.getBoolean(activity.getString(R.string.key_list_type), false);
    }

}
