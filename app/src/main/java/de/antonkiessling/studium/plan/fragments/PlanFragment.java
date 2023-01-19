package de.antonkiessling.studium.plan.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.List;

import de.antonkiessling.studium.R;
import de.antonkiessling.studium.commons.PDFDocumentType;
import de.antonkiessling.studium.commons.SettingsManager;
import de.antonkiessling.studium.plan.commons.DataController;
import de.antonkiessling.studium.plan.commons.EntryListType;
import de.antonkiessling.studium.plan.entries.Entry;

public class PlanFragment extends Fragment {
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.content_plan, container, false);
        progressBar = root.requireViewById(R.id.progressBar);
        recyclerView = root.findViewById(R.id.recyclerView);

        DataController dataController = new DataController();

        dataController.setup(getActivity(),getContext(), recyclerView, progressBar, EntryListType.STANDARD, LocalDate.now(), true);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_plan, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_day:
                NavHostFragment.findNavController(PlanFragment.this)
                        .navigate(R.id.action_navigation_plan_to_navigation_day);
                break;
            case R.id.action_buildings:
                Bundle bundle = new Bundle();
                bundle.putString("document", PDFDocumentType.PLAN.getFileName());
                NavHostFragment.findNavController(PlanFragment.this)
                        .navigate(R.id.action_plan_to_pdf, bundle);
                break;
            case R.id.action_settings:
                NavHostFragment.findNavController(PlanFragment.this)
                        .navigate(R.id.action_plan_to_settings);
                break;
        }
        return true;
    }

}