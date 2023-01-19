package de.antonkiessling.studium.modules.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.room.Room;

import com.google.android.material.textfield.TextInputLayout;

import de.antonkiessling.studium.R;
import de.antonkiessling.studium.db.Module;
import de.antonkiessling.studium.db.ModuleDAO;
import de.antonkiessling.studium.db.AppDatabase;
import de.antonkiessling.studium.commons.ErrorHandler;
import de.antonkiessling.studium.dashboard.commons.ModuleInputException;
import de.antonkiessling.studium.commons.PDFDocumentType;
import de.antonkiessling.studium.modules.commons.ModuleMapper;

public class ModulesInputFragment extends Fragment {
    private TextInputLayout textInputModule;
    private TextInputLayout textInputGrade;
    private TextInputLayout textInputWeighting;
    private TextInputLayout textInputSemester;
    private Button buttonWeighting;
    private Button buttonDelete;
    private Button buttonSave;
    private AppDatabase db;
    private ModuleDAO moduleDAO;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.content_module_input, container, false);
        ModuleMapper moduleMapper = new ModuleMapper(getActivity());
        ErrorHandler errorHandler = new ErrorHandler(getActivity());

        textInputModule = root.findViewById(R.id.textInputModule);
        textInputGrade = root.findViewById(R.id.textInputGrade);
        textInputWeighting = root.findViewById(R.id.textInputWeighting);
        textInputSemester = root.findViewById(R.id.textInputSemester);
        buttonWeighting = root.findViewById(R.id.buttonWeighting);
        buttonDelete = root.findViewById(R.id.buttonDelete);
        buttonSave = root.findViewById(R.id.buttonSave);

        synchronise();

        db = Room.databaseBuilder(getContext(),
                AppDatabase.class, "ResultCalculator").allowMainThreadQueries().build();
        moduleDAO = db.moduleDAO();

        buttonWeighting.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("document", PDFDocumentType.WEIGHTING.getFileName());
            NavHostFragment.findNavController(ModulesInputFragment.this)
                    .navigate(R.id.action_modules_input_to_pdf, bundle);
        });
        buttonSave.setOnClickListener(v -> {
            try {
                Module module = moduleMapper.mapModule(textInputModule, textInputGrade,
                        textInputWeighting, textInputSemester);
                moduleDAO.insertAll(module);

                NavHostFragment.findNavController(ModulesInputFragment.this)
                        .navigate(R.id.action_modules_input_to_modules_list);
            } catch (ModuleInputException e) {
                errorHandler.handleTextInputLayoutsEmptyError(textInputModule);
                errorHandler.handleTextInputLayoutsEmptyError(textInputWeighting);
                errorHandler.handleTextInputLayoutsEmptyError(textInputSemester);
            }
        });
        buttonDelete.setOnClickListener(v -> {
            NavHostFragment.findNavController(ModulesInputFragment.this)
                    .navigate(R.id.action_modules_input_to_modules_list);
        });

        return root;
    }

    private void synchronise() {
        try {
            Bundle module = this.getArguments();
            if (!module.isEmpty()) {
                try {
                    textInputModule.getEditText().setText(module.getString("module"));
                } catch (NullPointerException ignored) {}
                try {
                    textInputSemester.getEditText().setText(module.getString("semester"));
                } catch (NullPointerException ignored) {}
                try {
                    textInputGrade.getEditText().setText(module.getString("grade"));
                } catch (NullPointerException ignored) {}
                try {
                    textInputWeighting.getEditText().setText(module.getString("weighting"));
                } catch (NullPointerException ignored) {}
            }
        } catch (NullPointerException ignopred) {}
    }

    @Override
    public void onPause() {
        super.onPause();
        Bundle bundle = new Bundle();
        bundle.putString("module", textInputModule.getEditText().getText().toString());
        bundle.putString("semester", textInputSemester.getEditText().getText().toString());
        bundle.putString("grade", textInputGrade.getEditText().getText().toString());
        bundle.putString("weighting", textInputWeighting.getEditText().getText().toString());
        this.setArguments(bundle);
    }
}
