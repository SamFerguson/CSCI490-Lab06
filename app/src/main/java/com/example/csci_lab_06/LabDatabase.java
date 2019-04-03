package com.example.csci_lab_06;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Person.class}, version = 1)
public abstract class LabDatabase extends RoomDatabase {
    public abstract PersonDao personDao();
}
