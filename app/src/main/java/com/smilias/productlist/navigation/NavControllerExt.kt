package com.smilias.movierama.navigation

import androidx.navigation.NavController
import com.smilias.productlist.navigation.Screen

fun NavController.navigateToDetailsScreen(id: Long){
    this.navigate(Screen.ProductDetailScreen.route+"/$id")
}