package com.dharringtondev.moviewatchlist.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dharringtondev.moviewatchlist.R
import com.dharringtondev.moviewatchlist.adapters.LibrariesAdapter
import com.dharringtondev.moviewatchlist.databinding.FragmentLibrariesBinding

class LibrariesFragment: Fragment(), LibrariesAdapter.OnItemClickedListener {
    private val TAG = "LibrariesFragment"

    private var _binding: FragmentLibrariesBinding? = null
    private val binding get() = _binding!!

    private val librariesAdapter = LibrariesAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLibrariesBinding.inflate(inflater, container, false)
        initComponents()
        return binding.root
    }

    private fun initComponents() {
        binding.librariesRv.layoutManager = LinearLayoutManager(context)
        binding.librariesRv.setHasFixedSize(true)
        binding.librariesRv.adapter = librariesAdapter
        binding.librariesRv.addItemDecoration(DividerItemDecoration(binding.librariesRv.context, LinearLayoutManager.VERTICAL))
        librariesAdapter.setItemClickedListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(itemName: String) {
        when(itemName) {
            "Glide" -> {
                goToUrl(resources.getString(R.string.glide_url))
            }
            "Gson" -> {
                goToUrl(resources.getString(R.string.gson_url))
            }
            "Navigation Component" -> {
                goToUrl(resources.getString(R.string.nav_component_url))
            }
            "OMDb API" -> {
                goToUrl(resources.getString(R.string.omdb_url))
            }
            "Paging Library V3" -> {
                goToUrl(resources.getString(R.string.paging_url))
            }
            "Retrofit" -> {
                goToUrl(resources.getString(R.string.retrofit_url))
            }
            "Room" -> {
                goToUrl(resources.getString(R.string.room_url))
            }
            "RxJava3" -> {
                goToUrl(resources.getString(R.string.rxjava_url))
            }
            "sdp" -> {
                goToUrl(resources.getString(R.string.sdp_url))
            }
        }
    }

    private fun goToUrl(url: String) {
        Log.d(TAG, "goToUrl; url = $url")
        val uri = Uri.parse(url)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
}