package com.cs4520.assignment5.view.ui.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cs4520.assignment5.R
import com.cs4520.assignment5.view.common.Routes
import com.cs4520.assignment5.view.util.observeAsState

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: LoginViewModel = viewModel()
    val context = LocalContext.current

    val username = viewModel.username.observeAsState("")
    val password = viewModel.password.observeAsState("")

    LaunchedEffect(key1 = true) {
        viewModel.successEvent.collect { success ->
            if (success) {
                viewModel.clearCredentials()
                navController.navigate(Routes.PRODUCT_LIST.path)
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.errorEvent.collect { errorType ->
            val resId = getErrorMessageResId(errorType)
            Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.log_in_title),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        TextField(
            value = username.value,
            onValueChange = viewModel::setUsername,
            label = { Text(stringResource(id = R.string.username_text)) },
            placeholder = { Text(stringResource(id = R.string.username_placeholder)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        TextField(
            value = password.value,
            onValueChange = viewModel::setPassword,
            label = { Text(stringResource(id = R.string.password_text)) },
            placeholder = { Text(stringResource(id = R.string.password_placeholder)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        Button(onClick = { viewModel.login() }) {
            Text(stringResource(id = R.string.log_in_button_text))
        }
    }
}

private fun getErrorMessageResId(errorType: LoginErrorType): Int {
    return when (errorType) {
        LoginErrorType.USERNAME_MISSING -> R.string.username_missing_error
        LoginErrorType.PASSWORD_MISSING -> R.string.password_missing_error
        LoginErrorType.INVALID_CREDENTIALS -> R.string.invalid_credentials_error
    }
}