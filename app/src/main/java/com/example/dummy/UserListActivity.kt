package com.example.dummy


import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dummy.databinding.ActivityUserListBinding


class UserListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserListBinding
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var adapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserListAdapter(onItemClicked = { user ->
            // On user item click, open EditUserActivity with user id
            val intent = Intent(this, EditUserActivity::class.java)
            intent.putExtra("user_id", user.id)
            startActivity(intent)
        },
            onDeleteClicked = { user ->
                userViewModel.delete(user)
            }
        )
        binding.userRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.userRecyclerView.adapter = adapter

        userViewModel.allUsers.observe(this) { users ->
            adapter.submitList(users)
        }
    }
}
