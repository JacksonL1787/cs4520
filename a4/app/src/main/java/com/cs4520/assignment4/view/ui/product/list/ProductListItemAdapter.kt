package com.cs4520.assignment4.view.ui.product.list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment4.R
import com.cs4520.assignment4.databinding.ProductItemBinding
import com.cs4520.assignment4.view.ui.product.Product
import com.cs4520.assignment4.view.ui.product.ProductType

class ProductListItemAdapter :
    ListAdapter<Product, ProductListItemAdapter.ProductViewHolder>(ProductDiffCallback) {

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
            return when (product.type) {
                ProductType.EQUIPMENT -> R.drawable.equipment
                ProductType.FOOD -> R.drawable.food
            }
        }

        private fun getProductBackgroundColor(product: Product): Int {
            return when (product.type) {
                ProductType.EQUIPMENT -> Color.parseColor("#E06666")
                ProductType.FOOD -> Color.parseColor("#FFD965")
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
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}