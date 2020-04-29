package ru.ath.athautowatcher.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Transport.class}, version = 1, exportSchema = false)
public abstract class TransportDatabase extends RoomDatabase {

    private static final String DB_NAME = "transport.db";
    private static TransportDatabase database;
    private static final Object LOCK = new Object();

    public static TransportDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context, TransportDatabase.class, DB_NAME).build();
            }
        }
        return database;
    }

    public abstract TransportDao transportDao();
}
