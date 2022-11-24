package com.example.notesapp.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.example.notesapp.adapter.NotesAdapter
import com.example.notesapp.adapter.OnNoteItemClickListener
import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.database.NoteRepository
import com.example.notesapp.databinding.ActivityAddNoteBinding
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.models.Note
import com.example.notesapp.ui.viewmodel.NotesFactory
import com.example.notesapp.ui.viewmodel.NotesViewModel
import java.time.LocalDate

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var viewModel: NotesViewModel
    private lateinit var factory: NotesFactory
    private lateinit var notesRepository: NoteRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initial()
        setListeners()
    }

    private fun initial() {
        notesRepository = NoteRepository(NoteDatabase.getNoteDatabase(this).getNoteDao())
        factory = NotesFactory(notesRepository)
        viewModel = NotesViewModel(notesRepository)
    }

    private fun setListeners() {
        binding.ibApply.setOnClickListener {
            val newNote = Note(
                null,
                title = binding.etTitle.text.toString(),
                note = binding.etNote.text.toString(),
                null
            )
            setResult(RESULT_OK, Intent().apply { putExtra("newNote", newNote) })
            finish()
        }
    }
}