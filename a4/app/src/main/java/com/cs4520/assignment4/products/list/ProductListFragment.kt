package com.cs4520.assignment4.products.list

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cs4520.assignment4.products.Product
import com.cs4520.assignment4.data.productsDataset
import com.cs4520.assignment4.databinding.FragmentProductListBinding
import java.time.LocalDate

class ProductListFragment : Fragment() {
    private val viewModel: ProductListViewModel by viewModels()
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadProducts()

        val products = makeProductList(productsDataset)

        val productsAdapter = ProductListItemAdapter()
        binding.productListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productsAdapter
        }
        productsAdapter.submitList(products)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun makeProductList(list: List<List<Any?>>): List<Product> {
        return list.mapNotNull { p ->
            // Safe cast the array values to their given types.
            // Exclude item from list if the value is required and null
            val name = p[0] as? String ?: return@mapNotNull null
            val type = p[1] as? String ?: return@mapNotNull null
            val expiryDate = p[2] as? String
            val price = (p[3] as? Number)?.toDouble() ?: return@mapNotNull null


            when (type) {
                "Food" -> Product(name, price, expiryDate, "Food")
                "Equipment" -> Product(name, price, expiryDate, "Equipment")
                else -> null
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}