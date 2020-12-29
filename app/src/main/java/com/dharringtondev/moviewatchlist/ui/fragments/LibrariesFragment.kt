package com.dharringtondev.moviewatchlist.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dharringtondev.moviewatchlist.adapters.LibrariesAdapter
import com.dharringtondev.moviewatchlist.databinding.FragmentLibrariesBinding

class LibrariesFragment: Fragment() {
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}