package com.alwan.todolist

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alwan.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val noteAdapter = NoteAdapter()
    private var notes = arrayListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setData()
        setupRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun setData() {
        val title = resources.getStringArray(R.array.name)
        val description = resources.getStringArray(R.array.creator)

        for (i in title.indices) {
            val note = Note(
                i,
                title[i],
                "Owned by " + description[i],
            )
            notes.add(note)
        }
    }

    fun setupRecyclerView() {
        binding.rvNote.setHasFixedSize(true)
        binding.rvNote.addItemDecoration(NoteAdapter.MarginItemDecoration(15))
        binding.rvNote.layoutManager = LinearLayoutManager(this)
        noteAdapter.setData(notes)
        binding.rvNote.adapter = noteAdapter

        noteAdapter.setOnItemClickCallback(object : NoteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Note) {
                val updateDialogFragment = UpdateDialogFragment()
                updateDialogFragment.show(supportFragmentManager, "Update Dialog")
            }
        })
    }

    override fun onClick(v: View?) {
        when (v) {

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.add_menu -> {
                val addDialogFragment = AddDialogFragment()
                addDialogFragment.show(supportFragmentManager, "Add Dialog")
                true
            }
            else -> true
        }
    }
}