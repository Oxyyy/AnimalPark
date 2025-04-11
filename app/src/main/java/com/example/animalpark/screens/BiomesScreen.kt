package com.example.animalpark.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.*


//@Composable
//fun BiomesScreen(onBiomeClick: (String) -> Unit) {
//    val database = FirebaseDatabase.getInstance().reference // getReference("path") = path et .reference = racine
//    var biomes by remember { mutableStateOf<List<Pair<String, String>>>(emptyList()) }
//    var isLoading by remember { mutableStateOf(true) }
//
//    LaunchedEffect(Unit) {
//        Log.d("FirebaseBiome", "Début de la récupération des biomes")
//
//        database.get().addOnSuccessListener { snapshot ->
//            Log.d("FirebaseBiome", "Snapshot brut: ${snapshot.value}")
//
//            val biomeList = snapshot.children.mapNotNull { biomeSnapshot ->
//                val id = biomeSnapshot.key
//                val name = biomeSnapshot.child("name").getValue(String::class.java)
//
//                Log.d("FirebaseBiome", "Biome détecté: id=$id, name=$name")
//
//                if (id != null && name != null) id to name else null
//            }
//
//            biomes = biomeList
//            isLoading = false
//            Log.d("FirebaseBiome", "Liste des biomes récupérés: $biomes")
//        }.addOnFailureListener { error ->
//            Log.e("FirebaseBiome", "Erreur Firebase: ${error.message}")
//            isLoading = false
//        }
//    }
//
//    Column(
//        modifier = Modifier.fillMaxSize().padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Top
//    ) {
//        Text("Liste des Biomes", fontSize = 24.sp)
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        if (isLoading) {
//            CircularProgressIndicator()
//        } else {
//            LazyColumn {
//                items(biomes) { (id, name) ->
//                    Text(
//                        text = "🌍 $name",
//                        fontSize = 18.sp,
//                        modifier = Modifier
//                            .padding(8.dp)
//                            .clickable {
//                                Log.d("FirebaseBiome", "Biome sélectionné: id=$id, name=$name")
//                                onBiomeClick(id)
//                            }
//                    )
//                }
//            }
//        }
//    }
//}

// ----------- MVA -----------

//@Composable
//fun BiomesScreen(onBiomeClick: (String) -> Unit) {
//    val database = FirebaseDatabase.getInstance().reference // Récupère la racine
//    var biomes by remember { mutableStateOf<List<Pair<String, String>>>(emptyList()) }
//    var isLoading by remember { mutableStateOf(true) }
//
//    LaunchedEffect(Unit) {
//        Log.d("FirebaseBiome", "Début de la récupération des biomes")
//
//        database.get().addOnSuccessListener { snapshot ->
//            val biomeList = snapshot.children.mapNotNull { biomeSnapshot ->
//                val id = biomeSnapshot.key
//                val name = biomeSnapshot.child("name").getValue(String::class.java)
//                if (id != null && name != null) id to name else null
//            }
//            biomes = biomeList
//            isLoading = false
//            Log.d("FirebaseBiome", "Liste des biomes récupérés: $biomes")
//        }.addOnFailureListener {
//            Log.e("FirebaseBiome", "Erreur Firebase: ${it.message}")
//            isLoading = false
//        }
//    }
//
//    Column(
//        modifier = Modifier.fillMaxSize().padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Top
//    ) {
//        Text("Liste des Biomes", fontSize = 24.sp)
//        Spacer(modifier = Modifier.height(16.dp))
//
//        if (isLoading) {
//            CircularProgressIndicator()
//        } else {
//            LazyColumn {
//                items(biomes) { (id, name) ->
//                    Text(
//                        text = "🌍 $name",
//                        fontSize = 18.sp,
//                        modifier = Modifier
//                            .padding(8.dp)
//                            .clickable {
//                                Log.d("FirebaseBiome", "Biome sélectionné: id=$id")
////                                onBiomeClick(id) // Passe uniquement l'ID
//                            }
//                    )
//                }
//            }
//        }
//    }
//}

// --------- Biomes + enclos id ----------

//@Composable
//fun BiomesScreen(onBiomeClick: (String) -> Unit) {
//    val database = FirebaseDatabase.getInstance().reference // Récupère la racine
//    var biomes by remember { mutableStateOf<List<Pair<String, Pair<String, List<String>>>>>(emptyList()) } // Liste des biomes avec leurs enclos
//    var isLoading by remember { mutableStateOf(true) }
//
//    LaunchedEffect(Unit) {
//        Log.d("FirebaseBiome", "Début de la récupération des biomes")
//
//        database.get().addOnSuccessListener { snapshot ->
//            // Création de la liste des biomes avec enclos
//            val biomeList = snapshot.children.mapNotNull { biomeSnapshot ->
//                val id = biomeSnapshot.key
//                val name = biomeSnapshot.child("name").getValue(String::class.java)
//                val enclosures = biomeSnapshot.child("enclosures").children.mapNotNull {
//                    it.child("id").getValue(String::class.java)
//                }
//                if (id != null && name != null) id to (name to enclosures) else null
//            }
//
//            biomes = biomeList
//            isLoading = false
//            Log.d("FirebaseBiome", "Liste des biomes récupérés: $biomes")
//        }.addOnFailureListener {
//            Log.e("FirebaseBiome", "Erreur Firebase: ${it.message}")
//            isLoading = false
//        }
//    }
//
//    Column(
//        modifier = Modifier.fillMaxSize().padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Top
//    ) {
//        Text("Liste des Biomes", fontSize = 24.sp)
//        Spacer(modifier = Modifier.height(16.dp))
//
//        if (isLoading) {
//            CircularProgressIndicator()
//        } else {
//            LazyColumn {
//                items(biomes) { (id, nameAndEnclosures) ->
//                    val (name, enclosures) = nameAndEnclosures
//                    Column(modifier = Modifier.padding(8.dp)) {
//                        Text(
//                            text = "🌍 $name",
//                            fontSize = 18.sp,
//                            modifier = Modifier.padding(8.dp)
//                        )
//
//                        // Afficher les enclos sous chaque biome
//                        Text("Enclos disponibles:", fontSize = 16.sp)
//                        if (enclosures.isEmpty()) {
//                            Text("Aucun enclos disponible", fontSize = 14.sp)
//                        } else {
//                            enclosures.forEach { enclosureId ->
//                                Text("Enclos ID: $enclosureId", fontSize = 14.sp)
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}

