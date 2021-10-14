package com.alwan.todolist.ui.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.alwan.todolist.data.UserPreferences
import com.alwan.todolist.databinding.DialogFragmentUpdateBinding
import com.alwan.todolist.data.model.Note


class UpdateDialogFragment : DialogFragment(), View.OnClickListener {
    private lateinit var userPreferences: UserPreferences
    private var _binding: DialogFragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var note: Note

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentUpdateBinding.inflate(inflater, container, false)
        val root: View = binding.root
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userPreferences = UserPreferences(requireContext())
        note = requireArguments().getParcelable("EXTRA_NOTE")!!
        binding.editTitle.setText(note.title)
        binding.editDescription.setText(note.description)
        binding.btnUpdate.setOnClickListener(this)
        binding.btnDelete.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
        setupViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnUpdate -> {
                if (isChanged()) {
                    userPreferences.authId.asLiveData().observe(this, Observer {
                        val idUser = it!!.toInt()
                        val newTitle = binding.editTitle.text.toString()
                        val newDescription = binding.editDescription.text.toString()
                        val noteUpdated =
                            Note(note.id, newTitle, newDescription, note.isChecked, idUser)
                        mainViewModel.updateNote(noteUpdated)
                    })
                    Toast.makeText(requireContext(), "Note Updated", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "No Changes", Toast.LENGTH_SHORT).show()
                }
                dismiss()
            }
            binding.btnDelete -> {
                mainViewModel.removeNote(note.id)
                Toast.makeText(requireContext(), "Note Removed", Toast.LENGTH_SHORT).show()
                dismiss()
            }
            binding.btnCancel -> {
                dismiss()
            }
        }
    }

    fun setupViewModel() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    fun isChanged(): Boolean {
        return !(note.title!!.equals(binding.editTitle.text.toString()) && note.description!!.equals(
            binding.editDescription.text.toString()
        ))
    }
}