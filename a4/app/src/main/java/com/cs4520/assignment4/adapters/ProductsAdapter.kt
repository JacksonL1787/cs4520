package com.cs4520.assignment4.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment4.R
import com.cs4520.assignment4.data.Product
import com.cs4520.assignment4.databinding.ProductItemBinding

class ProductsAdapter :
    ListAdapter<Product, ProductsAdapter.ProductViewHolder>(ProductDiffCallback) {

    class ProductViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            with(binding) {
                productName.text = product.name
                productPrice.text =
                    itemView.context.getString(R.string.product_price, product.price)

                if (product.expiryDate == null) {
                    productExpiryDate.visibility = View.GONE
                } else {
                    productExpiryDate.visibility = View.VISIBLE
                    productExpiryDate.text =
                        itemView.context.getString(R.string.product_expiry_date, product.expiryDate)
                }

                productImage.setImageResource(getProductImage(product))
                productContainer.setBackgroundColor(getProductBackgroundColor(product))
            }

        }

        private fun getProductImage(product: Product): Int {
            return when (product) {
                is Product.Equipment -> R.drawable.equipment
                is Product.Food -> R.drawable.food
            }
        }

        private fun getProductBackgroundColor(product: Product): Int {
            return when (product) {
                is Product.Equipment -> Color.parseColor("#E06666")
                is Product.Food -> Color.parseColor("#FFD965")
            }
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
        // In the future, products should be assigned an ID.
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}