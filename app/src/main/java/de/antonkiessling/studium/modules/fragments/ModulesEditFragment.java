package de.antonkiessling.studium.modules.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.room.Room;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import de.antonkiessling.studium.R;
import de.antonkiessling.studium.db.Module;
import de.antonkiessling.studium.db.ModuleDAO;
import de.antonkiessling.studium.db.AppDatabase;
import de.antonkiessling.studium.commons.ErrorHandler;
import de.antonkiessling.studium.dashboard.commons.ModuleInputException;
import de.antonkiessling.studium.commons.PDFDocumentType;
import de.antonkiessling.studium.modules.commons.ModuleMapper;

public class ModulesEditFragment extends Fragment {
    private Module module;
    private AppDatabase db;
    private ModuleDAO moduleDAO;

    TextInputLayout textInputModule;
    TextInputLayout textInputGrade;
    TextInputLayout textInputWeighting;
    TextInputLayout textInputSemester;

    Button buttonDelete;
    Button buttonWeighting;
    Button buttonSave;


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

        Bundle bundle = this.getArguments();

        if (!bundle.isEmpty()) {
            module = (Module) bundle.getSerializable("module");
        } else {
            Toast.makeText(getContext(), getActivity().getString(R.string.loading_error), Toast.LENGTH_SHORT).show();
            NavHostFragment.findNavController(ModulesEditFragment.this)
                    .navigate(R.id.action_modules_edit_to_modules_list);
        }

        String grade = "";
        if (module.getGrade() != 0) {
            grade = String.valueOf(module.getGrade());
        }
        textInputModule.getEditText().setText(module.getName());
        textInputGrade.getEditText().setText(grade);
        textInputWeighting.getEditText().setText(String.valueOf(module.getWeighting()));
        textInputSemester.getEditText().setText(String.valueOf(module.getSemester()));

        db = Room.databaseBuilder(getContext(),
                AppDatabase.class, "ResultCalculator").allowMainThreadQueries().build();

        moduleDAO = db.moduleDAO();

        buttonWeighting.setOnClickListener(view -> {
            Bundle doc = new Bundle();
            doc.putString("document", PDFDocumentType.WEIGHTING.getFileName());
            NavHostFragment.findNavController(ModulesEditFragment.this)
                    .navigate(R.id.action_modules_edit_to_pdf, doc);
        });
        buttonSave.setOnClickListener(v -> {
            try {
                Module module = moduleMapper.mapModule(textInputModule, textInputGrade,
                        textInputWeighting, textInputSemester);
                module.setId(this.module.getId());
                moduleDAO.update(module);

                NavHostFragment.findNavController(ModulesEditFragment.this)
                        .navigate(R.id.action_modules_edit_to_modules_list);
            } catch (ModuleInputException e) {
                errorHandler.handleTextInputLayoutsEmptyError(textInputModule);
                errorHandler.handleTextInputLayoutsEmptyError(textInputWeighting);
                errorHandler.handleTextInputLayoutsEmptyError(textInputSemester);
            }
        });
        buttonDelete.setOnClickListener(view -> {
            MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getContext());
            materialAlertDialogBuilder
                    .setPositiveButton(getActivity().getString(R.string.delete), (dialog, which) -> {
                        moduleDAO.delete(module);

                        NavHostFragment.findNavController(ModulesEditFragment.this)
                                .navigate(R.id.action_modules_edit_to_modules_list);
                    })
                    .setCancelable(true)
                    .setNegativeButton(R.string.cancel, null)
                    .setMessage(module.getName() + " " + getActivity().getString(R.string.question_delete))
                    .show();
        });

        return root;
    }

}
