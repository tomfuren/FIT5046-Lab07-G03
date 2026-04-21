package com.example.zen.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    HOME("home", "Home", Icons.Default.Home),
    LOG("log", "Log", Icons.Default.Edit),
    INSIGHTS("insights", "Insights", Icons.Default.Info),
    PROFILE("profile", "Profile", Icons.Default.AccountCircle)
}
