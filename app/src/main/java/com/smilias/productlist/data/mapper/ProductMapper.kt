package com.smilias.productlist.data.mapper

import com.smilias.productlist.data.remote.model.ProductDto
import com.smilias.productlist.domain.model.Product

fun ProductDto.toProduct(): Product {
    return Product(
        id = id,
        description = description ?: "",
        image = image,
        name = name,
        price = price,
        thumbnail = thumbnail
    )
}