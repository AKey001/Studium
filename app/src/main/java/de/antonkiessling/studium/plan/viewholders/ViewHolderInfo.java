package de.antonkiessling.studium.plan.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.antonkiessling.studium.R;

public class ViewHolderInfo extends RecyclerView.ViewHolder {
    private TextView textViewInfo;


    public ViewHolderInfo(@NonNull View itemView) {
        super(itemView);
        textViewInfo = (TextView) itemView.findViewById(R.id.info);
    }

    public TextView getTextViewInfo() {
        return textViewInfo;
    }

}
