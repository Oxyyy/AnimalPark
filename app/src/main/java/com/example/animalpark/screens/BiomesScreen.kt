package com.example.animalpark.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animalpark.R
import com.google.firebase.database.*

@Composable
fun BiomesScreen(onBiomeClick: (String) -> Unit) {
    val database = FirebaseDatabase.getInstance().reference

    var biomes by remember {
        mutableStateOf(
            emptyList<Pair<String, Pair<String, Pair<String, List<Pair<String, List<Pair<String, String>>>>>>>>()
        )
    }
    var isLoading by remember { mutableStateOf(true) }
    val expandedStates = remember { mutableStateMapOf<String, Boolean>() }

    LaunchedEffect(Unit) {
        database.get().addOnSuccessListener { snapshot ->
            val biomeList = snapshot.children.mapNotNull { biomeSnapshot ->
                val id = biomeSnapshot.key
                val name = biomeSnapshot.child("name").getValue(String::class.java)
                val color = biomeSnapshot.child("color").getValue(String::class.java) ?: "#FFFFFF"
                val enclosures = biomeSnapshot.child("enclosures").children.mapNotNull { enclosureSnapshot ->
                    val enclosureId = enclosureSnapshot.child("id").getValue(String::class.java)
                    val animals = enclosureSnapshot.child("animals").children.mapNotNull { animalSnapshot ->
                        val animalId = animalSnapshot.child("id_animal").getValue(String::class.java)
                        val animalName = animalSnapshot.child("name").getValue(String::class.java)
                        if (animalId != null && animalName != null) animalId to animalName else null
                    }
                    if (enclosureId != null) enclosureId to animals else null
                }
                if (id != null && name != null) id to (name to (color to enclosures)) else null
            }

            biomes = biomeList
            isLoading = false
        }.addOnFailureListener {
            Log.e("FirebaseBiome", "Erreur Firebase: ${it.message}")
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.animalpark_logo),
            contentDescription = "Logo AnimalPark",
            modifier = Modifier
                .height(100.dp)
                .padding(bottom = 8.dp)
        )

        Text(
            text = "Liste des Biomes",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            biomes.forEach { (id, nameAndData) ->
                val (name, colorAndEnclosures) = nameAndData
                val (color, enclosures) = colorAndEnclosures
                val isExpanded = expandedStates[id] ?: false

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { expandedStates[id] = !isExpanded },
                    colors = CardDefaults.cardColors(
                        containerColor = Color(android.graphics.Color.parseColor(color)) //Coouleur pour chaque biome
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "🌳 $name",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )

                        if (isExpanded) {
                            Spacer(modifier = Modifier.height(4.dp))
                            if (enclosures.isEmpty()) {
                                Text("Aucun enclos disponible", fontSize = 14.sp)
                            } else {
                                enclosures.forEach { (enclosureId, animals) ->
                                    Text(
                                        text = "Enclos $enclosureId",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                                    )
                                    if (animals.isEmpty()) {
                                        Text(
                                            "• Aucun animal",
                                            fontSize = 14.sp,
                                            modifier = Modifier.padding(start = 16.dp)
                                        )
                                    } else {
                                        animals.forEach { (animalId, animalName) ->
                                            Text(
                                                text = "• $animalName",
                                                fontSize = 14.sp,
                                                modifier = Modifier.padding(start = 16.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
