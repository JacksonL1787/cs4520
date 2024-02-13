package com.cs4520.assignment1.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.cs4520.assignment1.R
import com.cs4520.assignment1.data.Product
import com.cs4520.assignment1.data.productsDataset
import com.cs4520.assignment1.databinding.FragmentLoginBinding
import com.cs4520.assignment1.databinding.FragmentProductListBinding
import com.cs4520.assignment1.ui.adapters.ProductsAdapter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Date
import java.util.Locale

class ProductListFragment : Fragment() {
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
        println(productsDataset)
        val products = makeProductList(productsDataset)
        println(products)

        val productsAdapter = ProductsAdapter()
        binding.productsRecyclerView.apply {
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
            val expiryString = p[2] as? String
            val price = (p[3] as? Number)?.toDouble() ?: return@mapNotNull null

            val expiryDate = try {
                LocalDate.parse(expiryString)
            } catch (e: Exception) {
                null
            }

            when (type) {
                "Food" -> Product.Food(name, price, expiryDate)
                "Equipment" -> Product.Equipment(name, price, expiryDate)
                else -> null
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}