package com.cs4520.assignment5.view.ui.product.list.layouts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cs4520.assignment5.R
import com.cs4520.assignment5.view.ui.product.Product
import com.cs4520.assignment5.view.ui.product.ProductType
import androidx.compose.ui.graphics.Color as ComposeColor

@Composable
fun ProductListItem(product: Product) {

    Box(
        modifier = Modifier
            .background(getProductBackgroundColor(product.type))
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            // Assuming you have a drawable resource for the product image
            Image(
                painter = getProductImage(product.type),
                contentDescription = null, // Accessibility description
                modifier = Modifier
                    .size(50.dp)
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = product.name,
                    color = ComposeColor.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.product_price, product.price),
                    color = ComposeColor.Black,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
        if(product.expiryDate != null) {
            Text(
                text = stringResource(R.string.product_expiry_date, product.expiryDate),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp)
            )
        }

    }
}

@Composable
private fun getProductImage(type: ProductType): Painter {
    return painterResource(when (type) {
        ProductType.EQUIPMENT -> R.drawable.equipment
        ProductType.FOOD -> R.drawable.food
    })
}

@Composable
private fun getProductBackgroundColor(type: ProductType): ComposeColor {
    return colorResource(when (type) {
        ProductType.EQUIPMENT -> R.color.product_equipment_background
        ProductType.FOOD -> R.color.product_food_background
    })
}
