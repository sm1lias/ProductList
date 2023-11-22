package com.smilias.movierama.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.smilias.productlist.navigation.Screen
import com.smilias.productlist.presentation.product.ProductDetailsRoute
import com.smilias.productlist.presentation.products.ProductsRoute

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    NavHost(navController = navController, startDestination = Screen.ProductsScreen.route) {
        composable(route = Screen.ProductsScreen.route) {
            ProductsRoute(
                onProductClick = navController::navigateToDetailsScreen,
                onShowSnackbar = onShowSnackbar
            )
        }
        composable(
            route = "${Screen.ProductDetailScreen.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) {
            ProductDetailsRoute(onShowSnackbar = onShowSnackbar)
        }
    }
}