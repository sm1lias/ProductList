package com.smilias.productlist.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smilias.productlist.domain.model.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProducts(movies: List<Product>)

    @Query("DELETE FROM product_table")
    suspend fun deleteAllProducts()

    @Query("SELECT * FROM product_table")
    suspend fun getProducts(): List<Product>

    @Query("SELECT * FROM product_table WHERE id = :id LIMIT 1")
    suspend fun getProduct(id: Long): Product
}