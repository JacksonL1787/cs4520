package com.cs4520.assignment1.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment1.R
import com.cs4520.assignment1.data.Product
import com.cs4520.assignment1.databinding.ProductItemBinding

class ProductsAdapter :
    ListAdapter<Product, ProductsAdapter.ProductViewHolder>(ProductDiffCallback) {

    class ProductViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.productName.text = product.name
            binding.productPrice.text =
                itemView.context.getString(R.string.product_price, product.price)

            if (product.expiryDate == null) {
                binding.productExpiryDate.visibility = View.GONE
            } else {
                binding.productExpiryDate.visibility = View.VISIBLE
                binding.productExpiryDate.text = itemView.context.getString(R.string.expiry_date, product.expiryDate)
            }

            val image: Int
            val background: Int
            when (product) {
                is Product.Equipment -> {
                    image = R.drawable.equipment
                    background = Color.parseColor("#E06666")
                }

                is Product.Food -> {
                    image = R.drawable.food
                    background = Color.parseColor("#FFD965")
                }
            }

            binding.productImage.setImageResource(image)
            binding.productContainer.setBackgroundColor(background)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductItemBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    /* Gets current flower and uses it to bind view. */
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

}

object ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        // Using name for now, since the dataset provided has unique names for all products.
        // In the future, products should really be assigned an ID.
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}