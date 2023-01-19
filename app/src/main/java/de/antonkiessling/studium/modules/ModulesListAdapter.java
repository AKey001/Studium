package de.antonkiessling.studium.modules;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

import de.antonkiessling.studium.R;
import de.antonkiessling.studium.db.Module;
import de.antonkiessling.studium.modules.entries.CaptionEntry;
import de.antonkiessling.studium.modules.entries.ListEntry;
import de.antonkiessling.studium.modules.entries.ListItemType;
import de.antonkiessling.studium.modules.viewholders.ViewHolderCaption;
import de.antonkiessling.studium.modules.viewholders.ViewHolderModule;
import de.antonkiessling.studium.plan.viewholders.ViewHolderInfo;
import de.antonkiessling.studium.plan.viewholders.ViewHolderTitle;

public class ModulesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ListEntry> items;
    private Fragment fragment;


    public ModulesListAdapter(List<ListEntry> dataSet, Fragment fragment) {
        items = dataSet;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case 1:
                View view1 = inflater.inflate(R.layout.list_item_module_caption, parent, false);
                viewHolder = new ViewHolderCaption(view1);
                break;
            case 2:
            default:
                View view = inflater.inflate(R.layout.list_item_module, parent, false);
                viewHolder = new ViewHolderModule(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ListEntry entry = items.get(position);

        switch (entry.getType()) {
            case CAPTION:
                ViewHolderCaption holderCaption = (ViewHolderCaption) holder;
                CaptionEntry captionEntry = (CaptionEntry) entry;
                holderCaption.getTextViewCaption().setText(captionEntry.getCaption());
                break;
            case MODULE:
                ViewHolderModule holderModule = (ViewHolderModule) holder;
                Module module = (Module) entry;

                String moduleName = module.getName();
                double grade = module.getGrade();
                int weighting = module.getWeighting();

                holderModule.getTextViewModule().setText(moduleName);
                holderModule.getTextViewWeighting().setText(String.valueOf(weighting));
                if (grade > 0) {
                    holderModule.getTextViewGrade().setText(String.valueOf(grade));
                } else {
                    holderModule.getTextViewGrade().setText("-.-");
                }
                break;
        }

        if (entry.getType() == ListItemType.MODULE) {
            holder.itemView.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable("module", (Serializable) items.get(position));

                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.action_modules_list_to_modules_edit, bundle);
            });
        }


//        if (position == 0) {
//            holder.getTextViewGroup().setText(semesterGroupTopic);
//            holder.getTextViewGroup().setVisibility(View.VISIBLE);
//        } else if (module.getSemester() != items.get(position - 1).getSemester()) {
//            holder.getTextViewGroup().setText(semesterGroupTopic);
//            holder.getTextViewGroup().setVisibility(View.VISIBLE);
//        } else {
//            holder.getTextViewGroup().setVisibility(View.GONE);
//        }

    }

    @Override
    public int getItemViewType(int position) {
//        Module currentModule = items.get(position);
//        Module previousModule = items.get(position - 1);
//
//        if (position == 0) {
//            return ModuleListItemType.CAPTION.getId();
//        } else if (currentModule.getSemester() != previousModule.getSemester()) {
//            return  ModuleListItemType.CAPTION.getId();
//        } else {
//            return ModuleListItemType.MODULE.getId();
//        }
        return items.get(position).getType().getId();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
