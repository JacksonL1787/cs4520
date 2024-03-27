package com.cs4520.assignment5.view.ui.product.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cs4520.assignment5.R

@Composable
fun ProductListPageNavigation(onPreviousClick: () -> Unit, onNextClick: () -> Unit, pageNumber: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousClick) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_left_24),
                contentDescription = stringResource(R.string.previous_page)
            )
        }
        Text(
            text = pageNumber,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onNextClick) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_right_24),
                contentDescription = stringResource(R.string.next_page)
            )
        }
    }
}

@Composable
fun ProductList(visible: Boolean = false) {
    if (visible) {
        LazyColumn {
            // Populate with items
        }
    }
}

@Composable
fun ProductListScreen(navController: NavController) {
    Column{
        ProductListPageNavigation(onNextClick = {/**/}, onPreviousClick = {/**/}, pageNumber = "")
    }
}