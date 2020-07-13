package com.amr.gharseldin.productbrowser.viewmodel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.amr.gharseldin.productbrowser.model.Product
import com.amr.gharseldin.productbrowser.model.ProductRepository
import com.amr.gharseldin.productbrowser.utils.getProgressDrawable
import com.amr.gharseldin.productbrowser.utils.loadImage

class ProductDisplayViewModel:ViewModel() {

    private var product: Product? = null

    fun getModel(id:String) =
        product ?: ProductRepository.findProductById(id).also { product = it}
}