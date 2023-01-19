package de.antonkiessling.studium.plan.timetable;

import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.antonkiessling.studium.R;
import de.antonkiessling.studium.plan.commons.EntryMapper;
import de.antonkiessling.studium.plan.entries.Entry;
import de.antonkiessling.studium.plan.entries.InfoEntry;
import de.antonkiessling.studium.plan.entries.TitleEntry;
import de.antonkiessling.studium.plan.entries.TimeTableEntry;
import de.antonkiessling.studium.plan.viewholders.ViewHolderInfo;
import de.antonkiessling.studium.plan.viewholders.ViewHolderTableEntry;
import de.antonkiessling.studium.plan.viewholders.ViewHolderTableHead;
import de.antonkiessling.studium.plan.viewholders.ViewHolderTitle;

public class PlanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Entry> entries;
    private final EntryMapper entryMapper;

    public PlanAdapter(List<Entry> entries, EntryMapper entryMapper) {
        this.entries = entries;
        this.entryMapper = entryMapper;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case 1:
                View view1 = inflater.inflate(R.layout.table_item_title, parent, false);
                viewHolder = new ViewHolderTitle(view1);
                break;
            case 2:
                View view2 = inflater.inflate(R.layout.table_item_head, parent, false);
                viewHolder = new ViewHolderTableHead(view2);
                break;
            case 3:
                View view3 = inflater.inflate(R.layout.table_item_entry, parent, false);
                viewHolder = new ViewHolderTableEntry(view3);
                break;
            case 4:
            default:
                View view = inflater.inflate(R.layout.table_item_info, parent, false);
                viewHolder = new ViewHolderInfo(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Entry entry = entries.get(position);

        switch(entry.getType()) {
            case TITLE:
                ViewHolderTitle holderTitle = (ViewHolderTitle) holder;
                TextView title = holderTitle.getTextViewTitle();

                TitleEntry titleEntry = (TitleEntry) entry;
                title.setText(titleEntry.getTitle());
                break;
            case TABLE_HEAD:
                ViewHolderTableHead holderTableHead = (ViewHolderTableHead) holder;
                TextView timeH = holderTableHead.getTextViewTime();
                TextView moduleH = holderTableHead.getTextViewModule();
                TextView roomH = holderTableHead.getTextViewRoom();
                TextView lecturerH = holderTableHead.getTextViewLecturer();

                timeH.setText(R.string.list_item_header_time);
                moduleH.setText(R.string.list_item_module);
                roomH.setText(R.string.list_item_room);
                lecturerH.setText(R.string.list_item_lecturer);
                break;
            case ENTRY:
                ViewHolderTableEntry holderTableEntry = (ViewHolderTableEntry) holder;
                TextView time = holderTableEntry.getTextViewTime();
                TextView module = holderTableEntry.getTextViewModule();
                TextView room = holderTableEntry.getTextViewRoom();
                TextView lecturer = holderTableEntry.getTextViewLecturer();

                TimeTableEntry tableEntry = (TimeTableEntry) entry;

                String timeOutput = entryMapper.mapTimeEntry(tableEntry.getTime());
                Spanned moduleOutput = entryMapper.mapModuleEntry(tableEntry.getEntryType(), tableEntry.getModule());

                time.setText(timeOutput);
                module.setText(moduleOutput);
                room.setText(tableEntry.getRoom());
                lecturer.setText(tableEntry.getLecturer());
                break;
            case INFO:
                InfoEntry infoEntry = (InfoEntry) entry;
                ViewHolderInfo holderInfo = (ViewHolderInfo) holder;
                TextView info = holderInfo.getTextViewInfo();

                info.setText(infoEntry.getInfo());
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        return entries.get(position).getType().getId();
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

}
