package com.example.notesapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.database.NoteRepository
import com.example.notesapp.models.Note
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NoteRepository) : ViewModel() {

    val notes: LiveData<MutableList<Note>> = repository.getAllNotes()

    fun add(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        repository.update(note.id!!, note.title!!, note.note!!)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }
}