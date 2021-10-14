package com.alwan.todolist.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.alwan.todolist.data.db.UserDao
import com.alwan.todolist.data.model.Note
import com.alwan.todolist.data.model.User
import com.example.githubdatabase.repository.local.NoteDao
import com.example.githubdatabase.repository.local.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var noteDao: NoteDao? = null
    private var noteDB: NoteDatabase? = NoteDatabase.getDatabase(application)

    init {
        noteDao = noteDB?.noteDao()
    }

    fun getNotes(idUser: Int): LiveData<List<Note>> {
        return noteDao!!.getNote(idUser)
    }

    fun addNote(data: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao?.addNote(data)
        }
    }

    fun updateNote(data: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao?.updateNote(data)
        }
    }

    fun updateChecked(data: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao?.updateChecked(data.id, !data.isChecked)
        }
    }

    fun removeNote(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao?.removeNote(id)
        }
    }
}