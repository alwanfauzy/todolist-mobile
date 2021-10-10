package com.alwan.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alwan.todolist.model.Note
import com.example.githubdatabase.repository.local.NoteDao
import com.example.githubdatabase.repository.local.NoteDatabase

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val listNotes = MutableLiveData<ArrayList<Note>>()
    private var noteDao: NoteDao? = null
    private var noteDB: NoteDatabase? = NoteDatabase.getDatabase(application)

    init {
        noteDao = noteDB?.noteDao()
    }

    fun getNotes(): LiveData<List<Note>>? {
        return noteDao?.getNote()
    }
}