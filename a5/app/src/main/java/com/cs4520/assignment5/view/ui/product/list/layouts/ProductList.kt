package com.cs4520.assignment5.view.ui.product.list.layouts

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.cs4520.assignment5.view.ui.product.Product

@Composable
fun ProductList(productList: List<Product>) {
    LazyColumn {
        items(items = productList, key = { it.hashCode() }, ) {
            ProductListItem(it)
        }
    }
}