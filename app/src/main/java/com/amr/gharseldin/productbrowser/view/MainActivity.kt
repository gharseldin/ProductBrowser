package com.amr.gharseldin.productbrowser.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amr.gharseldin.productbrowser.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .add(android.R.id.content, ProductListFragment())
            .commit()
    }
}