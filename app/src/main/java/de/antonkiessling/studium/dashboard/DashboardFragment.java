package de.antonkiessling.studium.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.room.Room;

import java.util.List;

import de.antonkiessling.studium.R;
import de.antonkiessling.studium.db.Module;
import de.antonkiessling.studium.db.ModuleDAO;
import de.antonkiessling.studium.db.AppDatabase;
import de.antonkiessling.studium.dashboard.commons.ResultCalculator;

public class DashboardFragment extends Fragment {
    private AppDatabase db;
    private ModuleDAO moduleDAO;
    private TextView textViewCountedGrade;
    private TextView textViewNotCountedGrade;
    private TextView textViewAverage;
    private TextView textViewAllModules;
    private TextView textViewGradedModules;
    private TextView textViewWeightedModules;
    private TextView textViewWeightedAndGradedModules;
    private TextView textViewSemester;
    private TextView textViewAverageOfSemester;
    private TextView textViewCalculationOfSemester;
    private Button btnHelp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.content_dashboard, container, false);

        btnHelp = root.findViewById(R.id.buttonHelp);
        textViewCountedGrade = root.findViewById(R.id.countedScore);
        textViewNotCountedGrade = root.findViewById(R.id.notCountedScore);
        textViewAverage = root.findViewById(R.id.scoreAvg);
        textViewAllModules = root.findViewById(R.id.savedModules);
        textViewGradedModules = root.findViewById(R.id.gradedModules);
        textViewWeightedModules = root.findViewById(R.id.weightedModules);
        textViewWeightedAndGradedModules = root.findViewById(R.id.weightedAndGradedModules);

        textViewSemester = root.findViewById(R.id.semester);
        textViewAverageOfSemester = root.findViewById(R.id.average);
        textViewCalculationOfSemester = root.findViewById(R.id.calculation);

        db = Room.databaseBuilder(getContext(),
                AppDatabase.class, "ResultCalculator")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        moduleDAO = db.moduleDAO();

        synchronise();

        btnHelp.setOnClickListener(v -> {
            NavHostFragment.findNavController(DashboardFragment.this)
                    .navigate(R.id.action_dashboard_to_help);
        });

        return root;
    }

    private void synchronise() {
        List<Module> modules = moduleDAO.getAll();

        int allModulesCounter = moduleDAO.countModules();
        int weightedModulesCounter = moduleDAO.countWeightedModules();
        int gradedModulesCounter = moduleDAO.countGradedModules();
        int weightedAndGradedModulesCounter = moduleDAO.countWeightedAndGradedModules();

        ResultCalculator resultCalculator = new ResultCalculator();
        String grade = resultCalculator.calculateGradeAsString(modules);

        String average = resultCalculator.calculateAverage(modules);

        String countedGrade = grade.substring(0, 3);
        String notCountedGrade = "";
        try {
            notCountedGrade = grade.substring(3);
            if (notCountedGrade.length() < 2) {
                notCountedGrade += "0";
            }
        } catch (StringIndexOutOfBoundsException ignored) {}

        textViewCountedGrade.setText(countedGrade);
        textViewNotCountedGrade.setText(notCountedGrade);
        textViewAverage.setText(average);

        textViewAllModules.setText("" + allModulesCounter);
        textViewGradedModules.setText("" + gradedModulesCounter);
        textViewWeightedModules.setText("" + weightedModulesCounter);
        textViewWeightedAndGradedModules.setText("" + weightedAndGradedModulesCounter);

        int maxSemester = moduleDAO.getMaxSemester();
        String averageOfSemester = "";
        String semester = "";
        String calculationOfSemester = "";
        for (int i = 1; i <= maxSemester; i++) {
            semester += i + ". Semester\n";
            double tempAverage = moduleDAO.selectAverageGradeOf(i);
            if (tempAverage == 0) {
                averageOfSemester += "-\n";
                calculationOfSemester += "-\n";
            } else {
                List<Module> modulesOfSemester = moduleDAO.getModulesOf(i);
                double tempCalculation = resultCalculator.calculateGradeAsDouble(modulesOfSemester);
                averageOfSemester += resultCalculator.roundValue(tempAverage, 2) + "\n";
                calculationOfSemester += resultCalculator.roundValue(tempCalculation, 2) + "\n";
            }
        }

        textViewSemester.setText(semester.trim());
        textViewAverageOfSemester.setText(averageOfSemester.trim());
        textViewCalculationOfSemester.setText(calculationOfSemester.trim());
    }

}