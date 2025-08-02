package com.example.wishsphere.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wishsphere.data.WishRepository

class WishViewModelFactory(private val repository: WishRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WishViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WishViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}