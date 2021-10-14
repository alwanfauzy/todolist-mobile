package com.alwan.todolist.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.alwan.todolist.data.db.UserDao
import com.alwan.todolist.data.model.User
import com.example.githubdatabase.repository.local.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private var userDao: UserDao? = null
    private var noteDB: NoteDatabase? = NoteDatabase.getDatabase(application)

    init {
        userDao = noteDB?.userDao()
    }

    fun getUser(id: Int) : LiveData<User> {
        return userDao!!.getUser(id)
    }

    fun addUser(data: User){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.addUser(data)
        }
    }

    fun removeUser(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeUser(id)
        }
    }

    fun getUserId(username: String, password: String) : Int{
        return userDao!!.getUserId(username, password)
    }

     fun checkUser(username: String, password: String) : Int {
        return userDao!!.checkUser(username, password)
    }
}