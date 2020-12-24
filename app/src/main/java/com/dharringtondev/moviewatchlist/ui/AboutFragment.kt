package com.dharringtondev.moviewatchlist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dharringtondev.moviewatchlist.R
import com.dharringtondev.moviewatchlist.adapters.AboutAdapter
import com.dharringtondev.moviewatchlist.databinding.FragmentAboutBinding

class AboutFragment: Fragment(), AboutAdapter.OnItemClickedListener {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    private val aboutAdapter = AboutAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        initComponents()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initComponents() {
        initRV()
        aboutAdapter.setItemClickedListener(this)
    }

    private fun initRV() {
        binding.aboutRv.layoutManager = LinearLayoutManager(context)
        binding.aboutRv.setHasFixedSize(true)
        binding.aboutRv.adapter = aboutAdapter
        binding.aboutRv.addItemDecoration(DividerItemDecoration(binding.aboutRv.context, LinearLayoutManager.VERTICAL))
    }

    override fun onItemClicked(itemName: String) {
        when (itemName) {
            "Donate" -> {

            }
            "Rate" -> {

            }
            "Share" -> {

            }
            "Licenses" -> {

            }
        }
    }
}