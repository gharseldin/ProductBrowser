package com.amr.gharseldin.productbrowser.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.amr.gharseldin.productbrowser.R
import com.amr.gharseldin.productbrowser.databinding.ProductDetailsBinding
import com.amr.gharseldin.productbrowser.model.BASE_URL
import com.amr.gharseldin.productbrowser.utils.getProgressDrawable
import com.amr.gharseldin.productbrowser.utils.loadImage
import com.amr.gharseldin.productbrowser.viewmodel.ProductDisplayViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.lang.IllegalStateException

public const val ARG_PRODUCT_ID = "product Id argument"

class ProductDetailFragment : Fragment() {
    private lateinit var binding: ProductDetailsBinding

    companion object {
        fun newInstance(productID: String?) = ProductDetailFragment().apply {
            arguments = bundleOf(ARG_PRODUCT_ID to productID)
        }

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, url: String?) {
            if (!url.isNullOrEmpty()) {
                Glide.with(view)
                    .load(BASE_URL + url)
                    .into(view)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ProductDetailsBinding.inflate(inflater, container, false)
        .apply { binding = this }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productViewModel = ViewModelProviders.of(this).get(ProductDisplayViewModel::class.java)
        val product = productViewModel.getModel(
            arguments?.getString(ARG_PRODUCT_ID)
                ?: throw IllegalStateException("no modelId provided")
        )

        product?.let {
            binding.product = product
        }
    }
}