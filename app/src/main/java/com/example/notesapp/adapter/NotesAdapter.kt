package com.example.notesapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.databinding.ItemNotesBinding
import com.example.notesapp.models.Note
import kotlin.random.Random

class NotesAdapter(private val context: Context, val listener: OnNoteItemClickListener) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private val allNotes = ArrayList<Note>()
    private val notes = ArrayList<Note>()

    private var colors = listOf<Int>(
        context.getColor(R.color.color01),
        context.getColor(R.color.color02),
        context.getColor(R.color.color03),
        context.getColor(R.color.color04),
        context.getColor(R.color.color05),
        context.getColor(R.color.color06),
        context.getColor(R.color.color07)
    )

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

            itemRootLayout.setCardBackgroundColor(colors[Random.nextInt(colors.size)])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateNotes(newNotes: List<Note>) {
        allNotes.clear()
        allNotes.addAll(newNotes)

        notes.clear()
        notes.addAll(allNotes)

        notifyDataSetChanged()
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