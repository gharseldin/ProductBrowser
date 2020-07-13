package com.amr.gharseldin.productbrowser.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.amr.gharseldin.productbrowser.R
import com.amr.gharseldin.productbrowser.model.ProductList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(
                    android.R.id.content,
                    ProductListFragment()
                ).addToBackStack(null)
            }
        }
    }
}