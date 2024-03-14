package com.cs4520.assignment4.products.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cs4520.assignment4.common.UIState
import com.cs4520.assignment4.products.Product

class ProductListViewModel : ViewModel() {
    private val productService = ProductListService()

    private val _uiState = MutableLiveData<UIState>()
    val uiState: LiveData<UIState> = _uiState

    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>> = _productList

    fun loadProducts() {
        productService.listProducts {products ->
            products?.let {
                _productList.value = products
            }
        }
    }
}