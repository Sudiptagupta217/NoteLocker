package com.sudipta.mynote.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface NoteDao {

    @Insert
    public void addNote(Note note);

    @Query("Select * from note ORDER BY id DESC")
    List<Note> getAllNotes();

    @Insert
    public void addultipleNotes(Note note);

}
