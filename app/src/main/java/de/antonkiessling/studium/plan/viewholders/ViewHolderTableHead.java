package de.antonkiessling.studium.plan.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.antonkiessling.studium.R;

public class ViewHolderTableHead extends RecyclerView.ViewHolder {
    private final TextView textViewTime;
    private final TextView textViewModule;
    private final TextView textViewRoom;
    private final TextView textViewLecturer;

    public ViewHolderTableHead(@NonNull View itemView) {
        super(itemView);
        this.textViewTime = itemView.findViewById(R.id.time);
        this.textViewModule = itemView.findViewById(R.id.module);
        this.textViewRoom = itemView.findViewById(R.id.room);
        this.textViewLecturer = itemView.findViewById(R.id.lecturer);
    }

    public TextView getTextViewTime() {
        return textViewTime;
    }

    public TextView getTextViewModule() {
        return textViewModule;
    }

    public TextView getTextViewRoom() {
        return textViewRoom;
    }

    public TextView getTextViewLecturer() {
        return textViewLecturer;
    }
}
