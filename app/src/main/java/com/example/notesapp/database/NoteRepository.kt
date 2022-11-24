package com.example.notesapp.database

import androidx.lifecycle.LiveData
import com.example.notesapp.models.Note

class NoteRepository(private val noteDao: NoteDao) {

    suspend fun insert(note: Note) {
        noteDao.insertNote(note)
    }

    fun getAllNotes(): LiveData<MutableList<Note>> {
        return noteDao.getAllNotes()
    }

    suspend fun delete(note: Note) {
        noteDao.deleteNote(note)
    }

    suspend fun update(id: Int, title: String, note: String) {
        noteDao.updateNote(id, title, note)
    }
}