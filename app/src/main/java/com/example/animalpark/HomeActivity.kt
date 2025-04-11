package com.example.animalpark

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.animalpark.screens.BiomeDetailScreen
import com.example.animalpark.screens.BiomesScreen
import com.example.animalpark.screens.ServiceScreen
import com.example.animalpark.screens.HomeScreen
import com.example.animalpark.screens.ListScreen
import com.example.animalpark.screens.TabView
import com.example.animalpark.ui.theme.AnimalParkTheme


data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

class HomeActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    val database = FirebaseDatabase.getInstance().getReference("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        enableEdgeToEdge()

        auth = FirebaseAuth.getInstance()

        val welcomeTextView = findViewById<TextView>(R.id.welcomeTextView)
        val logoutButton = findViewById<Button>(R.id.logoutButton)

        welcomeTextView.text = "Bienvenue, ${auth.currentUser?.email}"

        logoutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        setContent {
            val homeTab = TabBarItem("Accueil", Icons.Filled.Home, Icons.Outlined.Home)
            val biomesTab = TabBarItem("Biomes", Icons.Filled.List, Icons.Filled.List)
            val serviceTab = TabBarItem("Services", Icons.Filled.Info, Icons.Outlined.Info)

            val tabBarItems = listOf(homeTab, biomesTab, serviceTab)
            val navController = rememberNavController()

            AnimalParkTheme {
                Scaffold(
                    bottomBar = { TabView(tabBarItems, navController) },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        NavHost(navController = navController, startDestination = homeTab.title) {
                            composable(homeTab.title) { HomeScreen() }

                            composable(biomesTab.title) {
                                BiomesScreen { biomeId ->
                                    navController.navigate("biomes/$biomeId")
                                }
                            }

//                            composable(biomesTab.title) { BiomesScreen() }



                            composable(serviceTab.title) { ServiceScreen(navController) }

                            composable("BDR") {
                                ListScreen("Services Disponibles :", listOf(
                                    "🚻 Toilettes",
                                    "☕ Petit Café",
                                    "🛍️ Boutique",
                                    "🍽️ Restaurant du Parc"
                                ))
                            }

                            composable("VDC") {
                                ListScreen("Services Disponibles :", listOf(
                                    "🚂 Trajet en Train",
                                    "☕ Café Nomade",
                                    "🚰 Point d'eau",
                                    "🌳 Espace Pique-Nique"
                                ))
                            }

                            composable("BVD") {
                                ListScreen("Services Disponibles :", listOf(
                                    "🌄 Point de vue"
                                ))
                            }

                            composable("PLT") {
                                ListScreen("Services Disponibles :", listOf(
                                    "🚂 Trajet en Train",
                                    "🏡 Lodge",
                                    "🏖️ Paillote",
                                    "🌳 Espace Pique-Nique",
                                    "🎲 Plateau des jeux",
                                    "🚻 Toilettes",
                                    "🚰 Point d'eau",
                                    "⛺ Tente pédagogique"
                                ))
                            }

                            composable("CLR") {
                                ListScreen("Services Disponibles :", listOf(
                                    "🌄 Point de Vue",
                                    "🚻 Toilettes",
                                    "🚰 Point d'eau",
                                    "🌳 Espace Pique-Nique"
                                ))
                            }

                            composable("BDP") {
                                ListScreen("Services Disponibles :", listOf(
                                    "🚧 Aucun service présent pour le moment"
                                ))
                            }

                        }
                    }
                }
            }
        }
    }
}
