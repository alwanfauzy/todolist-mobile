package com.alwan.todolist.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String? = null,
    val description: String? = null,
    val isChecked: Boolean = false,
) : Parcelable
