package com.example.taskmaster;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {Details.class}, version = 1)
// this is the class of database to create it
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();

}
