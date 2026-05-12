package com.example.nimma_guru.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nimma_guru.ui.screens.*
import com.example.nimma_guru.viewmodel.AuthViewModel
import com.example.nimma_guru.viewmodel.MentorViewModel
import com.example.nimma_guru.viewmodel.SessionViewModel

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object StudentHome : Screen("student_home")
    object MentorHome : Screen("mentor_home")
    object MentorList : Screen("mentor_list")
    object MentorDetail : Screen("mentor_detail/{mentorId}") {
        fun createRoute(mentorId: String) = "mentor_detail/$mentorId"
    }
    object Booking : Screen("booking/{mentorId}") {
        fun createRoute(mentorId: String) = "booking/$mentorId"
    }
    object Calendar : Screen("calendar")
    object Profile : Screen("profile")
    object MentorProfileForm : Screen("mentor_profile_form")
    object Meeting : Screen("meeting")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    mentorViewModel: MentorViewModel,
    sessionViewModel: SessionViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController, authViewModel)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController, authViewModel)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController, authViewModel)
        }
        composable(Screen.StudentHome.route) {
            StudentHomeScreen(navController, authViewModel, sessionViewModel)
        }
        composable(Screen.MentorHome.route) {
            MentorHomeScreen(navController, authViewModel, sessionViewModel)
        }
        composable(Screen.MentorList.route) {
            MentorListScreen(navController, mentorViewModel)
        }
        composable(Screen.MentorDetail.route) { backStackEntry ->
            val mentorId = backStackEntry.arguments?.getString("mentorId") ?: ""
            MentorDetailScreen(navController, mentorId, mentorViewModel)
        }
        composable(Screen.Booking.route) { backStackEntry ->
            val mentorId = backStackEntry.arguments?.getString("mentorId") ?: ""
            BookingScreen(navController, mentorId, authViewModel, mentorViewModel, sessionViewModel)
        }
        composable(Screen.Calendar.route) {
            CalendarScreen(navController, authViewModel, sessionViewModel)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController, authViewModel)
        }
        composable(Screen.MentorProfileForm.route) {
            MentorProfileFormScreen(navController, mentorViewModel)
        }
        composable(Screen.Meeting.route) {
            MeetingScreen(navController)
        }
    }
}