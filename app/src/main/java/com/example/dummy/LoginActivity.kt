package com.example.dummy

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.dummy.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val userViewModel: UserViewModel by viewModels()
    private var isLoginAttempted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userViewModel.loginResult.observe(this) { user ->
            if (isLoginAttempted) {
                if (user != null) {
                    startActivity(Intent(this, UserListActivity::class.java))
                } else {
                    Toast.makeText(this, "Invalid credentials, please register.", Toast.LENGTH_SHORT).show()
                }
                isLoginAttempted = false // reset
            }
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            isLoginAttempted = true
            userViewModel.login(email, password)
        }

        binding.registerRedirect.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
