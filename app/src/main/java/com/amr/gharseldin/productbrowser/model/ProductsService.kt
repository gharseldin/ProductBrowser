package com.amr.gharseldin.productbrowser.model

import com.amr.gharseldin.productbrowser.di.DaggerApiComponent
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

public const val BASE_URL = "https://mobile-tha-server.firebaseapp.com"

class ProductsService {

    @Inject
    lateinit var api: ProductsApi

    init {
        DaggerApiComponent.create().inject(this)
    }
    fun getProducts(page: Int, size: Int): Single<ProductList> {
        return api.getProducts(page, size)
    }


}