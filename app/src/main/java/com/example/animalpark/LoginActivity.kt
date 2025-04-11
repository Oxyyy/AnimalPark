package com.example.animalpark

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class LoginActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerButton = findViewById<Button>(R.id.registerButton)

        // Connexion
        loginButton.setOnClickListener {
            val email = emailEditText.text?.toString()?.trim()
            val password = passwordEditText.text?.toString()?.trim()

            if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password)
            }
        }

        // Enregistrement
        registerButton.setOnClickListener {
            val email = emailEditText.text?.toString()?.trim()
            val password = passwordEditText.text?.toString()?.trim()

            if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show()
            } else {
                registerUser(email, password)
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navigateToHome()
            } else {
                val exception = task.exception as? FirebaseAuthException
                val errorMessage = when (exception?.errorCode) {
                    "ERROR_INVALID_EMAIL" -> "Email invalide."
                    "ERROR_USER_NOT_FOUND" -> "Aucun utilisateur trouvé."
                    "ERROR_WRONG_PASSWORD" -> "Mot de passe incorrect."
                    "ERROR_WEAK_PASSWORD" -> "Le mot de passe doit comporter au moins 6 caractères."
                    else -> "Erreur inconnue, veuillez réessayer."
                }
                Toast.makeText(this, "Échec de la connexion: $errorMessage", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Login failed. Reason: ${task.exception?.message}")
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Inscription réussie!", Toast.LENGTH_SHORT).show()
                navigateToHome()
            } else {
                val exception = task.exception as? FirebaseAuthException
                val errorMessage = when (exception?.errorCode) {
                    "ERROR_INVALID_EMAIL" -> "Email invalide."
                    "ERROR_EMAIL_ALREADY_IN_USE" -> "L'email est déjà utilisé."
                    "ERROR_WEAK_PASSWORD" -> "Le mot de passe doit comporter au moins 6 caractères."
                    else -> "Erreur inconnue, veuillez réessayer."
                }
                Toast.makeText(this, "Échec de l'inscription: $errorMessage", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Registration failed. Reason: ${task.exception?.message}")
            }
        }
    }

    private fun navigateToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish() // Empêche de revenir au login avec le bouton back
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            navigateToHome() // Redirige automatiquement si l'utilisateur est déjà connecté
        }
    }
}
