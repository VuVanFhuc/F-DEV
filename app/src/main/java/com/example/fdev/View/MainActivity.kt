package com.example.fdev.View

import CartScreen
import CartViewModel
import LayoutProductScreen
import RetrofitService
import ReviewScreen
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fdev.View.Admin.ProductAdmin
import com.example.fdev.View.Admin.UpdateProductScreenAdmin
import com.example.fdev.ViewModel.NetWork.ApiService
import com.example.fdev.navigator.GetLayoutButtonBarNavigator
import com.example.fdev.navigator.ROUTER
import com.example.fdev.viewmodel.ProductAdminViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainNavigation()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun MainNavigation() {
        val navController = rememberNavController()
        val retrofitService = RetrofitService() // Initialize RetrofitService
        val apiService: ApiService = retrofitService.fdevApiService // Get ApiService
        val cartViewModel = CartViewModel(apiService) // Initialize CartViewModel
        val productAdminViewModel: ProductAdminViewModel = viewModel() // Initialize ProductAdminViewModel

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
                LayoutProductScreen(navController = navController, cartViewModel = cartViewModel)
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
                CartScreen(navController = navController, cartViewModel = cartViewModel)
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
                ReviewScreen(navController = navController, productId = productId, productName = String())
            }

            composable(Router.ACCOUNTS.name) {
                LayoutAccounts(navController = navController)
            }

            composable("updateProduct/{productId}/{productName}/{productPrice}/{productDescription}/{productType}") { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: ""
                val productName = backStackEntry.arguments?.getString("productName") ?: ""
                val productPrice = backStackEntry.arguments?.getString("productPrice") ?: ""
                val productDescription = backStackEntry.arguments?.getString("productDescription") ?: ""
                val productType = backStackEntry.arguments?.getString("productType") ?: ""
                UpdateProductScreenAdmin(productId, productName, productPrice, productDescription, productType, productAdminViewModel)
            }
            composable(ROUTER.CONGRATSADMIN.name) {
                CongratsAdminScreen(navController)
            }
            composable(Router.ProductAdmin1.name) {
                ProductAdmin(navController, cartViewModel = cartViewModel)
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
        PRODUCTADMIN,
        UPDATEADMIN,
        ProductAdmin1
    }
}
