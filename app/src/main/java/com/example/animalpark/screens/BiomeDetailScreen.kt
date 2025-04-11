package com.example.animalpark.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import com.google.firebase.database.FirebaseDatabase

@Composable
fun BiomeDetailScreen(biomeId: String?) {
    val database = FirebaseDatabase.getInstance().reference.child(biomeId ?: "")
    var biomeName by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(biomeId) {
        database.get().addOnSuccessListener { snapshot ->
            biomeName = snapshot.child("name").getValue(String::class.java)
            isLoading = false
        }.addOnFailureListener {
            Log.e("FirebaseBiome", "Erreur récupération biome: ${it.message}")
            isLoading = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Détail du Biome", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text("ID: ${biomeId ?: "Inconnu"}", fontSize = 18.sp)

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Text("Nom: ${biomeName ?: "Non trouvé"}", fontSize = 18.sp)
        }
    }
}

