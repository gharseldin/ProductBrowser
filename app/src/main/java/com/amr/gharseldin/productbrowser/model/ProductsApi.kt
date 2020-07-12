package com.amr.gharseldin.productbrowser.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsApi {

    @GET("walmartproducts/{page}/{size}")
    fun getProducts(@Path("page") page: Int, @Path("size") size: Int): Single<ProductList>
}