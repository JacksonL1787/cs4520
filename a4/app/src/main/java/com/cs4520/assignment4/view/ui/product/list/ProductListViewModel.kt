package com.cs4520.assignment4.view.ui.product.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cs4520.assignment4.api.product.ProductApiConfig
import com.cs4520.assignment4.api.product.ProductApiFactory
import com.cs4520.assignment4.api.product.ProductData
import com.cs4520.assignment4.data.products.ProductEntity
import com.cs4520.assignment4.data.products.ProductRepository
import com.cs4520.assignment4.view.common.UIState
import com.cs4520.assignment4.view.ui.product.Product
import com.cs4520.assignment4.view.ui.product.ProductType
import com.cs4520.assignment4.view.util.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductListViewModel(
    private val productRepo: ProductRepository,
    private val networkRepo: NetworkRepository
) : ViewModel() {
    private val productApi = ProductApiFactory().create()

    private val _pageNumber = MutableLiveData(0)
    val pageNumber: LiveData<Int> = _pageNumber

    private val _uiState = MutableLiveData<UIState>()
    val uiState: LiveData<UIState> = _uiState

    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>> = _productList

    fun loadProducts() {
        val pageNumber = this.pageNumber.value ?: 0
        _uiState.value = UIState.LOADING
        _productList.value = listOf()

        viewModelScope.launch {
            if (!networkRepo.isOnline()) {
                val productList = getProductListFromDatabase(pageNumber)
                updateProductList(productList)
                return@launch
            }

            try {
                val data = getProductDataListFromApi(pageNumber)

                // Show empty state if raw data is empty
                if (data.isEmpty()) {
                    _uiState.value = UIState.EMPTY
                    return@launch
                }

                insertProductsIntoDatabase(pageNumber, data)

                // Converts list of ProductData to Product to be sent to UI
                val productList = data.mapNotNull {
                    convertProductDataToProduct(it)
                }
                
                updateProductList(productList)
            } catch (e: Exception) {
                _uiState.value = UIState.ERROR
                return@launch
            }
        }
    }

    fun nextPage() {
        val newPage = pageNumber.value?.plus(1) ?: 0
        updatePage(newPage)
    }

    fun prevPage() {
        val newPage = pageNumber.value?.minus(1) ?: 0
        updatePage(newPage)
    }

    private fun updatePage(new: Int) {
        if (isValidPageNumber(new)) {
            _pageNumber.value = new
            loadProducts()
        }
    }

    private fun isValidPageNumber(n: Int): Boolean {
        return n in 0..ProductApiConfig.MAX_PAGE_NUMBER
    }

    private fun updateProductList(productList: List<Product>) {
        _productList.value = productList

        if (productList.isEmpty()) {
            _uiState.value = UIState.EMPTY
            return
        }

        _uiState.value = UIState.SUCCESS
    }

    private suspend fun insertProductsIntoDatabase(page: Int, productList: List<ProductData>) {
        val productEntities = productList.map { product ->
            ProductEntity(
                name = product.name,
                price = product.price,
                expiryDate = product.expiryDate,
                type = product.type,
                page = page
            )
        }

        withContext(Dispatchers.IO) {
            productRepo.updateProductsForPage(page, productEntities)
        }
    }

    private suspend fun getProductDataListFromApi(page: Int): List<ProductData> {
        val res = withContext(Dispatchers.IO) {
            productApi.listProducts(page)
        }

        if (!res.isSuccessful || res.body() == null) {
            throw Exception("Error fetching data")
        }

        return res.body()!!
    }

    private suspend fun getProductListFromDatabase(page: Int): List<Product> {
        val productEntities = withContext(Dispatchers.IO) {
            productRepo.getProductsFromPage(page)
        }
        return productEntities.mapNotNull { entity ->
            convertProductEntityToProduct(entity)
        }
    }

    private fun convertProductDataToProduct(data: ProductData): Product? {
        val type = getProductType(data.type) ?: return null

        return Product(
            name = data.name,
            price = data.price,
            expiryDate = data.expiryDate,
            type = type
        )
    }

    private fun convertProductEntityToProduct(entity: ProductEntity): Product? {
        val type = getProductType(entity.type) ?: return null

        return Product(
            name = entity.name,
            price = entity.price,
            expiryDate = entity.expiryDate,
            type = type
        )
    }

    private fun getProductType(typeStr: String): ProductType? {
        return when (typeStr) {
            "Food" -> ProductType.FOOD
            "Equipment" -> ProductType.EQUIPMENT
            else -> null
        }
    }
}