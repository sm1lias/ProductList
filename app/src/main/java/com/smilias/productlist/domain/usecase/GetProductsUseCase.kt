package com.smilias.productlist.domain.usecase

import com.smilias.productlist.common.Resource
import com.smilias.productlist.domain.model.Product
import com.smilias.productlist.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    operator fun invoke(fromApi: Boolean): Flow<Resource<List<Product>>> {
        return flow {
            emit(Resource.Loading())
            if (fromApi)
                emit(productRepository.getProductsFromApi())
            else
                emit(productRepository.getProducts())
        }
    }
}