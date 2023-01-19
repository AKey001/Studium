package de.antonkiessling.studium.commons;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.barteksc.pdfviewer.PDFView;

import de.antonkiessling.studium.R;

public class PDFFragment extends Fragment {
    private String file;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.content_pdf, container, false);
        PDFView pdfView = root.findViewById(R.id.pdfView);

        Bundle bundle = this.getArguments();

        if (!bundle.isEmpty()) {
            file = bundle.getString("document");
        } else {
            Toast.makeText(getContext(), getActivity().getString(R.string.loading_error), Toast.LENGTH_SHORT).show();
        }

        boolean darkMode;
        int nightModeFlag = getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlag) {
            case Configuration.UI_MODE_NIGHT_YES:
                darkMode = true;
                break;
            default:
                darkMode = false;
        }

        pdfView.fromAsset(file)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .spacing(0)
                .nightMode(darkMode)
                .load();

        return root;
    }


}
