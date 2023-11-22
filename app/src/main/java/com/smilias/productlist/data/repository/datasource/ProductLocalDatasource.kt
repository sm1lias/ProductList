package com.smilias.productlist.data.repository.datasource

import com.smilias.productlist.domain.model.Product

interface ProductLocalDatasource {

    suspend fun getProductsFromDb(): List<Product>
    suspend fun getProductFromDb(id: Long): Product
    suspend fun saveProductsToDb(products: List<Product>)
    suspend fun deleteAll()
}