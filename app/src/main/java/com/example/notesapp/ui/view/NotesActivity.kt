package com.example.notesapp.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.R
import com.example.notesapp.adapter.NotesAdapter
import com.example.notesapp.adapter.OnNoteItemClickListener
import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.database.NoteRepository
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.models.Note
import com.example.notesapp.ui.viewmodel.NotesFactory
import com.example.notesapp.ui.viewmodel.NotesViewModel

class NotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NotesViewModel
    private lateinit var factory: NotesFactory
    private lateinit var notesRepository: NoteRepository
    private lateinit var notesAdapter: NotesAdapter

    private var newNoteLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                if (result.data?.getParcelableExtra("newNote") as Note? is Note) {
                    viewModel.add(result.data?.getParcelableExtra("newNote")!!)
                }
            }
        }

    private var updateNoteLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                if (result.data?.getParcelableExtra("updatedNote") as Note? is Note) {
                    viewModel.updateNote(result.data?.getParcelableExtra("updatedNote")!!)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initial()
        setListeners()
    }

    private fun initial() {
        notesRepository = NoteRepository(NoteDatabase.getNoteDatabase(this).getNoteDao())
        factory = NotesFactory(notesRepository)
        viewModel = NotesViewModel(notesRepository)
        notesAdapter = NotesAdapter(this, object : OnNoteItemClickListener {
            override fun onItemClick(note: Note) {
                updateNoteLauncher.launch(
                    Intent(
                        this@NotesActivity,
                        AddNoteActivity::class.java
                    ).apply {
                        putExtra("updateNote", note)
                    })
            }

            override fun onLongItemClick(note: Note, view: CardView) {
                val popupMenu = PopupMenu(this@NotesActivity, view)
                popupMenu.inflate(R.menu.note_popup)
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.deleteNote -> viewModel.deleteNote(note)
                    }
                    true
                }
                popupMenu.show()
            }
        })


        viewModel.notes.observe(this) { notes ->
            Log.d("Notes", notes?.toString() ?: "null")
            notes?.let {
                notesAdapter.updateNotes(it)
            }
        }

        binding.rvNotes.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = notesAdapter
        }
    }

    private fun setListeners() {
        binding.btnAdd.setOnClickListener {
            newNoteLauncher.launch(Intent(this, AddNoteActivity::class.java))
        }

    }


}