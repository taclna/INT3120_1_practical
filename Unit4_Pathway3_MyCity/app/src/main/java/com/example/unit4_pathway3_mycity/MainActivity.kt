package com.example.unit4_pathway3_mycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unit4_pathway3_mycity.CityViewModel
import com.example.unit4_pathway3_mycity.screens.CategoryScreen
import com.example.unit4_pathway3_mycity.screens.PlaceDetailScreen
import com.example.unit4_pathway3_mycity.screens.PlaceListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCityApp()
        }
    }
}

@Composable
fun MyCityApp(cityViewModel: CityViewModel = viewModel()) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "categories") {
        composable("categories") {
            CategoryScreen(cityViewModel.categories) { category ->
                navController.navigate("places/$category")
            }
        }
        composable("places/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            val places = cityViewModel.getPlacesByCategory(category)
            PlaceListScreen(
                places = places,
                onPlaceClick = { placeId -> navController.navigate("detail/$placeId") },
                onBack = { navController.popBackStack() }
            )
        }

        composable("detail/{placeId}") { backStackEntry ->
            val placeId = backStackEntry.arguments?.getString("placeId")?.toInt() ?: 0
            cityViewModel.getPlaceById(placeId)?.let { place ->
                PlaceDetailScreen(place, onBack = { navController.popBackStack() })
            }
        }

    }
}
