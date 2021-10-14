package com.alwan.todolist.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.alwan.todolist.data.UserPreferences
import com.alwan.todolist.databinding.ActivityLoginBinding
import com.alwan.todolist.ui.main.MainActivity
import com.alwan.todolist.ui.main.UserViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var userViewModel: UserViewModel
    private lateinit var userPreferences: UserPreferences
    private var _binding : ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences(this)

        setupViewModel()
        binding.btnLogin.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun onClick(v: View?) {
        when(v){
            binding.btnLogin -> {
                val username = binding.editUsername.text.toString()
                val password = binding.editPassword.text.toString()
                if(userViewModel.checkUser(username, password) > 0){
                    val id = userViewModel.getUserId(username, password)
                    lifecycleScope.launch{
                        userPreferences.saveAuthId(id.toString())
                    }
                    val i = Intent(this, MainActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(i)
                }else{
                    binding.editUsername.setText("")
                    binding.editUsername.clearFocus()
                    binding.editPassword.setText("")
                    binding.editPassword.clearFocus()
                    Toast.makeText(this, "Credential not found", Toast.LENGTH_SHORT).show()
                }
            }
            binding.btnRegister -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }

    fun setupViewModel(){
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
    }


}