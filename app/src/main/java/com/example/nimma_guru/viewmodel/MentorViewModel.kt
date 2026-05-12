package com.example.nimma_guru.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nimma_guru.model.Mentor
import com.example.nimma_guru.repository.MentorRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MentorViewModel(private val repository: MentorRepository) : ViewModel() {

    private val _mentors = MutableStateFlow<List<Mentor>>(emptyList())
    val mentors = _mentors.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _saveSuccess = MutableSharedFlow<Boolean>()
    val saveSuccess = _saveSuccess.asSharedFlow()

    init {
        fetchMentors()
    }

    private fun fetchMentors() {
        viewModelScope.launch {
            _loading.value = true
            repository.allMentors.collectLatest {
                _mentors.value = it
                _loading.value = false
            }
        }
    }

    fun addMentor(mentor: Mentor) {
        viewModelScope.launch {
            repository.insertMentors(listOf(mentor))
            _saveSuccess.emit(true)
        }
    }
}