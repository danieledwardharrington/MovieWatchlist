package com.dharringtondev.moviewatchlist.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dharringtondev.moviewatchlist.R
import com.dharringtondev.moviewatchlist.databinding.CardViewAboutBinding

class AboutAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val aboutList = arrayListOf<String>("Donate", "Rate", "Share", "Licenses")
    private val iconList = arrayListOf<Int>(R.drawable.ic_donate, R.drawable.ic_rate, R.drawable.ic_share, R.drawable.ic_licenses)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = CardViewAboutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AboutAdapter.AboutViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AboutAdapter.AboutViewHolder -> {
                holder.bind(aboutList[position], iconList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return aboutList.size
    }

    class AboutViewHolder constructor(itemBinding: CardViewAboutBinding): RecyclerView.ViewHolder(itemBinding.root) {
        private val aboutIconIV = itemBinding.aboutIconIv
        private val aboutItemTV = itemBinding.aboutItemTv

        fun bind(aboutItem: String, aboutIcon: Int) {
            aboutItemTV.text = aboutItem
            aboutIconIV.setBackgroundResource(aboutIcon)
        }
    }
}