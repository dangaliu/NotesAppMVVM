package com.example.notesapp.adapter

import androidx.cardview.widget.CardView
import com.example.notesapp.models.Note


interface OnNoteItemClickListener {

    fun onItemClick(note: Note)

    fun onLongItemClick(note: Note, view: CardView)
}