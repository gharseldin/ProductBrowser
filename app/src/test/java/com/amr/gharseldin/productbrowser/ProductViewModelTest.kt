package com.amr.gharseldin.productbrowser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.amr.gharseldin.productbrowser.model.Product
import com.amr.gharseldin.productbrowser.model.ProductList
import com.amr.gharseldin.productbrowser.model.ProductRepository
import com.amr.gharseldin.productbrowser.model.ProductsService
import com.amr.gharseldin.productbrowser.viewmodel.ProductViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ProductViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var productsService: ProductsService

    @Mock
    lateinit var productRepository: ProductRepository

    @InjectMocks
    var productViewModel = ProductViewModel()

    private var testSingle: Single<ProductList>? = null
    private var testRepositorySingle: Single<List<Product>>? = null

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }

    @Before
    fun setUpRxSchedulers(){
        val immediate = object : Scheduler() {
            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler{ scheduler -> immediate}
        RxJavaPlugins.setInitComputationSchedulerHandler {scheduler -> immediate}
        RxJavaPlugins.setInitNewThreadSchedulerHandler {scheduler -> immediate}
        RxJavaPlugins.setSingleSchedulerHandler{scheduler -> immediate}
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
    }

    @Test
    fun getProductsTest(){
        val product = Product(
            "001",
            "Product 1",
            "Short description 1",
            "Long Description of the product 1",
            "$10.00",
            "/images/image2.jpeg",
            3.5F,
            100,
            true
        )
        val productList = arrayListOf(product)
        val productsResponse = ProductList(productList,1,1,1,200)
        testSingle = Single.just(productsResponse)

        `when`(productsService.getProducts(1, 30)).thenReturn(testSingle)

        productViewModel.refresh()

        Assert.assertEquals(30, productViewModel.products.value?.size)
        Assert.assertEquals(false, productViewModel.productLoading.value)
        Assert.assertEquals(false, productViewModel.productsLoadingError.value)
    }

    @Test
    fun loadProductsTest(){
        testRepositorySingle = Single.just(listOf(Product(
                "001",
            "Product 1",
            "Short description 1",
            "Long Description of the product 1",
            "$10.00",
            "/images/image2.jpeg",
            3.5F,
            100,
            true
        )))

        `when`(productRepository.loadMore()).thenReturn(testRepositorySingle)

        productViewModel.refresh()
        productViewModel.loadMore(30)

        Assert.assertEquals(60, productViewModel.products.value?.size)
        Assert.assertEquals(false, productViewModel.productLoading.value)
        Assert.assertEquals(false, productViewModel.productsLoadingError.value)
    }
}