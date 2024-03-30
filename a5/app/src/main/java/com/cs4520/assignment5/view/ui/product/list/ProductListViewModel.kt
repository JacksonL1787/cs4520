package com.cs4520.assignment5.view.ui.product.list

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.cs4520.assignment5.api.product.ProductApiConfig
import com.cs4520.assignment5.data.AppDatabase
import com.cs4520.assignment5.data.products.ProductEntity
import com.cs4520.assignment5.data.products.ProductRepository
import com.cs4520.assignment5.view.common.UIState
import com.cs4520.assignment5.view.ui.product.Product
import com.cs4520.assignment5.view.ui.product.ProductType
import com.cs4520.assignment5.view.util.NetworkRepository
import com.cs4520.assignment5.workers.ProductListUpdateWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

private fun OneTimeWorkRequest.Builder.addPageNumber(page: Int): OneTimeWorkRequest.Builder {
    val data = Data.Builder()
    data.putInt(ProductListUpdateWorker.PAGE_NUMBER_INPUT, page)
    this.setInputData(data.build())
    return this
}

class ProductListViewModel(
    private val appContext: Context,
) : ViewModel() {
    private val workManager = WorkManager.getInstance(appContext)

    private val productDao = AppDatabase.get(appContext).productDao()
    private val productRepo = ProductRepository(productDao)

    private val networkRepo = NetworkRepository(appContext)

    private val _pageNumber = MutableLiveData(0)
    val pageNumber: LiveData<Int> = _pageNumber

    private val _uiState = MutableLiveData<UIState>()
    val uiState: LiveData<UIState> = _uiState

    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>> = _productList

    init {
        refreshData()
    }

    fun refreshData() {
        val pageNumber = this.pageNumber.value ?: 0

        // Try to set from database if offline
        if (!networkRepo.isOnline()) {
            viewModelScope.launch {
                val productList = getProductsFromDatabase(pageNumber)
                setProductsInState(productList)
            }
            return
        }

        _uiState.value = UIState.LOADING
        val req = OneTimeWorkRequestBuilder<ProductListUpdateWorker>()
            .addPageNumber(pageNumber)
            .build()
        workManager.enqueue(req)
        handleProductListUpdateWorkInfo(req.id,
            onSuccess = {
                viewModelScope.launch {
                    val productList = getProductsFromDatabase(pageNumber)
                    setProductsInState(productList)
                }
            },
            onFailure = {
                _uiState.value = UIState.ERROR
            })
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
            refreshData()
        }
    }

    private fun setProductsInState(productList: List<Product>) {
        if (productList.isEmpty()) {
            _uiState.value = UIState.EMPTY
            return
        }

        _productList.value = productList
        _uiState.value = UIState.SUCCESS
    }

    private fun handleProductListUpdateWorkInfo(
        workerID: UUID,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        val data = workManager.getWorkInfoByIdLiveData(workerID)

        val observer = object : Observer<WorkInfo> {
            override fun onChanged(workInfo: WorkInfo) {
                if (workInfo.state.isFinished) {
                    when (workInfo.state) {
                        WorkInfo.State.SUCCEEDED -> onSuccess()
                        WorkInfo.State.FAILED -> onFailure()
                    }
                    data.removeObserver(this)
                }
            }
        }

        data.observeForever(observer)
    }

    private suspend fun getProductsFromDatabase(page: Int): List<Product> {
        val entities = withContext(Dispatchers.IO) { productRepo.getProductsFromPage(page) }

        return entities.mapNotNull { convertProductEntityToProduct(it) }
    }

    private fun isValidPageNumber(n: Int): Boolean {
        return n in 0..ProductApiConfig.MAX_PAGE_NUMBER
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
