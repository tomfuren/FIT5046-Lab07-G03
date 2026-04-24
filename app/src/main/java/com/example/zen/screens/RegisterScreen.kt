package com.example.zen.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zen.ui.theme.ZenTheme

/**
 * Discrete password-strength buckets rendered by the live strength
 * indicator on [RegisterScreen]. Computed via [evaluatePasswordStrength].
 */
private enum class PasswordStrength { NONE, WEAK, MODERATE, STRONG }

/**
 * Classifies a password by length and character diversity for the live
 * strength indicator on [RegisterScreen].
 *
 * STRONG requires 8+ characters and at least 3 of {lowercase, uppercase,
 * digit, symbol}. MODERATE requires any 2 of those categories. Inputs
 * shorter than 6 characters or with lower diversity fall back to WEAK.
 *
 * UI-only heuristic — it is not a security check and does not replace
 * real validation. Assessment 4 will delegate password validation to
 * Firebase Auth's server-side policy; this function will remain only
 * as a client-side hint for the strength bar.
 */
private fun evaluatePasswordStrength(password: String): PasswordStrength {
    if (password.isEmpty()) return PasswordStrength.NONE
    val hasLower = password.any { it.isLowerCase() }
    val hasUpper = password.any { it.isUpperCase() }
    val hasDigit = password.any { it.isDigit() }
    val hasSymbol = password.any { !it.isLetterOrDigit() }
    val diversityCount = listOf(hasLower, hasUpper, hasDigit, hasSymbol).count { it }

    return when {
        password.length < 6 -> PasswordStrength.WEAK
        password.length >= 8 && diversityCount >= 3 -> PasswordStrength.STRONG
        diversityCount >= 2 -> PasswordStrength.MODERATE
        else -> PasswordStrength.WEAK
    }
}

/**
 * Registration screen for the Zen app (Assessment 2 prototype).
 *
 * Fields: name, email, password (with live strength indicator), confirm
 * password. Validation runs on Submit only — per-field errors clear as
 * the user edits. Successful submission invokes [onRegister]; [onBack]
 * pops back to the Login screen.
 *
 * In Assessment 4, [onRegister] will hand the credentials off to Firebase Auth.
 */
@Composable
fun RegisterScreen(onRegister: () -> Unit = {}, onBack: () -> Unit = {}) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }

    var nameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmError by remember { mutableStateOf(false) }

    val strength = evaluatePasswordStrength(password)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Create account",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                if (it.isNotEmpty()) nameError = false
            },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = nameError,
            supportingText = if (nameError) {
                { Text("Please enter your name") }
            } else null
        )
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                if (it.isNotEmpty()) emailError = false
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = emailError,
            supportingText = if (emailError) {
                { Text("Please enter your email") }
            } else null
        )
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                if (it.isNotEmpty()) passwordError = false
            },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) {
                            Icons.Default.Visibility
                        } else {
                            Icons.Default.VisibilityOff
                        },
                        contentDescription = if (passwordVisible) {
                            "Hide password"
                        } else {
                            "Show password"
                        }
                    )
                }
            },
            isError = passwordError,
            supportingText = if (passwordError) {
                { Text("Please enter a password") }
            } else null
        )
        Spacer(Modifier.height(4.dp))

        if (strength != PasswordStrength.NONE) {
            val (progress, label, color) = when (strength) {
                PasswordStrength.WEAK -> Triple(0.33f, "Weak", Color(0xFFD32F2F))
                PasswordStrength.MODERATE -> Triple(0.66f, "Moderate", Color(0xFFF57C00))
                PasswordStrength.STRONG -> Triple(1.0f, "Strong", Color(0xFF388E3C))
                else -> Triple(0f, "", Color.Transparent)
            }
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth(),
                color = color
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = color
            )
        }
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                if (it.isNotEmpty()) confirmError = false
            },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = if (confirmVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(onClick = { confirmVisible = !confirmVisible }) {
                    Icon(
                        imageVector = if (confirmVisible) {
                            Icons.Default.Visibility
                        } else {
                            Icons.Default.VisibilityOff
                        },
                        contentDescription = if (confirmVisible) {
                            "Hide password"
                        } else {
                            "Show password"
                        }
                    )
                }
            },
            isError = confirmError,
            supportingText = when {
                confirmError && confirmPassword.isEmpty() -> {
                    { Text("Please confirm your password") }
                }
                confirmError && confirmPassword != password -> {
                    { Text("Passwords do not match") }
                }
                else -> null
            }
        )
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                // Submit-time validation: flip every error flag in one pass,
                // then call onRegister only if all fields passed. Errors are
                // cleared live in each field's onValueChange above, so the
                // user sees their correction take effect without re-submitting.
                nameError = name.isEmpty()
                emailError = email.isEmpty()
                passwordError = password.isEmpty()
                confirmError = confirmPassword.isEmpty() || confirmPassword != password

                if (!nameError && !emailError && !passwordError && !confirmError) {
                    onRegister()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }
        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Already have an account? ",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Sign in",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onBack() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    ZenTheme {
        RegisterScreen()
    }
}
