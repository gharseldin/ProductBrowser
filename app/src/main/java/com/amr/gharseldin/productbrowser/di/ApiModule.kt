package com.amr.gharseldin.productbrowser.di

import com.amr.gharseldin.productbrowser.model.BASE_URL
import com.amr.gharseldin.productbrowser.model.ProductsApi
import com.amr.gharseldin.productbrowser.model.ProductsService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    @Provides
    fun providesProductsApi(): ProductsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ProductsApi::class.java)
    }
}