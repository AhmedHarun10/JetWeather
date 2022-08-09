package com.ahmedh.jetweather.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ahmedh.jetweather.screens.Search.SearchScreen
import com.ahmedh.jetweather.screens.about.AboutScreen
import com.ahmedh.jetweather.screens.favorites.FavoriteScreen
import com.ahmedh.jetweather.screens.main.MainScreen
import com.ahmedh.jetweather.screens.main.MainViewModel
import com.ahmedh.jetweather.screens.settings.SettingsScreen
import com.ahmedh.jetweather.screens.splash.WeatherSplashScreen


@ExperimentalComposeUiApi
@Composable
fun WeatherNavigation() {
    // creates the nav controller
    val navController = rememberNavController ()

    // create the nav host
    NavHost(navController = navController,
        startDestination = WeatherScreens.SplashScreen.name){
        composable(route = WeatherScreens.SplashScreen.name){
            WeatherSplashScreen(navController = navController)
        }
        val route = WeatherScreens.MainScreen.name
        composable(
            route = "$route/{city}",
            arguments = listOf(navArgument(name = "city"){
                type = NavType.StringType
            })
        ){ navBack ->
            navBack.arguments?.getString("city").let { city ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController = navController,mainViewModel, city = city )
            }
        }
        composable(route = WeatherScreens.SearchScreen.name){
            SearchScreen(navController = navController)
        }
        composable(route = WeatherScreens.AboutScreen.name){
            AboutScreen(navController = navController)
        }
        composable(route = WeatherScreens.SettingsScreen.name){
            SettingsScreen(navController = navController)
        }
        composable(route = WeatherScreens.FavoriteScreen.name){
            FavoriteScreen(navController = navController)
        }

    }
}