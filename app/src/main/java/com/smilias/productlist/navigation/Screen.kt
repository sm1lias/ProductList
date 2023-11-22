package com.smilias.productlist.navigation

sealed class Screen(val route: String){
    object ProductsScreen: Screen("products_screen")
    object ProductDetailScreen: Screen("product_details_screen")
}
