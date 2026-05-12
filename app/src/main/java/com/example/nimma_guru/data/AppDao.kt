package com.example.nimma_guru.data

import androidx.room.*
import com.example.nimma_guru.model.Mentor
import com.example.nimma_guru.model.Session
import com.example.nimma_guru.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password AND role = :role LIMIT 1")
    suspend fun login(email: String, password: String, role: String): User?

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun getUserByCredentials(email: String, password: String): User?

    @Query("SELECT * FROM users WHERE uid = :uid")
    suspend fun getUser(uid: String): User?

    // Mentors
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMentors(mentors: List<Mentor>)

    @Query("SELECT * FROM mentors")
    fun getAllMentors(): Flow<List<Mentor>>

    @Query("SELECT * FROM mentors WHERE id = :id")
    suspend fun getMentorById(id: String): Mentor?

    // Sessions
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: Session)

    @Query("SELECT * FROM sessions WHERE studentId = :studentId")
    fun getSessionsForStudent(studentId: String): Flow<List<Session>>

    @Query("SELECT * FROM sessions WHERE mentorId = :mentorId")
    fun getSessionsForMentor(mentorId: String): Flow<List<Session>>
}