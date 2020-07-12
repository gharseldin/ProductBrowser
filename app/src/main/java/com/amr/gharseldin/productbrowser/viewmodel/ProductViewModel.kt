package com.amr.gharseldin.productbrowser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amr.gharseldin.productbrowser.model.Product
import com.amr.gharseldin.productbrowser.model.ProductList
import com.amr.gharseldin.productbrowser.model.ProductRepository
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
        fetchProducts()
//        fetchMockProducts()
    }

    private fun fetchProducts() {
        productLoading.value = true
        ProductRepository.fetchApiProducts()
            ?.subscribeWith(object : DisposableSingleObserver<List<Product>>() {
                override fun onSuccess(prods: List<Product>?) {
                    // TODO can handle more edge cases with the response status
                    products.value = prods
                    productLoading.value = false
                    productsLoadingError.value = false
                }

                override fun onError(e: Throwable?) {
                    productLoading.value = false
                    productsLoadingError.value = true
                }
            })
    }

    private fun fetchMockProducts() {
        productLoading.value = true
        ProductRepository.fetchMockProducts()
            ?.subscribeWith(object : DisposableSingleObserver<ProductList>() {
                override fun onSuccess(value: ProductList?) {
                    products.value = value?.products
                    productLoading.value = false
                    productsLoadingError.value = false
                }

                override fun onError(e: Throwable?) {
                    productLoading.value = false
                    productsLoadingError.value = true
                }
            })
    }

    fun loadMore(size:Int) {
        if (productLoading.value == false && size<ProductRepository.totalDataCount) {
            productLoading.value = true
            ProductRepository.loadMore()
                ?.subscribeWith(object : DisposableSingleObserver<List<Product>>() {
                    override fun onSuccess(prods: List<Product>?) {

                        val list = mutableListOf<Product>()
                        prods?.let { list.addAll(it) }
                        products.value?.let { list.addAll(it) }
                        products.value = list
                        productLoading.value = false
                        productsLoadingError.value = false
                    }

                    override fun onError(e: Throwable?) {
                        productLoading.value = false
                        productsLoadingError.value = true
                    }
                })
        }
    }
}