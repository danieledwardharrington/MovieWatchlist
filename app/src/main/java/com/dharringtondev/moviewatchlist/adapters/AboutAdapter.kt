package com.dharringtondev.moviewatchlist.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dharringtondev.moviewatchlist.R
import com.dharringtondev.moviewatchlist.databinding.CardViewAboutBinding

class AboutAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = "AboutAdapter"

    private val aboutList = arrayListOf("Buy Me Coffee", "Rate", "Share", "Tutorial", "Libraries", "Contact", "GitHub")
    private val iconList = arrayListOf(R.drawable.ic_coffee, R.drawable.ic_rate, R.drawable.ic_share, R.drawable.ic_tutorial, R.drawable.ic_libraries, R.drawable.ic_contact, R.drawable.ic_github)
    private lateinit var clickedListener: OnItemClickedListener

    interface OnItemClickedListener {
        fun onItemClicked(itemName: String)
    }

    fun setItemClickedListener(newListener: OnItemClickedListener) {
        clickedListener = newListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = CardViewAboutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AboutViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AboutViewHolder -> {
                holder.bind(aboutList[position], iconList[position])
                holder.itemView.setOnClickListener {
                    Log.d(TAG, "item clicked")
                    clickedListener.onItemClicked(aboutList[position])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return aboutList.size
    }

    class AboutViewHolder(itemBinding: CardViewAboutBinding): RecyclerView.ViewHolder(itemBinding.root) {
        private val aboutIconIV = itemBinding.aboutIconIv
        private val aboutItemTV = itemBinding.aboutItemTv

        fun bind(aboutItem: String, aboutIcon: Int) {
            aboutItemTV.text = aboutItem
            aboutIconIV.setBackgroundResource(aboutIcon)
        }
    }
}