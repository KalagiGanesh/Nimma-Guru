package com.example.nimma_guru.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "users")
data class User(
    @PrimaryKey val uid: String,
    val name: String,
    val email: String,
    val password: String,
    val role: String // "student" or "mentor"
)

@Entity(tableName = "mentors")
data class Mentor(
    @PrimaryKey val id: String,
    val name: String,
    val skills: List<String>,
    val rating: Double = 4.5,
    val availability: String,
    val location: String,
    val bio: String,
    val email: String
)

@Entity(tableName = "sessions")
data class Session(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val mentorName: String,
    val mentorId: String,
    val studentName: String,
    val studentId: String,
    val date: String,
    val time: String,
    val topic: String
)

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }
}