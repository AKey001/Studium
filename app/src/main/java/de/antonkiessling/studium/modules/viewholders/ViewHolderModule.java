package de.antonkiessling.studium.modules.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import de.antonkiessling.studium.R;

public class ViewHolderModule extends RecyclerView.ViewHolder {
    private final TextView textViewModule;
    private final TextView textViewGrade;
    private final TextView textViewSemester;

    public ViewHolderModule(@NonNull View view) {
        super(view);

        textViewModule = view.findViewById(R.id.textViewModule);
        textViewGrade = view.findViewById(R.id.textViewGrade);
        textViewSemester = view.findViewById(R.id.textViewWeighting);
    }

    public TextView getTextViewModule() {
        return textViewModule;
    }

    public TextView getTextViewGrade() {
        return textViewGrade;
    }

    public TextView getTextViewWeighting() {
        return textViewSemester;
    }

}
