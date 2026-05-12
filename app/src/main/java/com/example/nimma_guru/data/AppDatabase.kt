package com.example.nimma_guru.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.nimma_guru.model.Converters
import com.example.nimma_guru.model.Mentor
import com.example.nimma_guru.model.Session
import com.example.nimma_guru.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

@Database(entities = [User::class, Mentor::class, Session::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "nimma_guru_database"
                )
                .addCallback(AppDatabaseCallback(scope))
                .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.appDao())
                }
            }
        }

        suspend fun populateDatabase(appDao: AppDao) {
            val sampleMentors = listOf(
                Mentor(
                    id = "mentor_ravi",
                    name = "Ravi Kumar",
                    skills = listOf("Android", "Kotlin", "Java"),
                    rating = 4.8,
                    availability = "Online - Flexible",
                    location = "Bangalore",
                    bio = "Expert Android developer with 10 years of experience in building scalable mobile applications.",
                    email = "ravi@example.com"
                ),
                Mentor(
                    id = "mentor_sneha",
                    name = "Sneha Iyer",
                    skills = listOf("Data Science", "Python", "ML"),
                    rating = 4.7,
                    availability = "Evening - Weekdays",
                    location = "Chennai",
                    bio = "Data Scientist at a top tech firm, specializing in machine learning and predictive modeling.",
                    email = "sneha@example.com"
                ),
                Mentor(
                    id = "mentor_arjun",
                    name = "Arjun R",
                    skills = listOf("Web Dev", "React", "Node.js"),
                    rating = 4.6,
                    availability = "Weekend - Mornings",
                    location = "Hyderabad",
                    bio = "Full-stack developer passionate about creating responsive and user-friendly web interfaces.",
                    email = "arjun@example.com"
                )
            )
            appDao.insertMentors(sampleMentors)
            
            // Add demo users
            val student = User("demo_student", "Demo Student", "student@example.com", "1234", "student")
            val mentorUser = User("demo_mentor", "Demo Mentor", "mentor@example.com", "1234", "mentor")
            appDao.insertUser(student)
            appDao.insertUser(mentorUser)

            // Add sample sessions for the student
            appDao.insertSession(Session(
                mentorName = "Ravi Kumar",
                mentorId = "mentor_ravi",
                studentName = "Demo Student",
                studentId = "demo_student",
                date = "25/10/2024",
                time = "17:00",
                topic = "Android Basics & Architecture"
            ))
            appDao.insertSession(Session(
                mentorName = "Sneha Iyer",
                mentorId = "mentor_sneha",
                studentName = "Demo Student",
                studentId = "demo_student",
                date = "26/10/2024",
                time = "18:30",
                topic = "Data Science Roadmap"
            ))

            // Add sample sessions for the mentor
            appDao.insertSession(Session(
                mentorName = "Demo Mentor",
                mentorId = "demo_mentor",
                studentName = "Demo Student",
                studentId = "demo_student",
                date = "27/10/2024",
                time = "10:00",
                topic = "Introduction to Mentorship"
            ))
            appDao.insertSession(Session(
                mentorName = "Demo Mentor",
                mentorId = "demo_mentor",
                studentName = "Alice",
                studentId = "alice_uid",
                date = "28/10/2024",
                time = "14:00",
                topic = "Career Guidance Session"
            ))
        }
    }
}