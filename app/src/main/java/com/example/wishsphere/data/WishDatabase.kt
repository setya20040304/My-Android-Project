package com.example.wishsphere.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// NAIKKAN VERSI DARI 4 MENJADI 5
@Database(entities = [Wish::class], version = 5, exportSchema = false)
abstract class WishDatabase : RoomDatabase() {
    abstract fun wishDao(): WishDao

    companion object {
        @Volatile
        private var INSTANCE: WishDatabase? = null

        fun getDatabase(context: Context): WishDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WishDatabase::class.java,
                    "wish_database"
                )
                    // TAMBAHKAN MIGRASI 4_5 DI SINI
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE wish_table ADD COLUMN wish_is_completed INTEGER NOT NULL DEFAULT 0")
    }
}

val MIGRATION_2_3: Migration = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE wish_table ADD COLUMN wish_category TEXT NOT NULL DEFAULT 'Lainnya'")
        db.execSQL("ALTER TABLE wish_table ADD COLUMN wish_priority TEXT NOT NULL DEFAULT 'Normal'")
    }
}

val MIGRATION_3_4: Migration = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE wish_table ADD COLUMN wish_image_uri TEXT DEFAULT NULL")
    }
}

// BUAT OBJEK MIGRASI BARU INI
val MIGRATION_4_5: Migration = object : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE wish_table ADD COLUMN wish_notes TEXT DEFAULT NULL")
    }
}