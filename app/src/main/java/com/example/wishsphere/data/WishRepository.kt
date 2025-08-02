package com.example.wishsphere.data

import kotlinx.coroutines.flow.Flow

class WishRepository(private val wishDao: WishDao) {
    val allWishes: Flow<List<Wish>> = wishDao.getAllWishes()

    suspend fun addWish(wish: Wish) {
        wishDao.addWish(wish)
    }
    suspend fun deleteWish(wish: Wish) {
        wishDao.deleteWish(wish)
    }
    fun getWishById(id: Long): Flow<Wish>{
        return wishDao.getWishById(id)
    }
    suspend fun updateWish(wish: Wish) {
        wishDao.updateWish(wish)
    }
}