package com.dharringtondev.moviewatchlist.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dharringtondev.moviewatchlist.R
import com.dharringtondev.moviewatchlist.adapters.AboutAdapter
import com.dharringtondev.moviewatchlist.databinding.FragmentAboutBinding
import com.google.android.play.core.review.ReviewManagerFactory

class AboutFragment: Fragment(), AboutAdapter.OnItemClickedListener {

    private val TAG = "AboutFragment"

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
        Log.d(TAG, itemName)
        val navController = findNavController()
        when (itemName) {
            "Buy Me Coffee" -> {
                navController.navigate(R.id.coffeeFragment)
            }
            "Rate" -> {
                launchReview()
            }
            "Share" -> {

            }
            "Libraries" -> {
                navController.navigate(R.id.librariesFragment)
            }
            "Contact" -> {
                goToUrl(resources.getString(R.string.google_form_url))
            }
            "GitHub" -> {
                goToUrl(resources.getString(R.string.github_url))
            }
            "Tutorial" -> {
                navController.navigate(R.id.tutorialDialog)
            }
        }
    }

    private fun launchReview() {
        val manager = ReviewManagerFactory.create(requireContext())
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { request ->
            if (request.isSuccessful) {
                // We got the ReviewInfo object
                val reviewInfo = request.result
                val flow = activity?.let { manager.launchReviewFlow(it, reviewInfo) }
                flow!!.addOnCompleteListener { _ ->
                }
            } else {
                Log.e(TAG, "Error with review")
                showShortToast("Error leaving review")
            }
        }
    }

    private fun goToUrl(url: String) {
        Log.d(TAG, "goToUrl; url = $url")
        val uri = Uri.parse(url)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    private fun showShortToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}