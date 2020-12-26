package com.dharringtondev.moviewatchlist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dharringtondev.moviewatchlist.adapters.CoffeeAdapter
import com.dharringtondev.moviewatchlist.databinding.FragmentCoffeeBinding

class CoffeeFragment: Fragment() {

    private val TAG = "CoffeeFragment"

    private var _binding: FragmentCoffeeBinding? = null
    private val binding get() = _binding!!

    private val coffeeAdapter = CoffeeAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCoffeeBinding.inflate(inflater, container, false)
        initComponents()
        return binding.root
    }

    fun initComponents() {
        binding.coffeeRv.layoutManager = LinearLayoutManager(context)
        binding.coffeeRv.setHasFixedSize(true)
        binding.coffeeRv.adapter = coffeeAdapter
        binding.coffeeRv.addItemDecoration(DividerItemDecoration(binding.coffeeRv.context, LinearLayoutManager.VERTICAL))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}