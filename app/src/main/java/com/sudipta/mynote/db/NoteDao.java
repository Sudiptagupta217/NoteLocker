package com.sudipta.mynote.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NoteDao {

    @Insert
   void addNote(Note note);

    @Query("Select * from note ORDER BY id DESC")
    List<Note> getAllNotes();

    @Update
    void updateNote(Note note);

}
