package com.smilias.productlist.di

import com.smilias.productlist.data.repository.ProductRepositoryImpl
import com.smilias.productlist.data.repository.datasource.ProductLocalDatasource
import com.smilias.productlist.data.repository.datasource.ProductRemoteDatasource
import com.smilias.productlist.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideProductRepository(
        productLocalDatasource: ProductLocalDatasource,
        productRemoteDatasource: ProductRemoteDatasource,
    ): ProductRepository {
        return ProductRepositoryImpl(productLocalDatasource, productRemoteDatasource)
    }
}