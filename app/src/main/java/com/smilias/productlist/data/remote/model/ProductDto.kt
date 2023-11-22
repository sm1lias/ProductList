package com.smilias.productlist.data.remote.model


import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("Description")
    val description: String?,
    @SerializedName("Id")
    val id: Long,
    @SerializedName("Image")
    val image: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Price")
    val price: String,
    @SerializedName("Thumbnail")
    val thumbnail: String
)