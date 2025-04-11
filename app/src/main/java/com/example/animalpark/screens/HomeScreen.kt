package com.example.animalpark.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.animalpark_logo),
            contentDescription = "Logo AnimalPark",
            modifier = Modifier
                .height(140.dp)
                .padding(bottom = 12.dp)
        )

        Text(
            text = userEmail,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        )

        Button(
            onClick = {
                auth.signOut()
                Toast.makeText(context, "Vous êtes déconnecté.", Toast.LENGTH_SHORT).show()
                context.startActivity(Intent(context, LoginActivity::class.java))
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        ) {
            Text("Se déconnecter")
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.map_parc),
                contentDescription = "Carte du parc",
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offset.x,
                        translationY = offset.y
                    )
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            scale = (scale * zoom).coerceIn(1f, 2f)
                            offset = Offset(
                                x = (offset.x + pan.x).coerceIn(-500f, 500f),
                                y = (offset.y + pan.y).coerceIn(-00f, 00f)
                            )
                        }
                    }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Bienvenue sur AnimalPark !",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            )
        )

        Text(
            text = "Voyagez à travers les biomes du monde.",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier.padding(top = 4.dp)
            )
        }
}
