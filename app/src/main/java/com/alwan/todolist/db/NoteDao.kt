package com.example.githubdatabase.repository.local

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.alwan.todolist.model.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(noteDao: Note)

    @Query("SELECT * FROM note")
    fun getNote(): LiveData<List<Note>>

    @Update
    fun updateNote(note: Note)

    @Query(" SELECT COUNT(*) FROM note WHERE note.id = :id")
    fun isCheckedNote(id: Int): Int

    @Query("DELETE FROM note WHERE note.id = :id ")
    fun removeNote(id: Int): Int

    @Query("SELECT * FROM note")
    fun findAll(): Cursor
}