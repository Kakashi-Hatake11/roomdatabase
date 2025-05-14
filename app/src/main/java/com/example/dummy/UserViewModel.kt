package com.example.dummy

import android.app.Application
import androidx.lifecycle.*
import com.example.dummy.User
import com.example.dummy.UserDatabase
import com.example.dummy.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository
    val allUsers: LiveData<List<User>>

    private val _loginResult = MutableLiveData<User?>(null)
    val loginResult: LiveData<User?> = _loginResult

    init {
        val userDao = UserDatabase.getInstance(application).userDao()
        repository = UserRepository(userDao)
        allUsers = repository.allUsers
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        val user = repository.login(email, password)
        _loginResult.postValue(user)
    }

    fun register(user: User) = viewModelScope.launch {
        repository.insert(user)
    }

    fun update(user: User) = viewModelScope.launch {
        repository.update(user)
    }

    fun delete(user: User) = viewModelScope.launch {
        repository.delete(user)
    }
}