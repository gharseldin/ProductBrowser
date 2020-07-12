package com.amr.gharseldin.productbrowser.model

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.operators.single.SingleObserveOn
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlin.math.ceil
import kotlin.math.max

object ProductRepository {

    private val productService = ProductsService()
    private var page = 1
    private const val pageSize = 30
    private var maxPage = 1
    var totalDataCount = 0

    fun fetchApiProducts(): Single<List<Product>>? {
        page = 1
        return Single.create { emitter ->
            productService.getProducts(page, pageSize)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ProductList>() {
                    override fun onSuccess(value: ProductList?) {
                        totalDataCount = value?.totalProducts ?: 0
                        page++
                        maxPage = totalDataCount/ pageSize
                        if(totalDataCount% pageSize!=0) maxPage++
                        emitter.onSuccess(value?.products)
                    }

                    override fun onError(e: Throwable?) {
                        emitter.onError(e)
                    }

                })
        }
    }


    fun loadMore():Single<List<Product>> {
        return Single.create { emitter ->
            productService.getProducts(page, pageSize)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ProductList>() {
                    override fun onSuccess(value: ProductList?) {
                        totalDataCount = value?.totalProducts ?: 0
                        page++
                        maxPage = totalDataCount/ pageSize
                        if(totalDataCount% pageSize!=0) maxPage++
                        emitter.onSuccess(value?.products)
                    }

                    override fun onError(e: Throwable?) {
                        emitter.onError(e)
                    }

                })
        }
    }

    fun fetchMockProducts(): Single<ProductList> {
        val mockData = listOf(
            Product(
                "001",
                "Product 1",
                "Short description 1",
                "Long Description of the product 1",
                "$10.00",
                "/images/image2.jpeg",
                3.5F,
                100,
                true
            ),
            Product(
                "002",
                "Product 2",
                "Short description 2",
                "Long Description of the product 2",
                "$20.00",
                "/images/image2.jpeg",
                4.0F,
                200,
                true
            ),
            Product(
                "003",
                "Product 3",
                "Short description 3",
                "Long Description of the product 3",
                "$30.00",
                "/images/image2.jpeg",
                4.5F,
                300,
                true
            ),
            Product(
                "004",
                "Product 4",
                "Short description 4",
                "Long Description of the product 4",
                "$40.00",
                "/images/image2.jpeg",
                5F,
                400,
                true
            )
        )
        return Single.create<ProductList> { emitter ->
            val productList = ProductList(mockData, 4, 1, 30, 200)
            emitter.onSuccess(productList)
        }
    }
}