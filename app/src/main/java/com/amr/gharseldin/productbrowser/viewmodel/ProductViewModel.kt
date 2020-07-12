package com.amr.gharseldin.productbrowser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amr.gharseldin.productbrowser.model.Product
import com.amr.gharseldin.productbrowser.model.ProductList
import com.amr.gharseldin.productbrowser.model.ProductsService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ProductViewModel : ViewModel() {

    private val productService = ProductsService()
    private val disposable = CompositeDisposable()
    val products = MutableLiveData<List<Product>>()
    val productsLoadingError = MutableLiveData<Boolean>()
    val productLoading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchMockProducts()
        fetchApiProducts()
    }

    private fun fetchApiProducts() {
        productLoading.value = true
        disposable.add(productService.getProducts(1, 30)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<ProductList>() {
                override fun onSuccess(value: ProductList?) {
                    // TODO can handle more edge cases with the response status
                    products.value = value?.products
                    productLoading.value = false
                    productsLoadingError.value = false
                }

                override fun onError(e: Throwable?) {
                    productLoading.value = false
                    productsLoadingError.value = true
                }
            })
        )

    }

    private fun fetchMockProducts() {
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