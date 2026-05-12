package com.example.nimma_guru.repository

import com.example.nimma_guru.data.AppDao
import com.example.nimma_guru.model.Mentor
import kotlinx.coroutines.flow.Flow

class MentorRepository(private val appDao: AppDao) {
    val allMentors: Flow<List<Mentor>> = appDao.getAllMentors()
    
    suspend fun getMentorById(id: String): Mentor? = appDao.getMentorById(id)
    
    suspend fun insertMentors(mentors: List<Mentor>) {
        appDao.insertMentors(mentors)
    }
}