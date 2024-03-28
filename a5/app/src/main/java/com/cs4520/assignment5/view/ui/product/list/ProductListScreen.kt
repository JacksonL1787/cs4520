package com.cs4520.assignment5.view.ui.product.list

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cs4520.assignment5.data.AppDatabase
import com.cs4520.assignment5.data.products.ProductRepository
import com.cs4520.assignment5.view.ui.product.list.layout.ProductListPageNavigation
import com.cs4520.assignment5.view.util.NetworkRepository

private fun initViewModelFactory(context: Context): ProductListViewModelFactory {
    val productDao = AppDatabase.get(context).productDao()
    val productRepo = ProductRepository(productDao)
    val networkRepo = NetworkRepository(context)

    return ProductListViewModelFactory(productRepo, networkRepo)
}

@Composable
fun ProductListScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: ProductListViewModel = viewModel(factory = initViewModelFactory(context))

    Column {
        ProductListPageNavigation(onNextClick = {/**/ }, onPreviousClick = {/**/ }, pageNumber = "")
    }
}