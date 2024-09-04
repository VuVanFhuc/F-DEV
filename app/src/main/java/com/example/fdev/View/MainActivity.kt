package com.example.fdev.View

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fdev.View.CartScreen
import com.example.fdev.View.CheckoutScreen
import com.example.fdev.View.FavoritesScreen
import com.example.fdev.View.SearchScreen
import com.example.fdev.View.NotificationScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GetlayoutNavigation()
        }
    }

    enum class Router {
        WELCOME,
        SETTING,
        CART,
        CHECKOUT,
        FAVORITES,
        SEARCH,
        NOTIFICATIONS

    }

    @Composable
    fun GetlayoutNavigation() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Router.WELCOME.name) {
            composable(Router.WELCOME.name) {
                LayoutWelcome(navController = navController)
            }
            composable(Router.SETTING.name){
                LayoutSetting(navController = navController)
            }
            composable(Router.CART.name) {
                CartScreen(navController = navController)
            }
            composable(Router.CHECKOUT.name) {
                CheckoutScreen(navController = navController)
            }
            composable(Router.FAVORITES.name) {
                FavoritesScreen(navController = navController)
            }
            composable(Router.SEARCH.name) {
                SearchScreen(navController = navController)
            }
            composable(Router.NOTIFICATIONS.name) {
                NotificationScreen(navController = navController)
            }



        }


    }
}
