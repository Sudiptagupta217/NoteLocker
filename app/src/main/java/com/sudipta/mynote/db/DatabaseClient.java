package com.sudipta.mynote.db;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    private static NoteDatabase nodeDatabase;

    public static NoteDatabase databaseClient(Context context) {
        if (nodeDatabase == null) {
            nodeDatabase = Room.databaseBuilder(context, NoteDatabase.class, "NoteDB")
                    .allowMainThreadQueries().build();
        }
        return nodeDatabase;
    }
}
