package com.dharringtondev.moviewatchlist.ui.dialogfragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dharringtondev.moviewatchlist.R
import com.dharringtondev.moviewatchlist.databinding.AlertDialogTutorialBinding
import com.dharringtondev.moviewatchlist.viewmodel.MovieViewModel
import com.dharringtondev.moviewatchlist.viewmodel.MovieViewModelFactory

class TutorialDialog: DialogFragment() {
    private val TAG = "TutorialDialog"

    private val TUTORIAL_BOOL_KEY = "TutorialBool"

    private var _binding: AlertDialogTutorialBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d(TAG, "onCreateDialog")
        _binding = AlertDialogTutorialBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(binding.root).setPositiveButton("Let's Go!") { _, _ ->
            Log.d(TAG, "positive button clicked")
            dismiss()
            findNavController().previousBackStackEntry?.savedStateHandle?.set(TUTORIAL_BOOL_KEY, true)
        }
        return builder.create()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}