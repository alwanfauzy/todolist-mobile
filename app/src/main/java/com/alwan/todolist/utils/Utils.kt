package com.alwan.todolist

import com.alwan.todolist.data.model.Note

fun List<Note>.toArrayList(): ArrayList<Note> {
    val listNote = ArrayList<Note>()
    for (note in this) {
        val noteMapped = Note(
            note.id,
            note.title,
            note.description,
            note.isChecked,
        )
        listNote.add(noteMapped)
    }
    return listNote
}