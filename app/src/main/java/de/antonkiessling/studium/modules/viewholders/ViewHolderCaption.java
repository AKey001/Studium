package de.antonkiessling.studium.modules.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.antonkiessling.studium.R;

public class ViewHolderCaption extends RecyclerView.ViewHolder {
    private TextView textViewCaption;


    public ViewHolderCaption(@NonNull View itemView) {
        super(itemView);
        textViewCaption = (TextView) itemView.findViewById(R.id.textViewCaption);
    }

    public TextView getTextViewCaption() {
        return textViewCaption;
    }

}
