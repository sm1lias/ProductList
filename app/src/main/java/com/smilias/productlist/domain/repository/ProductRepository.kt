package com.smilias.productlist.domain.repository

import com.smilias.productlist.common.Resource
import com.smilias.productlist.domain.model.Product

interface ProductRepository {

    suspend fun getProducts(): Resource<List<Product>>

    suspend fun getProductsFromApi(): Resource<List<Product>>

    suspend fun getProductById(id: Long): Product
}