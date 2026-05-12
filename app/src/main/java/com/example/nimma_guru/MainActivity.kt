package com.example.nimma_guru

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.nimma_guru.navigation.NavGraph
import com.example.nimma_guru.ui.theme.NimmaGuruTheme
import com.example.nimma_guru.viewmodel.AuthViewModel
import com.example.nimma_guru.viewmodel.MentorViewModel
import com.example.nimma_guru.viewmodel.SessionViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val app = application as NimmaGuruApp
        
        setContent {
            NimmaGuruTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val authViewModel: AuthViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return AuthViewModel(app.authRepository) as T
                        }
                    })
                    val mentorViewModel: MentorViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return MentorViewModel(app.mentorRepository) as T
                        }
                    })
                    val sessionViewModel: SessionViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return SessionViewModel(app.sessionRepository) as T
                        }
                    })

                    val navController = rememberNavController()
                    NavGraph(
                        navController = navController,
                        authViewModel = authViewModel,
                        mentorViewModel = mentorViewModel,
                        sessionViewModel = sessionViewModel
                    )
                }
            }
        }
    }
}
