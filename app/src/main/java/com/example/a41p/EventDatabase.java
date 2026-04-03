package com.example.a41p;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Event.class}, version = 1)
public abstract class EventDatabase extends RoomDatabase {
    public abstract EventDao eventDao();

    private static EventDatabase INSTANCE;
    public static EventDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    EventDatabase.class,
                    "event_database"
            ).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
