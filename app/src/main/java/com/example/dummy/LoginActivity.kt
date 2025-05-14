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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString()

            userViewModel.loginResult.observe(this) { user ->
                if (user != null) {
                    startActivity(Intent(this, UserListActivity::class.java))
                } else {
                    Toast.makeText(this, "Invalid credentials, please register.", Toast.LENGTH_SHORT).show()
                    // startActivity(Intent(this, RegisterActivity::class.java))
                }
            }

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, RegisterActivity::class.java))
            }

            userViewModel.login(email, password)
        }

        binding.registerRedirect.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }


    }
}