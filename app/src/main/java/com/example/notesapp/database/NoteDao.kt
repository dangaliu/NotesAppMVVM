package com.example.notesapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notesapp.models.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Query("select * from notes order by id")
    fun getAllNotes(): LiveData<MutableList<Note>>

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("update notes set title = :title, note = :note where id = :id")
    suspend fun updateNote(id: Int, title: String, note: String)
}