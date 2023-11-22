package com.smilias.productlist.data.remote

import com.smilias.productlist.data.remote.model.ProductDto
import retrofit2.http.GET

interface ProductApi {
    @GET("products")
    suspend fun getProducts(): List<ProductDto>

}