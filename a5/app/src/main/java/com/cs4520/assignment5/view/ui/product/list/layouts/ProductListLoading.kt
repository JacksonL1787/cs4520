package com.cs4520.assignment5.view.ui.product.list.layouts

import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProductListLoading() {
    CircularProgressIndicator(modifier = Modifier
        .padding(top = 20.dp))
}