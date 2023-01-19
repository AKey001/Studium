package de.antonkiessling.studium.modules.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import de.antonkiessling.studium.R;
import de.antonkiessling.studium.db.AppDatabase;
import de.antonkiessling.studium.commons.PDFDocumentType;
import de.antonkiessling.studium.db.Module;
import de.antonkiessling.studium.db.ModuleDAO;
import de.antonkiessling.studium.modules.ModulesListAdapter;
import de.antonkiessling.studium.modules.commons.ModuleListMapper;
import de.antonkiessling.studium.modules.entries.ListEntry;

public class ModulesListFragment extends Fragment {
    private AppDatabase db;
    private ModuleDAO moduleDAO;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ModulesListAdapter modulesListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_modules_list, container, false);

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(view -> NavHostFragment.findNavController(ModulesListFragment.this)
                .navigate(R.id.action_modules_list_to_modules_input));


        db = Room.databaseBuilder(getContext(),
                AppDatabase.class, "ResultCalculator").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        moduleDAO = db.moduleDAO();

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        synchronise();

        return root;
    }

    private void synchronise() {
        ModuleListMapper moduleListMapper = new ModuleListMapper();
        List<Module> modules = moduleDAO.getAll();

        List<ListEntry> entries = moduleListMapper.mapModulesToEntries(modules);

        modulesListAdapter = new ModulesListAdapter(entries, this);
        recyclerView.setAdapter(modulesListAdapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_modules_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Bundle doc = new Bundle();
        switch (item.getItemId()) {
            case R.id.action_modules:
                doc.putString("document", PDFDocumentType.MODULES.getFileName());
                NavHostFragment.findNavController(ModulesListFragment.this)
                        .navigate(R.id.action_modules_list_to_pdf, doc);
                break;
            case R.id.action_weighting:
                doc.putString("document", PDFDocumentType.WEIGHTING.getFileName());
                NavHostFragment.findNavController(ModulesListFragment.this)
                        .navigate(R.id.action_modules_list_to_pdf,doc);
                break;
        }
        return true;
    }
}
