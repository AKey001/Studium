package de.antonkiessling.studium.commons;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import java.util.prefs.PreferenceChangeListener;

import de.antonkiessling.studium.MainActivity;
import de.antonkiessling.studium.R;
import de.antonkiessling.studium.commons.SettingsManager;
import de.antonkiessling.studium.plan.fragments.PlanFragment;

public class SettingsFragment extends Fragment {
    private SharedPreferences prefs;
    private SharedPreferences.OnSharedPreferenceChangeListener changeListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.content_settings, container, false);

        if (savedInstanceState == null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new GeneralFragment())
                    .commit();
        }

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        changeListener = (prefs1, key) -> {
            SettingsManager settingsManager = new SettingsManager(getActivity(), getContext());
            settingsManager.synchronise();
        };
        prefs.registerOnSharedPreferenceChangeListener(changeListener);

        return root;
    }

        public static class GeneralFragment extends PreferenceFragmentCompat {
            @Override
            public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
                setPreferencesFromResource(R.xml.root_preferences, rootKey);
            }

        }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("QWE1RTZ", "PAUSE");
        prefs.unregisterOnSharedPreferenceChangeListener(changeListener);
    }
}