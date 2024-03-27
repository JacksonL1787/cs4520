package com.cs4520.assignment5.view.ui.product.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cs4520.assignment5.R
import com.cs4520.assignment5.data.AppDatabase
import com.cs4520.assignment5.data.products.ProductRepository
import com.cs4520.assignment5.databinding.FragmentProductListBinding
import com.cs4520.assignment5.view.common.UIState
import com.cs4520.assignment5.view.util.NetworkRepository

class ProductListFragment : Fragment() {
    private val viewModel: ProductListViewModel by viewModels {
        val application = requireNotNull(this.activity).application

        val productDao = AppDatabase.get(application).productDao()
        val productRepo = ProductRepository(productDao)

        val networkRepo = NetworkRepository(application)

        ProductListViewModelFactory(productRepo, networkRepo)
    }

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    private val productListItemAdapter = ProductListItemAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadProducts()
        binding.productListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productListItemAdapter
            itemAnimator = null
        }
        initObservers()
        initButtonListeners()
    }

    private fun initObservers() {
        viewModel.pageNumber.observe(viewLifecycleOwner) { pageNumber ->
            val formattedPageNumber = pageNumber + 1
            binding.pageNumberTextView.text = getString(R.string.page_number, formattedPageNumber)
        }

        viewModel.productList.observe(viewLifecycleOwner) { productList ->
            productListItemAdapter.submitList(productList)
        }

        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            val statesAndViews = listOf(
                Pair(UIState.SUCCESS, binding.productListRecyclerView),
                Pair(UIState.EMPTY, binding.productListEmptyLayout),
                Pair(UIState.LOADING, binding.productListProgressBar),
                Pair(UIState.ERROR, binding.productListErrorLayout)
            )

            statesAndViews.forEach { (state, view) ->
                if (state == uiState) {
                    view.visibility = View.VISIBLE
                    return@forEach
                }
                view.visibility = View.GONE
            }
        }
    }

    private fun initButtonListeners() {
        binding.productListEmptyRetryButton.setOnClickListener {
            viewModel.loadProducts()
        }

        binding.productListErrorRetryButton.setOnClickListener {
            viewModel.loadProducts()
        }

        binding.nextPageButton.setOnClickListener {
            viewModel.nextPage()
        }

        binding.previousPageButton.setOnClickListener {
            viewModel.prevPage()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}