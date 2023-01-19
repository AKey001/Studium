package de.antonkiessling.studium.dashboard.commons;

import android.util.Log;

import java.util.List;

import de.antonkiessling.studium.db.Module;

public class ResultCalculator {

    public String calculateGradeAsString(List<Module> modules) {
        double grade = calculateGradeAsDouble(modules);

        if (Double.isNaN(grade)) {
            return "-.-";
        } else {
            return String.valueOf(grade);
        }

    }

    public double calculateGradeAsDouble(List<Module> modules) {
        double sumWeightingsAndGrades = 0;
        double sumWeightings = 0;

        double currentWeighting;
        double currentGrade;
        for (Module module : modules) {
            currentGrade = module.getGrade();
            currentWeighting = module.getWeighting();

            if (module.getGrade() > 0) {
                sumWeightingsAndGrades += currentGrade * currentWeighting;
                sumWeightings += currentWeighting;
            }
        }

        double result = sumWeightingsAndGrades / sumWeightings;
        double rounded = roundValue(result, 3);

        if (rounded == 0.0) {
            return Double.NaN;
        } else {
            return rounded;
        }

    }

    public String calculateAverage(List<Module> modules) {
        double counter = 0;
        double sum = 0;
        for (Module module : modules) {
            double grade = module.getGrade();
            if (grade > 0.0) {
                counter++;
                sum += grade;
            }
        }
        double average = roundValue(sum/counter, 2);
        Log.d("QWE1RTZ", "avg: " + average);
        return String.valueOf(average);
    }

    public double roundValue(double value, int decimalPlaces) {
        String roundingHelper = "1";
        for (int i = 0; i < decimalPlaces; i++) {
            roundingHelper += "0";
        }
        int roundingHelperInteger = Integer.parseInt(roundingHelper);
        double roundingHelperDouble = Integer.parseInt(roundingHelper);

        return Math.round(value * roundingHelperInteger)/roundingHelperDouble;
    }

}
