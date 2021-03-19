package com.blackdev.misakoassignment.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities =  {RegionData.class},version = 1,exportSchema = false)
public abstract class RoomDB extends RoomDatabase  {
    private static RoomDB database;
    private static String DATABASE_NAME = "region_database";

    public synchronized static RoomDB getInstance(Context context) {
        if(database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),RoomDB.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract RegionDao regionDao();
}
