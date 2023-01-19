package de.antonkiessling.studium.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import de.antonkiessling.studium.db.Module;
import de.antonkiessling.studium.db.ModuleDAO;

@Database(entities = {Module.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ModuleDAO moduleDAO();
}
