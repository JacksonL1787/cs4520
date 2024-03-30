package com.cs4520.assignment5.view.ui.product.list

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cs4520.assignment5.R
import com.cs4520.assignment5.data.AppDatabase
import com.cs4520.assignment5.data.products.ProductRepository
import com.cs4520.assignment5.view.common.UIState
import com.cs4520.assignment5.view.ui.product.list.layouts.ProductList
import com.cs4520.assignment5.view.ui.product.list.layouts.ProductListLoading
import com.cs4520.assignment5.view.ui.product.list.layouts.ProductListPageNavigation
import com.cs4520.assignment5.view.ui.product.list.layouts.ProductListRetry
import com.cs4520.assignment5.view.util.NetworkRepository
import com.cs4520.assignment5.view.util.observeAsState


@Composable
fun ProductListScreen() {
    val context = LocalContext.current
    val applicationContext = context.applicationContext
    val viewModel: ProductListViewModel = viewModel(factory = ProductListViewModelFactory(applicationContext))
    val productList = viewModel.productList.observeAsState(listOf())
    val uiState = viewModel.uiState.observeAsState(UIState.LOADING)
    val pageNumber = viewModel.pageNumber.observeAsState(0)


    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProductListPageNavigation(
            onNextClick = viewModel::nextPage,
            onPreviousClick = viewModel::prevPage,
            pageNumber = pageNumber.value + 1
        )
        
        when (uiState.value) {
            UIState.SUCCESS -> ProductList(productList.value)
            UIState.LOADING -> ProductListLoading()
            UIState.EMPTY -> ProductListRetry(
                message = stringResource(R.string.product_list_empty),
                onRetryClick = viewModel::loadProducts
            )

            UIState.ERROR -> ProductListRetry(
                message = stringResource(R.string.product_list_error),
                onRetryClick = viewModel::loadProducts
            )
        }
    }
}