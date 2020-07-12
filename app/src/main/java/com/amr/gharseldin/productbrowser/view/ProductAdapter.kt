package com.amr.gharseldin.productbrowser.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amr.gharseldin.productbrowser.R
import com.amr.gharseldin.productbrowser.model.Product
import kotlinx.android.synthetic.main.product_list_row.view.*

class ProductAdapter(var products: ArrayList<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {


    fun updateProducts(newProducts: List<Product>) {
        products.clear()
        products.addAll(newProducts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.product_list_row,
                parent,
                false
            )
        )

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindTo(products[position])
    }

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val productName = view.productName
        private val productDescription = view.productDescription
        private val ratingBar = view.ratingBar
        private val totalReviews = view.totalReviews
        private val isAvailable = view.isAvailable
        private val productPrice = view.productPrice
        private val productImage = view.productImage

        fun bindTo(product: Product) {
            productName.text = product.productName
            productDescription.text = product.shortDescription
            if (product.inStock == true) {
                isAvailable.text = "In Stock"
                isAvailable.setTextColor(65280)
            } else {
                isAvailable.text = "Unavailable"
                isAvailable.setTextColor(15663104)
            }
            ratingBar.rating = product.reviewRating ?: 0.0F
            totalReviews.text = "(${product.reviewCount})"
            // TODO adjust the formatting of the price on the screen if there is time
            productPrice
            // TODO create an extension function for loading images
        }
    }
}