package com.example.zen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.zen.ui.theme.ZenTheme

/**
 * App entry point.
 *
 * For Assessment 2 we keep everything UI-only, but we still want the "Dark mode" switch
 * (Profile screen) to visibly change the app theme. The simplest way is to store the
 * theme flag at the Activity level and pass it down to the composables.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Saveable so it survives configuration changes (e.g., rotation).
            var darkMode by rememberSaveable { mutableStateOf(false) }
            ZenTheme(darkTheme = darkMode) {
                ZenApp(
                    darkMode = darkMode,
                    onDarkModeChange = { darkMode = it }
                )
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    // Kept only for the default template preview; not used in the actual navigation flow.
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ZenTheme {
        Greeting("Android")
    }
}

