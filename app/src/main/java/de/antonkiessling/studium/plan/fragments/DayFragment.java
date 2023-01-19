package de.antonkiessling.studium.plan.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;

import de.antonkiessling.studium.R;
import de.antonkiessling.studium.plan.commons.DataController;
import de.antonkiessling.studium.plan.commons.EntryListType;

public class DayFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private DataController dataController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        android.view.View root = inflater.inflate(R.layout.content_plan, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        progressBar = root.findViewById(R.id.progressBar);

        dataController = new DataController();

        executeDatePicker(dataController);

        return root;
    }


    private void executeDatePicker(DataController dataController) {
        int startDay = LocalDate.now().getDayOfMonth();
        int startMonth = LocalDate.now().getMonth().getValue();
        int startYear = LocalDate.now().getYear();

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            LocalDate localDate = LocalDate.of(year, month + 1, dayOfMonth);
            dataController.setup(getActivity(), getContext(), recyclerView, progressBar, EntryListType.DAY, localDate, false);
        }, startYear, startMonth - 1, startDay);
        datePickerDialog.create();
        datePickerDialog.show();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_day, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_day:
                executeDatePicker(dataController);
                break;

        }
        return true;
    }


}
