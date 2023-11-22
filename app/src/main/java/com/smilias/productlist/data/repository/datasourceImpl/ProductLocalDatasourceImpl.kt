package com.smilias.productlist.data.repository.datasourceImpl

import com.smilias.productlist.data.local.ProductDao
import com.smilias.productlist.data.repository.datasource.ProductLocalDatasource
import com.smilias.productlist.domain.model.Product
import javax.inject.Inject

class ProductLocalDatasourceImpl @Inject constructor(
    private val productDao: ProductDao
) : ProductLocalDatasource {
    override suspend fun getProductsFromDb(): List<Product> {
        return productDao.getProducts()
    }

    override suspend fun getProductFromDb(id: Long): Product {
       return productDao.getProduct(id)
    }

    override suspend fun saveProductsToDb(products: List<Product>) {
        productDao.saveProducts(products)
    }

    override suspend fun deleteAll() {
        productDao.deleteAllProducts()
    }
}