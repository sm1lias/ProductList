package com.smilias.productlist.di

import android.content.Context
import androidx.room.Room
import com.smilias.productlist.data.local.ProductDao
import com.smilias.productlist.data.local.ProductDatabase
import com.smilias.productlist.data.remote.ProductApi
import com.smilias.productlist.data.repository.datasource.ProductLocalDatasource
import com.smilias.productlist.data.repository.datasource.ProductRemoteDatasource
import com.smilias.productlist.data.repository.datasourceImpl.ProductLocalDatasourceImpl
import com.smilias.productlist.data.repository.datasourceImpl.ProductRemoteDatasourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideProductDatabase(context: Context): ProductDao {
        return Room.databaseBuilder(context, ProductDatabase::class.java,
            "product_database")
            .build()
            .movieDao()
    }

    @Singleton
    @Provides
    fun provideProductLocalDatasource(productDao: ProductDao): ProductLocalDatasource {
        return ProductLocalDatasourceImpl(productDao)
    }

    @Singleton
    @Provides
    fun provideProductRemoteDatasource(productApi: ProductApi): ProductRemoteDatasource {
        return ProductRemoteDatasourceImpl(productApi)
    }
}