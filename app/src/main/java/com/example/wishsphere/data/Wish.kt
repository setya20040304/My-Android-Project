package com.example.wishsphere.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wish_table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "wish_title")
    val title: String,

    @ColumnInfo(name = "wish_description")
    val description: String,

    @ColumnInfo(name = "wish_is_completed")
    val isCompleted: Boolean = false,

    @ColumnInfo(name = "wish_category")
    val category: String = "Lainnya",

    @ColumnInfo(name = "wish_priority")
    val priority: String = "Normal",

    @ColumnInfo(name = "wish_image_uri")
    val imageUri: String? = null,

    @ColumnInfo(name = "wish_notes")
    val notes: String? = null
)