package com.alwan.todolist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val note_id: Int,
    val title: String? = null,
    val description: String? = null,
    val isChecked: Boolean = false,
) : Parcelable
