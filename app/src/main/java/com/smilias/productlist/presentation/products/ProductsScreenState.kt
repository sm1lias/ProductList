package com.smilias.productlist.presentation.products

import com.smilias.productlist.domain.model.Product

data class ProductsScreenState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    var error: String? = null,
    val refreshing: Boolean = false
)
