package com.example.nimma_guru

import android.app.Application
import com.example.nimma_guru.data.AppDatabase
import com.example.nimma_guru.repository.AuthRepository
import com.example.nimma_guru.repository.MentorRepository
import com.example.nimma_guru.repository.SessionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NimmaGuruApp : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val authRepository by lazy { AuthRepository(database.appDao()) }
    val mentorRepository by lazy { MentorRepository(database.appDao()) }
    val sessionRepository by lazy { SessionRepository(database.appDao()) }
}