package com.cs4520.assignment5.view.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

@Composable
fun <R, T : R> LiveData<T>.observeAsState(initial: R): State<R> {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state = remember {
        mutableStateOf(value ?: initial)
    }

    DisposableEffect(this, lifecycleOwner) {
        val observer = Observer<T> { state.value = it }
        observe(lifecycleOwner, observer)
        onDispose { removeObserver(observer) }
    }

    return state
}