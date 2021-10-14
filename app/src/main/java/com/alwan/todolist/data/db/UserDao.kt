package com.alwan.todolist.data.db

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alwan.todolist.data.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(userDao : User)

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUser(id: Int): LiveData<User>

    @Query("SELECT id FROM user WHERE user.username = :username AND user.password = :password")
    fun getUserId(username: String, password: String): Int

    @Query("DELETE FROM user WHERE user.id = :id ")
    fun removeUser(id: Int): Int

    @Query("SELECT * FROM note")
    fun findAll(): Cursor

    @Query("SELECT COUNT(*) FROM user WHERE user.username = :username AND user.password = :password")
    fun checkUser(username: String, password: String): Int
}