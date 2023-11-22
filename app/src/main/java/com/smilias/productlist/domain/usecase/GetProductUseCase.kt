package com.smilias.productlist.domain.usecase

import com.smilias.productlist.domain.model.Product
import com.smilias.productlist.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    suspend operator fun invoke(id: Long): Product {
        return productRepository.getProductById(id)
    }
}