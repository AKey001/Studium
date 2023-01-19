package de.antonkiessling.studium.commons;

import android.app.Activity;

import com.google.android.material.textfield.TextInputLayout;

import de.antonkiessling.studium.R;

public class ErrorHandler {
    private Activity activity;

    public ErrorHandler(Activity activity) {
        this.activity = activity;
    }

    public void handleTextInputLayoutsEmptyError(TextInputLayout textInput) {
        if (textInput.getEditText().getText().toString().trim().equals("")) {
            showError(textInput);
        } else {
            showSuccess(textInput);
        }
    }

    private void showError(TextInputLayout textInput) {
        textInput.setErrorEnabled(true);
        textInput.setError(activity.getString(R.string.form_info_fill_out));
        textInput.setEndIconActivated(true);
    }

    private void showSuccess(TextInputLayout textInput) {
        textInput.setErrorEnabled(false);
        textInput.setEndIconActivated(false);
        textInput.setError(null);
    }

}
