package com.example.wishsphere.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import androidx.room.Update

@Dao
interface WishDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWish(wish: Wish)

    // Flow akan otomatis update UI jika data di database berubah
    @Query("SELECT * FROM wish_table")
    fun getAllWishes(): Flow<List<Wish>>

    @Query("SELECT * from wish_table WHERE id = :id")
    fun getWishById(id:Long): Flow<Wish>

    @Update
    suspend fun updateWish(wish: Wish)

    @Delete
    suspend fun deleteWish(wish: Wish)
}