package com.example.githubdatabase.repository.local

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.alwan.todolist.data.model.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(noteDao: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM note WHERE id_user = :idUser")
    fun getNote(idUser: Int): LiveData<List<Note>>

    @Query(" SELECT COUNT(*) FROM note WHERE note.id = :id")
    fun isCheckedNote(id: Int): Int

    @Query("UPDATE note SET isChecked = :checked WHERE id = :id")
    fun updateChecked(id: Int, checked: Boolean)

    @Query("DELETE FROM note WHERE note.id = :id ")
    fun removeNote(id: Int): Int

    @Query("SELECT * FROM note")
    fun findAll(): Cursor
}