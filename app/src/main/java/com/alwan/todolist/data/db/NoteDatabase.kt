package com.example.githubdatabase.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alwan.todolist.data.db.UserDao
import com.alwan.todolist.data.model.Note
import com.alwan.todolist.data.model.User

@Database(
    entities = [Note::class, User::class],
    version = 3
)

abstract class NoteDatabase : RoomDatabase() {
    companion object {
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase? {
            if (INSTANCE == null) {
                synchronized(NoteDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java,
                        "note_database"
                    ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun noteDao(): NoteDao
    abstract fun userDao(): UserDao
}