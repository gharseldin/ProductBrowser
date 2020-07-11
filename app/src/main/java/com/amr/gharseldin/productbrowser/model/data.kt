package com.amr.gharseldin.productbrowser.model

import com.google.gson.annotations.SerializedName

data class ProductList(
    @SerializedName("products")
    val products: List<Product>?,
    @SerializedName("totalProducts")
    val totalProducts: Int?,
    @SerializedName("pageNumber")
    val pageNumber: Int?,
    @SerializedName("pageSize")
    val pageSize: Int?,
    @SerializedName("statusCode")
    val statusCode: Int?
)

data class Product(
    @SerializedName("productId")
    val productId: String?,
    @SerializedName("productName")
    val productName: String?,
    @SerializedName("shortDescription")
    val shortDescription: String?,
    @SerializedName("longDescription")
    val longDescription: String?,
    @SerializedName("price")
    val price: String?,
    @SerializedName("productImage")
    val productImage: String?,
    @SerializedName("reviewRating")
    val reviewRating: Int?,
    @SerializedName("reviewCount")
    val reviewCount: Int?,
    @SerializedName("inStock")
    val inStock: Boolean?
)