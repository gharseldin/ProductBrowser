package com.amr.gharseldin.productbrowser.utils

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.amr.gharseldin.productbrowser.R
import com.amr.gharseldin.productbrowser.model.BASE_URL
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(url:String?, progressDrawable: CircularProgressDrawable){
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)
    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(BASE_URL+url)
        .into(this)
}

fun TextView.setUnitPrice(price:String?){
    this.text = price?.split("$")?.get(1)?.split(".")?.get(0)?:"0"
}

fun TextView.setDecimalPrice(price:String?){
    this.text = price?.split("$")?.get(1)?.split(".")?.get(1)?:"0"
}

operator fun <T> MutableLiveData<ArrayList<T>>.plusAssign(values: List<T>) {
    val value = this.value ?: arrayListOf()
    value.addAll(values)
    this.value = value
}