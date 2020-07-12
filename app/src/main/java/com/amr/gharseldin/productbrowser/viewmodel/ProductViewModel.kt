package com.amr.gharseldin.productbrowser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amr.gharseldin.productbrowser.model.Product

class ProductViewModel : ViewModel() {

    val products = MutableLiveData<List<Product>>()
    val productsLoadingError = MutableLiveData<Boolean>()
    val productLoading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchProducts()
    }

    private fun fetchProducts() {
        val mockData = listOf(
            Product(
                "001",
                "Product 1",
                "Short description 1",
                "Long Description of the product 1",
                "10.00",
                "https://via.placeholder.com/100",
                3.5F,
                100,
                true
            ),
            Product(
                "002",
                "Product 2",
                "Short description 2",
                "Long Description of the product 2",
                "10.00",
                "https://via.placeholder.com/100",
                4.0F,
                200,
                true
            ),
            Product(
                "003",
                "Product 3",
                "Short description 3",
                "Long Description of the product 3",
                "10.00",
                "https://via.placeholder.com/100",
                4.5F,
                300,
                true
            ),
            Product(
                "004",
                "Product 4",
                "Short description 4",
                "Long Description of the product 4",
                "40.00",
                "https://via.placeholder.com/100",
                5F,
                400,
                true
            )
        )

        productLoading.value = false
        productsLoadingError.value = false
        products.value = mockData
    }
}