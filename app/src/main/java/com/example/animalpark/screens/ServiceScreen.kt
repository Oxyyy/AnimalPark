package com.example.animalpark.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.animalpark.R

@Composable
fun ServiceScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.animalpark_logo),
            contentDescription = "Logo AnimalPark",
            modifier = Modifier
                .height(100.dp)
                .padding(bottom = 8.dp)
        )

        Text(
            text = "Services",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        val biomeButtons = listOf(
            Triple("La Bergerie des reptiles", "BDR", "#7FE2D3"),
            Triple("Le Vallon des cascades", "VDC", "#96AEBE"),
            Triple("Le Belvédère", "BVD", "#C7B087"),
            Triple("Le Plateau", "PLT", "#E9A89D"),
            Triple("Les Clairières", "CLR", "#E9D296"),
            Triple("Le Bois des pins", "BDP", "#BCE48E")
        )

        biomeButtons.forEach { (label, route, hexColor) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { navController.navigate(route) },
                colors = CardDefaults.cardColors(
                    containerColor = Color(android.graphics.Color.parseColor(hexColor))
                )
            ) {
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🧰 $label",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            }
        }
}