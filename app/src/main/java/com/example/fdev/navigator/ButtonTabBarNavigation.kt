package com.example.fdev.navigator

import RetrofitService
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fdev.R
import com.example.fdev.View.Admin.AddProductScreen
import com.example.fdev.View.FavoritesScreen
import com.example.fdev.View.LayoutHomeScreen
import com.example.fdev.View.NotificationScreen
import com.example.fdev.View.ProfileScreen
import com.example.fdev.View.SearchScreen
import com.google.firebase.auth.FirebaseAuth


enum class ROUTER {
    home,
    favourite,
    Notification,
    search,
    person,
    ADDPRODUCT,
    PRODUCTADMIN,
    CONGRATSADMIN
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetLayoutButtonBarNavigator(navHostController: NavHostController) {
    var isSelected by rememberSaveable { mutableStateOf(ROUTER.home.name) }
    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()

    val user = auth.currentUser
    val isAdmin = user?.displayName == "AdminFdev"

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
            ) {
                // Home - luôn hiển thị
                NavigationBarItem(
                    selected = isSelected == ROUTER.home.name,
                    onClick = {
                        isSelected = ROUTER.home.name
                        navController.navigate(ROUTER.home.name) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    },
                    icon = { Icon(painter = painterResource(id = R.drawable.home_anh), contentDescription = null, modifier = Modifier.size(25.dp)) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF059BEE),
                        unselectedIconColor = Color.Black,
                        indicatorColor = Color.White
                    )
                )

                // Nếu là admin
                if (isAdmin) {
                    NavigationBarItem(
                        selected = isSelected == ROUTER.ADDPRODUCT.name,
                        onClick = {
                            isSelected = ROUTER.ADDPRODUCT.name
                            navController.navigate(ROUTER.ADDPRODUCT.name) {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            }
                        },
                        icon = { Icon(painter = painterResource(id = R.drawable.add_icon), contentDescription = null, modifier = Modifier.size(20.dp)) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF059BEE),
                            unselectedIconColor = Color.Black,
                            indicatorColor = Color.White
                        )
                    )
                } else {
                    // Nếu không phải admin (người dùng thông thường)
                    NavigationBarItem(
                        selected = isSelected == ROUTER.favourite.name,
                        onClick = {
                            isSelected = ROUTER.favourite.name
                            navController.navigate(ROUTER.favourite.name) {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            }
                        },
                        icon = { Icon(painter = painterResource(id = R.drawable.favourite), contentDescription = null, modifier = Modifier.size(25.dp)) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF059BEE),
                            unselectedIconColor = Color.Black,
                            indicatorColor = Color.White
                        )
                    )
                    NavigationBarItem(
                        selected = isSelected == ROUTER.Notification.name,
                        onClick = {
                            isSelected = ROUTER.Notification.name
                            navController.navigate(ROUTER.Notification.name) {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            }
                        },
                        icon = { Icon(painter = painterResource(id = R.drawable.notification), contentDescription = null, modifier = Modifier.size(25.dp)) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF059BEE),
                            unselectedIconColor = Color.Black,
                            indicatorColor = Color.White
                        )
                    )
                }

                // Các mục luôn hiển thị: Search và Person
                NavigationBarItem(
                    selected = isSelected == ROUTER.search.name,
                    onClick = {
                        isSelected = ROUTER.search.name
                        navController.navigate(ROUTER.search.name) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    },
                    icon = { Icon(painter = painterResource(id = R.drawable.search_anh), contentDescription = null, modifier = Modifier.size(25.dp)) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF059BEE),
                        unselectedIconColor = Color.Black,
                        indicatorColor = Color.White
                    )
                )
                NavigationBarItem(
                    selected = isSelected == ROUTER.person.name,
                    onClick = {
                        isSelected = ROUTER.person.name
                        navController.navigate(ROUTER.person.name) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    },
                    icon = { Icon(painter = painterResource(id = R.drawable.person), contentDescription = null, modifier = Modifier.size(25.dp)) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF059BEE),
                        unselectedIconColor = Color.Black,
                        indicatorColor = Color.White
                    )
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it)
        ) {
            Spacer(modifier = Modifier.height(1.dp))

            // NavHost để quản lý các màn hình
            NavHost(
                navController = navController,
                startDestination = isSelected
            ) {
                composable(ROUTER.home.name) {
                    LayoutHomeScreen(navHostController, RetrofitService())
                }
                if (isAdmin) {
                    composable(ROUTER.ADDPRODUCT.name) {
                        AddProductScreen(navHostController)
                    }



                } else {
                    composable(ROUTER.favourite.name) {
                        FavoritesScreen(navHostController)
                    }
                    composable(ROUTER.Notification.name) {
                        NotificationScreen(navHostController)
                    }
                }
                composable(ROUTER.search.name) {
                    SearchScreen(navHostController, retrofitService = RetrofitService())
                }
                composable(ROUTER.person.name) {
                    ProfileScreen(navHostController)
                }

            }
        }
    }
}

