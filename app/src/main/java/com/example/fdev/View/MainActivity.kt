package com.example.fdev.View

import CartScreen
import LayoutProductScreen
import RetrofitService
import ReviewScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fdev.ViewModel.NetWork.ApiService
import com.example.fdev.navigator.GetLayoutButtonBarNavigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainNavigation()
        }
    }

    @Composable
    fun MainNavigation() {
        val navController = rememberNavController()
        val retrofitService = RetrofitService() // Initialize RetrofitService
        val apiService: ApiService = retrofitService.fdevApiService // Get ApiService

        NavHost(navController = navController, startDestination = Router.WELCOME.name) {
            composable(Router.WELCOME.name) {
                LayoutWelcome(navController = navController)
            }
            composable(Router.SETTING.name) {
                LayoutSetting(navController = navController)
            }
            composable(Router.HOME.name) {
                GetLayoutButtonBarNavigator(navController)
            }
            composable(Router.LOGIN.name) {
                LayoutLoginScreen(navController = navController)
            }
            composable(Router.REGISTER.name) {
                LayoutRegisterScreen(navController = navController)
            }
            composable(Router.PRODUCT.name) {
                LayoutProductScreen(navController = navController)
            }
            composable(Router.HELP.name) {
                LayoutHelp(navController = navController)
            }
            composable(Router.CONTACT.name) {
                LayoutContact(navController = navController)
            }
            composable(Router.MAIL.name) {
                LayoutMail(navController = navController)
            }
            composable(Router.CART.name) {
                CartScreen(navController = navController)
            }
            composable(
                route = "${Router.CHECKOUT.name}/{totalPrice}",
                arguments = listOf(navArgument("totalPrice") { type = NavType.StringType })
            ) { backStackEntry ->
                val totalPrice = backStackEntry.arguments?.getString("totalPrice") ?: "0.0"
                CheckoutScreen(navController = navController, totalPrice = totalPrice)
            }
            composable(Router.FAVORITES.name) {
                FavoritesScreen(navController = navController)
            }
            composable(Router.SEARCH.name) {
                SearchScreen(navController = navController,retrofitService=retrofitService)
            }
            composable(Router.NOTIFICATIONS.name) {
                NotificationScreen(navController = navController)
            }
            composable(Router.CONGRATSSCREEN.name) {
                CongratsScreen(navController = navController)
            }
            composable(Router.PAYMENTMETHODSCREEN.name) {
                PaymentMethodScreen(navController = navController)
            }
            composable(Router.ADDPAYMENTMETHOD.name) {
                AddPaymentMethod(navController = navController)
            }
            composable(Router.REVIEW.name + "/{productId}") { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: ""
                ReviewScreen(navController = navController,productId=productId, productName = String())
            }

            composable(Router.ACCOUNTS.name) {
                LayoutAccounts(navController = navController)
            }
        }
    }

    enum class Router {
        WELCOME,
        SETTING,
        HOME,
        LOGIN,
        REGISTER,
        PRODUCT,
        HELP,
        CONTACT,
        MAIL,
        CART,
        CHECKOUT,
        FAVORITES,
        SEARCH,
        NOTIFICATIONS,
        CONGRATSSCREEN,
        PAYMENTMETHODSCREEN,
        ADDPAYMENTMETHOD,
        REVIEW,
        ACCOUNTS,
    }
}
