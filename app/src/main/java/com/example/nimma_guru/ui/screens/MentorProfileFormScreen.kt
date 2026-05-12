package com.example.nimma_guru.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nimma_guru.model.Mentor
import com.example.nimma_guru.viewmodel.MentorViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MentorProfileFormScreen(
    navController: NavHostController,
    viewModel: MentorViewModel
) {
    var name by remember { mutableStateOf("") }
    var skills by remember { mutableStateOf("") }
    var availability by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    
    var showError by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Professional Profile", fontWeight = FontWeight.ExtraBold, color = Color(0xFF263238)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color(0xFF1976D2))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color(0xFFF8FAFC)
    ) { padding ->
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + slideInVertically(initialOffsetY = { 40 })
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(Color(0xFFE3F2FD), RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.BusinessCenter, contentDescription = null, tint = Color(0xFF1976D2))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "Public Information",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF263238)
                        )
                    }
                }

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it; showError = false },
                    label = { Text("Display Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Medium),
                    isError = showError && name.isBlank()
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it; showError = false },
                    label = { Text("Contact Email") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Medium),
                    isError = showError && email.isBlank()
                )

                OutlinedTextField(
                    value = skills,
                    onValueChange = { skills = it; showError = false },
                    label = { Text("Skills (e.g. Kotlin, React, UX)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Medium),
                    isError = showError && skills.isBlank()
                )

                OutlinedTextField(
                    value = availability,
                    onValueChange = { availability = it; showError = false },
                    label = { Text("Availability (e.g. Mon-Fri, 6-8 PM)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Medium),
                    isError = showError && availability.isBlank()
                )

                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it; showError = false },
                    label = { Text("City / Region") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Medium),
                    isError = showError && location.isBlank()
                )

                OutlinedTextField(
                    value = bio,
                    onValueChange = { bio = it; showError = false },
                    label = { Text("About Me / Experience") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 4,
                    shape = RoundedCornerShape(16.dp),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Medium),
                    isError = showError && bio.isBlank()
                )

                if (showError) {
                    Text(
                        text = "Please complete all fields to publish your profile.",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (name.isBlank() || email.isBlank() || skills.isBlank() || 
                            availability.isBlank() || location.isBlank() || bio.isBlank()) {
                            showError = true
                        } else {
                            val mentor = Mentor(
                                id = UUID.randomUUID().toString(),
                                name = name,
                                email = email,
                                skills = skills.split(",").map { it.trim() }.filter { it.isNotEmpty() },
                                availability = availability,
                                location = location,
                                bio = bio,
                                rating = 4.5
                            )
                            viewModel.addMentor(mentor)
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
                ) {
                    Text("Save & Publish", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}