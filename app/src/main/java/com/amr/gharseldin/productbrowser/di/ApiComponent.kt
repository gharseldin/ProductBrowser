package com.amr.gharseldin.productbrowser.di

import com.amr.gharseldin.productbrowser.model.ProductRepository
import com.amr.gharseldin.productbrowser.model.ProductsService
import com.amr.gharseldin.productbrowser.viewmodel.ProductViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: ProductsService)
}