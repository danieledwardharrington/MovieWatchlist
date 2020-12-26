package com.dharringtondev.moviewatchlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dharringtondev.moviewatchlist.databinding.CardViewCoffeeBinding

class CoffeeAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val coffeeList = arrayListOf<String>("Latte", "Triple Latte", "Quad Latte")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = CardViewCoffeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoffeeAdapter.CoffeeViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CoffeeViewHolder -> {
                holder.bind(coffeeList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return coffeeList.size
    }

    class CoffeeViewHolder(itemBinding: CardViewCoffeeBinding): RecyclerView.ViewHolder(itemBinding.root) {
        val coffeeNameTv = itemBinding.coffeeItemTv

        fun bind(coffeeName: String) {
            coffeeNameTv.text = coffeeName
        }
    }
}