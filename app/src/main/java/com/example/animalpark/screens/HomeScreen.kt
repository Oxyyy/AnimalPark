package com.example.animalpark.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import com.example.animalpark.LoginActivity
import com.example.animalpark.R
import com.google.firebase.auth.FirebaseAuth


@Composable
fun HomeScreen() {
    val auth = FirebaseAuth.getInstance()
    val userEmail = auth.currentUser?.email ?: "Utilisateur inconnu"
    val context = LocalContext.current

    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Bienvenue,", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = userEmail, style = MaterialTheme.typography.bodyLarge)
        Button(
            onClick = {
                auth.signOut()  // Déconnecter l'utilisateur
                Toast.makeText(
                    context,
                    "Vous êtes déconnecté.",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Se déconnecter")
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.map_parc),
                contentDescription = "Map du Parc",
                modifier = Modifier
                    .size(300.dp)
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offset.x,
                        translationY = offset.y
                    )
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            scale = (scale * zoom).coerceIn(1f, 3f) // Zoom min 1x, max 5x en gros
                            offset = Offset(
                                x = (offset.x + pan.x).coerceIn(-500f, 500f), // Limite les déplacements pour pas que ça se casse loiin
                                y = (offset.y + pan.y).coerceIn(-00f, 00f)
                            )
                        }
                    }
            )
        }
    }

}
