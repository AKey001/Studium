package de.antonkiessling.studium.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import de.antonkiessling.studium.R;
import ru.noties.jlatexmath.JLatexMathDrawable;
import ru.noties.jlatexmath.JLatexMathView;

public class HelpFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.content_help, container, false);

        String latex =
                "\\begin{displaymath}\n" +
                "    \\mathnormal{N_p} = \\frac{\\sum\\limits_{j=1}^{x}(w_j * \\mathnormal{N_j})}{\\sum\\limits_{j=1}^{x} w_j}\n" +
                "\\end{displaymath}\n" +
                "    \\\\\\\\" +
                "    $\\mathnormal{N_j} $ \\dots Note\\: der\\: Modulprüfung\\: des\\: Moduls\\: $ j $ \\\\ \n" +
                "    $\\mathnormal{w_j} $ \\dots Wichtungsfaktor\\: des\\: Moduls\\: $ j $ \\\\ \n" +
                "    $\\mathnormal{x} $ \\dots Anzahl\\: der\\: Module\\: ";

        JLatexMathView jLatexMathView = root.findViewById(R.id.latex_math_view);
        jLatexMathView.setLatex(latex);


        return root;
    }


}
