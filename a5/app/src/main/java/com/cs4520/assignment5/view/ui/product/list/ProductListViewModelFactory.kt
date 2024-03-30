package com.cs4520.assignment5.view.ui.product.list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cs4520.assignment5.data.products.ProductRepository
import com.cs4520.assignment5.view.util.NetworkRepository

class ProductListViewModelFactory(
    private val applicationContext: Context,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductListViewModel(this.applicationContext) as T
        }
        throw IllegalArgumentException("viewModel must be a ProductListViewModel")
    }
}