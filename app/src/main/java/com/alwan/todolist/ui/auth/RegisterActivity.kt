package com.alwan.todolist.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.alwan.todolist.data.model.User
import com.alwan.todolist.databinding.ActivityRegisterBinding
import com.alwan.todolist.ui.main.UserViewModel

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private var _binding : ActivityRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        binding.btnRegister.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(v: View?) {
        when(v){
            binding.btnRegister -> {
                val username = binding.editUsername.text.toString()
                val password = binding.editPassword.text.toString()
                val user = User(0, username, password)
                userViewModel.addUser(user)
                finish()
            }
            binding.btnBack -> {
                finish()
            }
        }
    }

    fun setupViewModel(){
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
    }
}