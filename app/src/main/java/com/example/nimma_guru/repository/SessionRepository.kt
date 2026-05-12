package com.example.nimma_guru.repository

import com.example.nimma_guru.data.AppDao
import com.example.nimma_guru.model.Session
import kotlinx.coroutines.flow.Flow

class SessionRepository(private val appDao: AppDao) {
    fun getSessionsForStudent(studentId: String): Flow<List<Session>> = appDao.getSessionsForStudent(studentId)
    fun getSessionsForMentor(mentorId: String): Flow<List<Session>> = appDao.getSessionsForMentor(mentorId)
    suspend fun bookSession(session: Session) = appDao.insertSession(session)
}