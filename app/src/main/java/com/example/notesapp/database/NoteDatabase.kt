package com.example.notesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.models.Note
import com.example.notesapp.utils.NOTE_DATABASE
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun getNoteDao(): NoteDao


    companion object {

        @Volatile
        private var INSTANCE: NoteDatabase? = null

        private var noteDatabase: NoteDatabase? = null

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            NOTE_DATABASE
        ).build()

        @OptIn(InternalCoroutinesApi::class)
        fun getNoteDatabase(context: Context) = INSTANCE ?: synchronized(Any()) {
            INSTANCE = createDatabase(context)
            INSTANCE!!
        }
    }

}