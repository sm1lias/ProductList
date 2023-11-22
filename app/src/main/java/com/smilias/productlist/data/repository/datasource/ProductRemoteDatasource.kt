package com.smilias.productlist.data.repository.datasource

import com.smilias.productlist.common.Resource
import com.smilias.productlist.domain.model.Product

interface ProductRemoteDatasource {
    suspend fun getProducts(): Resource<List<Product>>
}