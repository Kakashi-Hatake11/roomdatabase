package com.example.dummy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dummy.databinding.ItemUserBinding

class UserListAdapter(
    private val onItemClicked: (User) -> Unit,
    private val onDeleteClicked: (User) -> Unit
) :
    ListAdapter<User, UserListAdapter.UserViewHolder>(UserDiffCallback()) {

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.nameText.text = user.name
            binding.emailText.text = user.email
            binding.ageText.text = "Age: ${user.age}"
            binding.passwordText.text = "Password: ${user.password}"


            binding.root.setOnClickListener {
                onItemClicked(user)
            }
            binding.root.setOnClickListener {
                onDeleteClicked(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class UserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
}
