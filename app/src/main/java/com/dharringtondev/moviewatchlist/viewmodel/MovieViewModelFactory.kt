package com.dharringtondev.moviewatchlist.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MovieViewModelFactory(application: Application): ViewModelProvider.Factory {

    private var mApplication = application

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MovieViewModel(mApplication) as T
    }
}