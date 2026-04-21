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

@Composable
fun ZenApp() {
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
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.MAIN) { inclusive = true }
                    }
                }
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
