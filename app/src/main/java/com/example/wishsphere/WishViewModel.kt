package com.example.wishsphere.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wishsphere.data.Wish
import com.example.wishsphere.data.WishRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WishViewModel(private val repository: WishRepository) : ViewModel() {

    val wishes: StateFlow<List<Wish>> = repository.allWishes.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Variabel untuk menyimpan wish yang terakhir dihapus
    private var lastDeletedWish: Wish? = null

    fun addWish(wish: Wish) {
        viewModelScope.launch {
            repository.addWish(wish)
        }
    }

    fun getWishById(id: Long): StateFlow<Wish?> {
        return repository.getWishById(id).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
    }

    fun updateWish(wish: Wish) {
        viewModelScope.launch {
            repository.updateWish(wish)
        }
    }

    // MODIFIKASI FUNGSI INI
    fun deleteWish(wish: Wish) {
        // Simpan dulu wish yang mau dihapus
        lastDeletedWish = wish
        viewModelScope.launch {
            repository.deleteWish(wish)
        }
    }

    // TAMBAHKAN FUNGSI BARU INI
    fun undoDelete() {
        // Ambil wish yang disimpan, lalu tambahkan kembali ke database
        lastDeletedWish?.let { wish ->
            addWish(wish)
        }
    }
}