package com.example.nimma_guru.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nimma_guru.model.Session
import com.example.nimma_guru.repository.SessionRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SessionViewModel(private val repository: SessionRepository) : ViewModel() {

    private val _studentSessions = MutableStateFlow<List<Session>>(emptyList())
    val studentSessions = _studentSessions.asStateFlow()

    private val _mentorSessions = MutableStateFlow<List<Session>>(emptyList())
    val mentorSessions = _mentorSessions.asStateFlow()

    private val _bookingSuccess = MutableSharedFlow<Boolean>()
    val bookingSuccess = _bookingSuccess.asSharedFlow()

    fun loadStudentSessions(studentId: String) {
        viewModelScope.launch {
            repository.getSessionsForStudent(studentId).collectLatest {
                _studentSessions.value = it
            }
        }
    }

    fun loadMentorSessions(mentorId: String) {
        viewModelScope.launch {
            repository.getSessionsForMentor(mentorId).collectLatest {
                _mentorSessions.value = it
            }
        }
    }

    fun bookSession(session: Session) {
        viewModelScope.launch {
            repository.bookSession(session)
            _bookingSuccess.emit(true)
        }
    }
}