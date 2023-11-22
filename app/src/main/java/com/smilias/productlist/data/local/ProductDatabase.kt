package com.smilias.productlist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smilias.productlist.domain.model.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class ProductDatabase: RoomDatabase() {

    abstract fun movieDao(): ProductDao
}