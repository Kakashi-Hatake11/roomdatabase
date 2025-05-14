package com.example.dummy

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.dummy.databinding.ActivityEditUserBinding


class EditUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditUserBinding


    private val userViewModel: UserViewModel by viewModels()
    private var currentUser: User? = null
    private var userId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityEditUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getIntExtra("user_id", -1)

        if (userId == -1) {
            Toast.makeText(this, "User ID invalid", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        userViewModel.allUsers.observe(this) { users ->
            currentUser = users.find { it.id == userId }
            currentUser?.let {
                binding.nameEditText.setText(it.name)
                binding.emailEditText.setText(it.email)
                binding.ageEditText.setText(it.age.toString())
                binding.passwordEditText.setText(it.password.toString())
            }
        }

        binding.saveButton.setOnClickListener {
            val name = binding.nameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val ageText = binding.ageEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val age = ageText.toIntOrNull()

            if (name.isEmpty() || email.isEmpty() || age == null || age <= 0 ||password.isEmpty()) {
                Toast.makeText(this, "Enter valid data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedUser = currentUser?.copy(name = name, email = email, age = age, password = password)
            if (updatedUser != null) {
                userViewModel.update(updatedUser)
                Toast.makeText(this, "User updated", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
