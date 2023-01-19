package de.antonkiessling.studium.plan.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.antonkiessling.studium.R;

public class ViewHolderTitle extends RecyclerView.ViewHolder {
    private final TextView textViewTitle;

    public ViewHolderTitle(@NonNull View itemView) {
        super(itemView);
        this.textViewTitle = itemView.findViewById(R.id.title);
    }

    public TextView getTextViewTitle() {
        return textViewTitle;
    }
}
