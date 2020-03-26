package edu.harvard.cs50.notes;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDAO {

    @Query("INSERT INTO notes (contents) VALUES ('New note')")
    void create(); //create table

    @Query("SELECT * FROM notes")
    List<Note> getAllNotes(); //select all table

    @Query("UPDATE notes SET contents = :contents WHERE id = :id")
    void save(String contents, int id); //update information

    @Query("DELETE FROM notes WHERE id = :id")
    void delete(int id);
}
