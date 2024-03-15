package com.cs4520.assignment4.view.ui.product.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cs4520.assignment4.data.products.ProductRepository
import com.cs4520.assignment4.view.util.NetworkRepository

class ProductListViewModelFactory(
    private val productRepo: ProductRepository,
    private val networkRepo: NetworkRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductListViewModel(this.productRepo, this.networkRepo) as T
        }
        throw IllegalArgumentException("viewModel must be of a ProductListViewModel")
    }
}