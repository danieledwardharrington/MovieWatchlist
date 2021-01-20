package com.dharringtondev.moviewatchlist.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dharringtondev.moviewatchlist.R
import com.dharringtondev.moviewatchlist.databinding.CardViewCoffeeBinding
import com.dharringtondev.moviewatchlist.products.CoffeeProduct

class CoffeeAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TAG = "CoffeeAdapter"

    private var coffeeProductList = ArrayList<CoffeeProduct>()
    private lateinit var productClickedListener: OnProductClickedListener

    interface OnProductClickedListener {
        fun onProductClicked(coffeeProduct: CoffeeProduct)
    }

    fun setProductListener(newListener: OnProductClickedListener) {
        productClickedListener = newListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = CardViewCoffeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoffeeViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CoffeeViewHolder -> {
                holder.bind(coffeeProductList[position])
                holder.itemView.setOnClickListener {
                    Log.d(TAG, "product clicked; id = ${coffeeProductList[position].productId}")
                    productClickedListener.onProductClicked(coffeeProductList[position])
                }
            }
        }
    }

    fun submitList(newList: ArrayList<CoffeeProduct>) {
        coffeeProductList = newList
    }

    override fun getItemCount(): Int {
        return coffeeProductList.size
    }

    class CoffeeViewHolder(itemBinding: CardViewCoffeeBinding): RecyclerView.ViewHolder(itemBinding.root) {
        private val coffeeNameTv = itemBinding.coffeeNameTv
        private val coffeePriceTv = itemBinding.coffeePriceTv

        fun bind(coffeeProduct: CoffeeProduct) {
            coffeeNameTv.text = coffeeProduct.productName
            coffeePriceTv.text = coffeeProduct.productPrice
        }
    }
}