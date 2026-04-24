package com.example.zen.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Destinations for the bottom navigation bar shown in `MainScaffold`.
 *
 * Modelled as an enum so the `NavigationBar` can iterate over `Destination.entries`
 * and stay in sync with the routes registered in the nested `NavHost`. Adding a
 * new tab means adding one enum constant plus one `composable(...)` entry.
 *
 * @property route Identifier used by the `NavHost` and `navigate` calls.
 * @property label Text shown under each tab icon and in the top app bar.
 * @property icon Material icon rendered on the tab.
 */
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
