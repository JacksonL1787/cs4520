package com.cs4520.assignment4.products.list

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cs4520.assignment4.common.UIState
import com.cs4520.assignment4.data.DatabaseClient
import com.cs4520.assignment4.data.products.ProductDao
import com.cs4520.assignment4.data.products.ProductEntity
import com.cs4520.assignment4.products.Product
import com.cs4520.assignment4.products.ProductData
import com.cs4520.assignment4.products.toProduct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductListViewModel(application: Application) : AndroidViewModel(application) {
    private val productService = ProductListService()
    private val productDao = DatabaseClient.get(application).productDao()

    private val _uiState = MutableLiveData<UIState>()
    val uiState: LiveData<UIState> = _uiState

    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>> = _productList

    fun loadProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            println(productDao.getAll())
        }

        setUIState(UIState.LOADING)
        productService.listProductsFromApi { data, success ->
            if (!success || data == null) {
                setUIState(UIState.ERROR)
                return@listProductsFromApi
            }

            insertProductsIntoDatabase(0, data)

            val productList = data!!.mapNotNull { productData ->
                try {
                    productData.toProduct()
                } catch (e: Exception) {
                    println(e.message)
                    return@mapNotNull null
                }
            }
            _productList.value = productList

            if (productList.isEmpty()) {
                setUIState(UIState.EMPTY)
                return@listProductsFromApi
            }

            setUIState(UIState.SUCCESS)
        }
    }

    private fun insertProductsIntoDatabase(page: Int, productList: List<ProductData>) {
        val productEntities = productList.map { product ->
            ProductEntity(
                name = product.name,
                price = product.price,
                expiryDate = product.expiryDate,
                type = product.type,
                page = page
            )
        }

        CoroutineScope(Dispatchers.IO).launch {
            productDao.insertAll(*productEntities.toTypedArray())
        }
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting == true
    }


    private fun setUIState(state: UIState) {
        _uiState.postValue(state)
    }
}