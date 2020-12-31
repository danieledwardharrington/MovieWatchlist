package com.dharringtondev.moviewatchlist.ui.dialogfragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.dharringtondev.moviewatchlist.databinding.AlertDialogTutorialBinding

class TutorialDialog: DialogFragment() {
    private val TAG = "TutorialDialog"

    private var _binding: AlertDialogTutorialBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d(TAG, "onCreateDialog")
        _binding = AlertDialogTutorialBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(binding.root).setPositiveButton("Let's Go!") { _, _ -> dismiss() }
        return builder.create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}