package de.antonkiessling.studium.modules.commons;

import android.app.Activity;

import com.google.android.material.textfield.TextInputLayout;

import de.antonkiessling.studium.dashboard.commons.ModuleInputException;
import de.antonkiessling.studium.db.Module;

public class ModuleMapper {
    private boolean error = false;
    private Activity activity;

    public ModuleMapper(Activity activity) {
        this.activity = activity;
    }

    public Module mapModule(TextInputLayout textInputModule, TextInputLayout textInputGrade,
                            TextInputLayout textInputWeighting, TextInputLayout textInputSemester)
            throws ModuleInputException {
        String module = textInputModule.getEditText().getText().toString();;
        if (module.equals("")) {
            error = true;
        }

        double grade = 0.0;
        String gradePlaceholder = textInputGrade.getEditText().getText().toString();
        if (!gradePlaceholder.equals("") && Double.parseDouble(gradePlaceholder) <= 5.0 && Double.parseDouble(gradePlaceholder) > 0.0) {
            grade = Double.parseDouble(gradePlaceholder);
        }

        int weighting = 0;
        if (textInputWeighting.getEditText().getText().toString().equals("")) {
            error = true;
        } else {
            weighting = Integer.parseInt(textInputWeighting.getEditText().getText().toString());
        }

        int semester = 1;
        if (textInputSemester.getEditText().getText().toString().equals("")) {
            error = true;
        } else {
            semester = Integer.parseInt(textInputSemester.getEditText().getText().toString());
        }

        if (error) {
            throw new ModuleInputException();
        }

        return new Module(module, grade, weighting, semester);
    }



}
