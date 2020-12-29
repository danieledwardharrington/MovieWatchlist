package com.dharringtondev.moviewatchlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dharringtondev.moviewatchlist.databinding.CardViewLibrariesBinding

class LibrariesAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = "LibrariesAdapter"

    private val librariesList = arrayListOf<String>("Glide", "Gson", "Navigation Component", "OMDb API", "Retrofit", "Room", "RxJava3", "sdp")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = CardViewLibrariesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LibrariesAdapter.LibrariesViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LibrariesViewHolder -> {
                holder.bind(librariesList[position])
            }
        }

    }

    override fun getItemCount(): Int {
        return librariesList.size
    }

    class LibrariesViewHolder(itemBinding: CardViewLibrariesBinding): RecyclerView.ViewHolder(itemBinding.root) {
        val librariesItemTV = itemBinding.librariesItemTv

        fun bind(libraryName: String) {
            librariesItemTV.text = libraryName
        }
    }
}