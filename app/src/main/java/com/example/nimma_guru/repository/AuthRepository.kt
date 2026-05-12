package com.example.nimma_guru.repository

import com.example.nimma_guru.data.AppDao
import com.example.nimma_guru.model.User

class AuthRepository(private val appDao: AppDao) {
    suspend fun login(email: String, pass: String, role: String): User? = appDao.login(email, pass, role)
    suspend fun register(user: User) = appDao.insertUser(user)
    suspend fun getUserByCredentials(email: String, pass: String): User? = appDao.getUserByCredentials(email, pass)
    suspend fun getUser(uid: String) = appDao.getUser(uid)
}