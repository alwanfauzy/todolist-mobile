package com.alwan.todolist.ui.main

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.alwan.todolist.data.UserPreferences
import com.alwan.todolist.databinding.DialogFragmentAddBinding
import com.alwan.todolist.data.model.Note
import com.alwan.todolist.ui.auth.LoginActivity


class AddDialogFragment : DialogFragment(), View.OnClickListener {
    private lateinit var userPreferences: UserPreferences
    private var idUser: Int? = null
    private var _binding: DialogFragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogFragmentAddBinding.inflate(inflater, container, false)
        val root: View = binding.root
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userPreferences = UserPreferences(requireContext())

        setupViewModel()
        binding.btnAdd.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnAdd -> {
                userPreferences.authId.asLiveData().observe(this, Observer {
                    idUser = it!!.toInt()
                    val note =
                        Note(
                            0,
                            binding.editTitle.text.toString(),
                            binding.editDescription.text.toString(),
                            false,
                            idUser
                        )
                    mainViewModel.addNote(note)
                })
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
}