@Composable
fun BiomesScreen(onBiomeClick: (String) -> Unit) {
    val database = FirebaseDatabase.getInstance().reference // Récupère la racine
    var biomes by remember { mutableStateOf<List<Pair<String, Pair<String, List<Pair<String, List<Pair<String, String>>>>>>>>(emptyList()) } // Liste des biomes avec leurs enclos et animaux
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        Log.d("FirebaseBiome", "Début de la récupération des biomes")

        database.get().addOnSuccessListener { snapshot ->
            // Création de la liste des biomes avec enclos et animaux
            val biomeList = snapshot.children.mapNotNull { biomeSnapshot ->
                val id = biomeSnapshot.key
                val name = biomeSnapshot.child("name").getValue(String::class.java)
                val enclosures = biomeSnapshot.child("enclosures").children.mapNotNull { enclosureSnapshot ->
                    val enclosureId = enclosureSnapshot.child("id").getValue(String::class.java)
                    val animals = enclosureSnapshot.child("animals").children.mapNotNull { animalSnapshot ->
                        val animalId = animalSnapshot.child("id_animal").getValue(String::class.java)
                        val animalName = animalSnapshot.child("name").getValue(String::class.java)
                        if (animalId != null && animalName != null) animalId to animalName else null
                    }
                    if (enclosureId != null) enclosureId to animals else null
                }
                if (id != null && name != null) id to (name to enclosures) else null
            }

            // Assignation de la liste complète des biomes avec leurs enclos et animaux
            biomes = biomeList
            isLoading = false
            Log.d("FirebaseBiome", "Liste des biomes récupérés: $biomes")
        }.addOnFailureListener {
            Log.e("FirebaseBiome", "Erreur Firebase: ${it.message}")
            isLoading = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Liste des Biomes", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                items(biomes) { (id, nameAndEnclosures) ->
                    val (name, enclosures) = nameAndEnclosures
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = "🌍 $name",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(8.dp)
                        )

                        // Afficher les enclos sous chaque biome
                        Text("Enclos disponibles:", fontSize = 16.sp)
                        if (enclosures.isEmpty()) {
                            Text("Aucun enclos disponible", fontSize = 14.sp)
                        } else {
                            enclosures.forEach { (enclosureId, animals) ->
                                Text("Enclos ID: $enclosureId", fontSize = 14.sp)

                                // Afficher les animaux sous chaque enclos
                                if (animals.isEmpty()) {
                                    Text("Aucun animal dans cet enclos", fontSize = 14.sp)
                                } else {
                                    animals.forEach { (animalId, animalName) ->
                                        Text("Animal: $animalName (ID: $animalId)", fontSize = 14.sp)
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







