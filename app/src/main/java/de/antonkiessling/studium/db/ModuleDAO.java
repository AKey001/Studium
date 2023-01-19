package de.antonkiessling.studium.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ModuleDAO {
    @Query("SELECT * FROM module ORDER BY semester DESC, weighting DESC, name ASC")
    List<Module> getAll();

    @Query("SELECT * FROM module WHERE semester = :semester")
    List<Module> getModulesOf(int semester);

    @Query("SELECT * FROM module WHERE name IN (:names)")
    List<Module> loadAllByNames(String[] names);

    @Query("SELECT * FROM module WHERE name LIKE :name LIMIT 1")
    Module findByName(String name);

    @Query("SELECT COUNT(DISTINCT name) FROM module")
    int countModules();

    @Query("SELECT COUNT(id) FROM module WHERE weighting > 0")
    int countWeightedModules();

    @Query("SELECT COUNT(id) FROM module WHERE grade > 0")
    int countGradedModules();

    @Query("SELECT COUNT(id) FROM Module WHERE weighting > 0 AND grade > 0")
    int countWeightedAndGradedModules();

    @Insert
    void insertAll(Module... modules);

    @Update
    int update(Module module);

    @Delete
    void delete(Module module);

    @Query("DELETE FROM module")
    void deleteAll();

    @Query("SELECT MAX(semester) from module")
    int getMaxSemester();

    @Query("SELECT AVG(grade) FROM module WHERE semester = :semester AND grade > 0")
    double selectAverageGradeOf(int semester);

}
