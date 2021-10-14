package com.alwan.todolist.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alwan.todolist.R
import com.alwan.todolist.adapter.NoteAdapter
import com.alwan.todolist.data.UserPreferences
import com.alwan.todolist.data.model.Note
import com.alwan.todolist.databinding.ActivityMainBinding
import com.alwan.todolist.toArrayList
import com.alwan.todolist.ui.auth.LoginActivity
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private lateinit var userPreferences: UserPreferences
    private val binding get() = _binding!!
    private val noteAdapter = NoteAdapter()
    private var notes = arrayListOf<Note>()
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences(this)
        setData()
        setupRecyclerView()
        userPreferences.authId.asLiveData().observe(this, {
            if (it.isNullOrEmpty()) {
                val i = Intent(this, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(i)
            } else {
                setupViewModel(it.toInt())
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_menu -> {
                val addDialogFragment = AddDialogFragment()
                addDialogFragment.show(supportFragmentManager, "Add Dialog")
                true
            }
            R.id.logout_menu -> {
                lifecycleScope.launch {
                    userPreferences.deleteAuthId()
                }
                val i = Intent(this, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(i)
                true
            }
            else -> true
        }
    }

    fun setupViewModel(idUser: Int) {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mainViewModel.getNotes(idUser).observe(this, {
            noteAdapter.setData(it.toArrayList())
            if(it.count() > 0){
                showEmptyStatus(false)
            }else{
                showEmptyStatus(true)
            }
        })

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
        binding.rvNote.adapter = noteAdapter

        noteAdapter.setOnItemClickCallback(object : NoteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Note) {
                val updateDialogFragment = UpdateDialogFragment()
                val bundle = Bundle()
                bundle.putParcelable("EXTRA_NOTE", data)
                updateDialogFragment.arguments = bundle
                updateDialogFragment.show(supportFragmentManager, "Update Dialog")
            }

            override fun onCheckboxClicked(data: Note) {
                mainViewModel.updateChecked(data)
            }
        })
    }

    fun showEmptyStatus(state: Boolean) {
        if(state){
            binding.imgEmpty.visibility = View.VISIBLE
            binding.tvEmpty.visibility = View.VISIBLE
        }else{
            binding.imgEmpty.visibility = View.GONE
            binding.tvEmpty.visibility = View.GONE
        }
    }
}