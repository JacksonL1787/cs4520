package com.cs4520.assignment4.products.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cs4520.assignment4.common.UIState
import com.cs4520.assignment4.databinding.FragmentProductListBinding

class ProductListFragment : Fragment() {
    private lateinit var viewModel: ProductListViewModel
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    private val productListItemAdapter = ProductListItemAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[ProductListViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadProducts()

        binding.productListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productListItemAdapter
        }
        initObservers()
        initButtonListeners()
    }

    private fun initObservers() {
        viewModel.productList.observe(viewLifecycleOwner) { productList ->
            println(productList)
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
                    print(state)
                    println(view)
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
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}