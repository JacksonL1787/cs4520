package com.cs4520.assignment5.view.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cs4520.assignment5.R
import com.cs4520.assignment5.view.common.Routes
import com.cs4520.assignment5.view.ui.login.LoginScreen
import com.cs4520.assignment5.view.ui.product.list.ProductListScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                elevation = 4.dp
            )
        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = Routes.LOGIN.path, modifier = Modifier.padding(innerPadding)) {
            composable(Routes.LOGIN.path) { LoginScreen(navController) }
            composable(Routes.PRODUCT_LIST.path) { ProductListScreen()}
        }
    }

}