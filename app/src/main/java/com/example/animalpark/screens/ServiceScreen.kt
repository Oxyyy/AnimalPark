package com.example.animalpark.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ServiceScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        val buttonRanges = listOf(
            "La Bergerie des reptiles" to "BDR",
            "Le Vallon des cascades" to "VDC",
            "Le Belvédère" to "BVD",
            "Le Plateau" to "PLT",
            "Les Clairières" to "CLR",
            "Le Bois des pins" to "BDP"
        )

        buttonRanges.forEach { (label, route) ->
            Button(
                onClick = { navController.navigate(route) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Text(text = label)
            }
        }
    }
}