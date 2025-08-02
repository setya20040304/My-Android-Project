package com.example.wishsphere

import android.app.Application
import com.example.wishsphere.data.WishDatabase
import com.example.wishsphere.data.WishRepository

class WishApp : Application() {
    // Inisialisasi hanya saat dibutuhkan
    private val database by lazy { WishDatabase.getDatabase(this) }
    val repository by lazy { WishRepository(database.wishDao()) }
}