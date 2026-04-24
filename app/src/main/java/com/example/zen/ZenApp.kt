package com.example.zen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zen.navigation.Routes
import com.example.zen.screens.HistoryScreen
import com.example.zen.screens.LoginScreen
import com.example.zen.screens.RegisterScreen
import com.example.zen.screens.SplashScreen
import com.example.zen.screens.TipsLibraryScreen

/**
 * Top-level navigation graph (app-level routes).
 *
 * We intentionally keep this graph small:
 * - Auth flow: Splash → Login/Register
 * - Main flow: a scaffold that hosts bottom-tab navigation
 * - Two secondary pages: History and Tips Library
 *
 * In Assessment 4, data/auth will be connected, but the navigation structure can stay.
 */
@Composable
fun ZenApp(
    darkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(
                onGetStarted = { navController.navigate(Routes.LOGIN) }
            )
        }
        composable(Routes.LOGIN) {
            LoginScreen(
                onLogin = {
                    // Week 5 navigation pattern: popUpTo(SPLASH) with
                    // inclusive = true clears the entire auth flow from the
                    // back stack, so pressing Back from the main app will not
                    // return the user to Splash or Login.
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                },
                onSignUp = { navController.navigate(Routes.REGISTER) }
            )
        }
        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegister = {
                    // After register we don't want users to return to Splash via back.
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.MAIN) {
            MainScaffold(
                onBrowseTips = { navController.navigate(Routes.TIPS_LIBRARY) },
                onViewHistory = { navController.navigate(Routes.HISTORY) },
                onLogout = {
                    // Logout returns to Login and clears the main flow.
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.MAIN) { inclusive = true }
                    }
                },
                darkMode = darkMode,
                onDarkModeChange = onDarkModeChange
            )
        }
        composable(Routes.HISTORY) {
            HistoryScreen(onBack = { navController.popBackStack() })
        }
        composable(Routes.TIPS_LIBRARY) {
            TipsLibraryScreen(onBack = { navController.popBackStack() })
        }
    }
}