package com.amr.gharseldin.productbrowser.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

public const val BASE_URL = "https://mobile-tha-server.firebaseapp.com"

class ProductsService {


    private val api: ProductsApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ProductsApi::class.java)

    fun getProducts(page: Int, size: Int): Single<ProductList>{
        return api.getProducts(page, size)
    }


}