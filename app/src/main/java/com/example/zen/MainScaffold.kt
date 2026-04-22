package com.example.zen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.zen.navigation.Destination
import com.example.zen.screens.HomeScreen
import com.example.zen.screens.InsightsScreen
import com.example.zen.screens.LogScreen
import com.example.zen.screens.ProfileScreen
import com.example.zen.ui.theme.ZenTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
/**
 * Main shell shown after login.
 *
 * Responsibilities:
 * - Top app bar title (based on selected bottom tab)
 * - Bottom navigation (Home/Log/Insights/Profile)
 * - Nested NavHost that swaps the tab content
 *
 * This keeps the app-level NavHost (`ZenApp`) simpler.
 */
fun MainScaffold(
    onBrowseTips: () -> Unit,
    onViewHistory: () -> Unit,
    onLogout: () -> Unit,
    darkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val currentTab = Destination.entries.firstOrNull {
        it.route == currentDestination?.route
    }
    val title = currentTab?.label ?: "Zen"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    // Use surface colors so the "gold" accent stays subtle.
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ) {
                Destination.entries.forEach { destination ->
                    NavigationBarItem(
                        selected = currentDestination?.route == destination.route,
                        onClick = {
                            // Standard bottom-tab behavior:
                            // - keep one instance of each tab
                            // - restore previous state when returning
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = destination.icon,
                                contentDescription = destination.label
                            )
                        },
                        label = { Text(destination.label) }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Destination.HOME.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Destination.HOME.route) {
                HomeScreen(
                    onBrowseTips = onBrowseTips,
                    onLogMood = {
                        navController.navigate(Destination.LOG.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
            composable(Destination.LOG.route) {
                LogScreen()
            }
            composable(Destination.INSIGHTS.route) {
                InsightsScreen(onViewHistory = onViewHistory)
            }
            composable(Destination.PROFILE.route) {
                ProfileScreen(
                    onLogout = onLogout,
                    darkMode = darkMode,
                    onDarkModeChange = onDarkModeChange
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MainScaffoldPreview() {
    ZenTheme {
        MainScaffold(
            onBrowseTips = {},
            onViewHistory = {},
            onLogout = {},
            darkMode = false,
            onDarkModeChange = {}
        )
    }
}
