package com.dharringtondev.moviewatchlist.ui.dialogfragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.dharringtondev.moviewatchlist.databinding.AlertDialogTutorialBinding
import com.dharringtondev.moviewatchlist.viewmodel.MovieViewModel
import com.dharringtondev.moviewatchlist.viewmodel.MovieViewModelFactory

class TutorialDialog: DialogFragment() {
    private val TAG = "TutorialDialog"

    private var _binding: AlertDialogTutorialBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieViewModel: MovieViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d(TAG, "onCreateDialog")
        _binding = AlertDialogTutorialBinding.inflate(LayoutInflater.from(context))
        initComponents()
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(binding.root).setPositiveButton("Let's Go!") { _, _ ->
            Log.d(TAG, "positive button clicked")
            dismiss()
            movieViewModel.setSeen(true)
        }
        return builder.create()
    }

    private fun initComponents() {
        movieViewModel = ViewModelProvider(this, MovieViewModelFactory(requireActivity().application)).get(MovieViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}