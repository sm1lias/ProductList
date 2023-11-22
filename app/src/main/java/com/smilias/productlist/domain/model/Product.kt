package com.smilias.productlist.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class Product(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val description: String,
    val image: String,
    val name: String,
    val price: String,
    val thumbnail: String
)