package com.example.notesapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.database.NoteRepository
import com.example.notesapp.models.Note
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NoteRepository) : ViewModel() {

    private var _notes: MutableLiveData<List<Note>> = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> = _notes

    fun add(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

    fun getAllNotes() = viewModelScope.launch {
        _notes = repository.getAllNotes() as MutableLiveData<List<Note>>
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        repository.update(note.id!!, note.title!!, note.note!!)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }
}