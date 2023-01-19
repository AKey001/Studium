package de.antonkiessling.studium.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import de.antonkiessling.studium.modules.entries.ListEntry;
import de.antonkiessling.studium.modules.entries.ListItemType;

@Entity
public class Module implements Serializable, ListEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "grade")
    private double grade;

    @ColumnInfo(name = "weighting")
    private int weighting;

    @ColumnInfo(name = "semester")
    private int semester;

    public Module(String name, double grade, int weighting, int semester) {
        this.name = name;
        this.grade = grade;
        this.weighting = weighting;
        this.semester = semester;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getGrade() {
        return grade;
    }

    public int getWeighting() {
        return weighting;
    }

    public int getSemester() {
        return semester;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public void setWeighting(int weighting) {
        this.weighting = weighting;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    @Override
    public ListItemType getType() {
        return ListItemType.MODULE;
    }
}
