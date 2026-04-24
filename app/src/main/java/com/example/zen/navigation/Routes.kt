package com.example.zen.navigation

/**
 * Route identifiers for the app-level `NavHost` in `ZenApp`.
 *
 * Two groups of routes are defined here:
 * - Auth flow: [SPLASH], [LOGIN], [REGISTER] — shown before the user reaches the main app.
 * - Main flow: [MAIN] hosts the bottom-nav scaffold; [HISTORY] and [TIPS_LIBRARY] are
 *   secondary screens reachable from the main tabs.
 *
 * Bottom-navigation tabs themselves live in the [Destination] enum, not here, because
 * the `NavigationBar` needs to iterate over them — these routes only need name lookup.
 */
object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val MAIN = "main"
    const val HISTORY = "history"
    const val TIPS_LIBRARY = "tips_library"
}
