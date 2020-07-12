package com.amr.gharseldin.productbrowser.view

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amr.gharseldin.productbrowser.R
import com.amr.gharseldin.productbrowser.model.Product
import com.amr.gharseldin.productbrowser.utils.getProgressDrawable
import com.amr.gharseldin.productbrowser.utils.loadImage
import com.amr.gharseldin.productbrowser.utils.setDecimalPrice
import com.amr.gharseldin.productbrowser.utils.setUnitPrice
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
        private val inStock = view.inStock
        private val unavailable = view.unavailable
        private val productUnitPrice = view.unitsPrice
        private val productDecimalPrice = view.decimalPrice
        private val productImage = view.productImage
        private val progressDrawable = getProgressDrawable(view.context)

        fun bindTo(product: Product) {
            productName.text = product.productName
            productDescription.text = Html.fromHtml(product.shortDescription?:"");
            if (product.inStock == true) {
                inStock.visibility = View.VISIBLE
                unavailable.visibility = View.GONE
            } else {
                inStock.visibility = View.GONE
                unavailable.visibility = View.VISIBLE
            }
            ratingBar.rating = product.reviewRating ?: 0.0F
            totalReviews.text = "(${product.reviewCount})"
            productUnitPrice.setUnitPrice(product.price)
            productDecimalPrice.setDecimalPrice(product.price)
            productImage.loadImage(product.productImage, progressDrawable)
        }
    }
}