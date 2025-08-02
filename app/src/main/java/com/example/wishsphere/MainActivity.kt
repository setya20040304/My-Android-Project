package com.example.wishsphere

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wishsphere.ui.AddWishScreen
import com.example.wishsphere.ui.WishDetailScreen
import com.example.wishsphere.ui.WishListScreen
import com.example.wishsphere.ui.WishViewModel
import com.example.wishsphere.ui.WishViewModelFactory
import com.example.wishsphere.ui.theme.WishSphereTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WishSphereTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val repository = (context.applicationContext as WishApp).repository
    val viewModel: WishViewModel = viewModel(factory = WishViewModelFactory(repository))
    val navController = rememberNavController()
    val wishes by viewModel.wishes.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "wish_list",
        // Parameter untuk animasi masuk
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { 1000 },
                animationSpec = tween(300)
            )
        },
        // Parameter untuk animasi keluar
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -1000 },
                animationSpec = tween(300)
            )
        }
    ) {
        composable("wish_list") {
            WishListScreen(
                navToaddWish = {
                    navController.navigate("add_wish")
                },
                wishes = wishes,
                onDeleteWish = { wish -> viewModel.deleteWish(wish) },
                onItemClick = { wish ->
                    navController.navigate("wish_detail/${wish.id}")
                },
                onCheckedChange = { wish, isCompleted ->
                    viewModel.updateWish(wish.copy(isCompleted = isCompleted))
                }
            )
        }
        composable("add_wish") {
            AddWishScreen(
                navigateUp = { navController.popBackStack() },
                onAddWish = { wish -> viewModel.addWish(wish) },
                onUpdateWish = {}
            )
        }
        composable("wish_detail/{wishId}") { backStackEntry ->
            val wishId = backStackEntry.arguments?.getString("wishId")?.toLongOrNull()
            val wish = wishId?.let {
                viewModel.getWishById(it).collectAsState(initial = null).value
            }

            if (wish != null) {
                WishDetailScreen(
                    wish = wish,
                    onEditClick = {
                        navController.navigate("edit_wish/${wish.id}")
                    },
                    onNavigateUp = { navController.navigateUp() }
                )
            } else {
                Text("Wish tidak ditemukan.")
            }
        }
        composable("edit_wish/{wishId}"){ backStackEntry ->
            val wishId = backStackEntry.arguments?.getString("wishId")?.toLongOrNull()
            val wish = wishId?.let {
                viewModel.getWishById(it).collectAsState(initial = null).value
            }
            if (wish != null) {
                AddWishScreen(
                    navigateUp = { navController.popBackStack() },
                    onAddWish = {},
                    onUpdateWish = { updatedWish -> viewModel.updateWish(updatedWish) },
                    wish = wish
                )
            } else {
                Text("Wish tidak ditemukan.")
            }
        }
    }
}