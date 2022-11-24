package com.example.notesapp.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.database.NoteRepository
import com.example.notesapp.models.Note
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Calendar

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


    fun getDate(): String {
        return LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
    }
}