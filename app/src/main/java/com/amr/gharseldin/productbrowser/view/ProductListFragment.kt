package com.amr.gharseldin.productbrowser.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amr.gharseldin.productbrowser.R
import com.amr.gharseldin.productbrowser.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.product_list.*


public class ProductListFragment : Fragment() {

    private lateinit var productViewModel: ProductViewModel
    private val productAdapter =
        ProductAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.product_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        productViewModel.refresh()

        productsList.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = productAdapter
            addOnScrollListener(object: RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val totalItemCount = (layoutManager as LinearLayoutManager).itemCount
                    val lastVisibleItem = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    if(lastVisibleItem > totalItemCount-5) {
                            productViewModel.loadMore(totalItemCount)
                    }
                }
            })
        }

        swipeRefresh.setOnRefreshListener {
            productViewModel.refresh()
            swipeRefresh.isRefreshing = false
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {

        productViewModel.products.observe(viewLifecycleOwner, Observer { products ->
            products?.let {
                productsList.visibility = View.VISIBLE
                productAdapter.updateProducts(it)
            }
        })

        productViewModel.productsLoadingError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let { loadingErrorText.visibility = if (it) View.VISIBLE else View.GONE }
        })

        productViewModel.productLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                loadingProgressBar.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    loadingErrorText.visibility = View.GONE
                    productsList.visibility = View.GONE
                }
            }
        })
    }
}
