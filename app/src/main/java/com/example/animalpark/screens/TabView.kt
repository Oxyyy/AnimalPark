package com.example.animalpark.screens

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.animalpark.TabBarItem

@Composable
fun TabView(tabBarItems: List<TabBarItem>, navController: NavController) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    NavigationBar {
        tabBarItems.forEachIndexed { index, tabBarItem ->
            NavigationBarItem(
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    navController.navigate(tabBarItem.title)
                },
                icon = { Icon(if (selectedTabIndex == index) tabBarItem.selectedIcon else tabBarItem.unselectedIcon, contentDescription = tabBarItem.title) },
                label = { Text(tabBarItem.title) }
            )
        }
    }
}
