package com.smilias.productlist.data.repository.datasourceImpl

import com.smilias.productlist.data.repository.datasource.ProductLocalDatasource
import com.smilias.productlist.domain.model.Product

class ProductLocalDatasourceMemoryImpl : ProductLocalDatasource {

    var list: List<Product> = emptyList()
    override suspend fun getProductsFromDb(): List<Product> {
        return list
    }

    override suspend fun getProductFromDb(id: Long): Product {
       return list.first { it.id == id }
    }

    override suspend fun saveProductsToDb(products: List<Product>) {
        list = products
    }

    override suspend fun deleteAll() {
        list = emptyList()
    }
}