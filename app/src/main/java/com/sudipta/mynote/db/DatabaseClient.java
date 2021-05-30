package com.sudipta.mynote.db;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {


    private static DatabaseClient databaseClient;

    //our app database object
    private NoteDatabase noteDatabase;

    private DatabaseClient(Context context) {


        //creating the app database with Room database builder
        //MyToDos is the name of the database
        noteDatabase = Room.databaseBuilder(context, NoteDatabase.class, "NoteDB").build();
    }

    public static synchronized DatabaseClient getInstance(Context context) {
        if (databaseClient == null) {
            databaseClient = new DatabaseClient(context);
        }
        return databaseClient;
    }

    public NoteDatabase getAppDatabase() {
        return noteDatabase;
    }
}
