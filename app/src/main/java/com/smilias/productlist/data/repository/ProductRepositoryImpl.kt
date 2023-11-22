package com.smilias.productlist.data.repository

import com.smilias.productlist.common.Resource
import com.smilias.productlist.data.repository.datasource.ProductLocalDatasource
import com.smilias.productlist.data.repository.datasource.ProductRemoteDatasource
import com.smilias.productlist.domain.model.Product
import com.smilias.productlist.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productLocalDatasource: ProductLocalDatasource,
    private val productRemoteDatasource: ProductRemoteDatasource
) : ProductRepository {

    override suspend fun getProducts(): Resource<List<Product>> {
        return getProductsFromDb()
    }


    override suspend fun getProductsFromApi(): Resource<List<Product>> {
        return getFromApiAndSaveToDb()
    }

    override suspend fun getProductById(id: Long): Product {
        return productLocalDatasource.getProductFromDb(id)
    }


    private suspend fun getProductsFromDb(): Resource<List<Product>> {
        val productList = productLocalDatasource.getProductsFromDb()
        return if (productList.isNotEmpty()) {
            Resource.Success(productList)
        } else {
            getFromApiAndSaveToDb()
        }

    }

    private suspend fun getFromApiAndSaveToDb(): Resource<List<Product>> {
        val products = productRemoteDatasource.getProducts()
        return if (products is Resource.Success) {
            products.data?.let { list ->
                productLocalDatasource.saveProductsToDb(list)
                Resource.Success(list)
            } ?: Resource.Error(message = "Api returned null data")
        } else {
            Resource.Error(
                message = products.message.toString(),
                data = productLocalDatasource.getProductsFromDb()
            )
        }
    }
}
