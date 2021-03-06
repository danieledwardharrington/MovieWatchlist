package com.dharringtondev.moviewatchlist.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dharringtondev.moviewatchlist.R
import com.dharringtondev.moviewatchlist.adapters.CoffeeAdapter
import com.dharringtondev.moviewatchlist.databinding.FragmentCoffeeBinding
import com.dharringtondev.moviewatchlist.products.CoffeeProduct

class CoffeeFragment: Fragment(), CoffeeAdapter.OnProductClickedListener {

    private val TAG = "CoffeeFragment"

    private var _binding: FragmentCoffeeBinding? = null
    private val binding get() = _binding!!

    private val coffeeAdapter = CoffeeAdapter()

    private var coffeeProductList = ArrayList<CoffeeProduct>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCoffeeBinding.inflate(inflater, container, false)
        initComponents()
        return binding.root
    }

    private fun initComponents() {
        setupList()
        initRV()
    }

    private fun setupList() {
        coffeeProductList = arrayListOf<CoffeeProduct>(CoffeeProduct("Espresso", "$0.99", resources.getString(R.string.espresso_id)),
            CoffeeProduct("Latte", "$1.99", resources.getString(R.string.latte_id)),
            CoffeeProduct("Triple Latte", "$2.99", resources.getString(R.string.triple_latte_id)),
            CoffeeProduct("Quad Latte", "$3.99", resources.getString(R.string.quad_latte_id)))
    }

    private fun initRV() {
        coffeeAdapter.setProductListener(this@CoffeeFragment)
        coffeeAdapter.submitList(coffeeProductList)
        binding.coffeeRv.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = coffeeAdapter
            addItemDecoration(DividerItemDecoration(binding.coffeeRv.context, LinearLayoutManager.VERTICAL))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onProductClicked(coffeeProduct: CoffeeProduct) {

    }

}