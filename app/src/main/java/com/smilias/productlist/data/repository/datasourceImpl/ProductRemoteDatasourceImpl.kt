package com.smilias.productlist.data.repository.datasourceImpl

import com.smilias.productlist.common.Resource
import com.smilias.productlist.data.mapper.toProduct
import com.smilias.productlist.data.remote.ProductApi
import com.smilias.productlist.data.remote.model.ProductDto
import com.smilias.productlist.data.repository.datasource.ProductRemoteDatasource
import com.smilias.productlist.domain.model.Product
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductRemoteDatasourceImpl @Inject constructor(
    private val productApi: ProductApi
) : ProductRemoteDatasource {
    override suspend fun getProducts(): Resource<List<Product>> {
        return try {
            productsToResource(productApi.getProducts())
        } catch (e: IOException) {
            Resource.Error("No internet connection")
        } catch (e: HttpException){
            Resource.Error(e.message())
        }
    }

    private fun productsToResource(products: List<ProductDto>): Resource<List<Product>> {
        return Resource.Success(products.map { it.toProduct() })
    }
}