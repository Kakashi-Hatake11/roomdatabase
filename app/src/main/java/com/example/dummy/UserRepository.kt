package com.example.dummy


import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {

    val allUsers: LiveData<List<User>> = userDao.getAllUsers()

    suspend fun insert(user: User) = userDao.insertUser(user)

    suspend fun login(email: String, password: String): User? =
        userDao.getUserByEmailAndPassword(email, password)

    suspend fun update(user: User) = userDao.updateUser(user)

    suspend fun delete(user: User) = userDao.deleteUser(user)
}