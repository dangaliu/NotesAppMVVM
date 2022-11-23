package com.example.notesapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.ItemNotesBinding
import com.example.notesapp.models.Note

class NotesAdapter(private val context: Context, val listener: OnNoteItemClickListener) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private val allNotes = ArrayList<Note>()
    private val notes = ArrayList<Note>()

    inner class NotesViewHolder(val binding: ItemNotesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = ItemNotesBinding.inflate(LayoutInflater.from(context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = notes[holder.adapterPosition]
        with(holder.binding) {
            tvNoteTitle.text = note.title
            tvNoteText.text = note.note
            tvNoteDate.text = note.date

            itemRootLayout.setOnClickListener {
                listener.onItemClick(note)
            }

            itemRootLayout.setOnLongClickListener {
                listener.onLongItemClick(note, itemRootLayout)
                true
            }
        }
    }

    fun updateNotes(newNotes: ArrayList<Note>) {
        allNotes.clear()
        allNotes.addAll(newNotes)

        notes.clear()
        notes.addAll(allNotes)
    }

    fun filterList(search: String) {

        notes.clear()

        for (item in allNotes) {
            if (item.title?.lowercase()
                    ?.contains(search.lowercase()) == true || item.note?.lowercase()
                    ?.contains(search.lowercase()) == true
            ) {
                notes.add(item)
            }
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}