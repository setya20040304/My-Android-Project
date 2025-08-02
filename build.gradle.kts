// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    // HAPUS BARIS 'kotlin.compose' DARI SINI
    id("com.google.devtools.ksp") version "1.9.23-1.0.19" apply false // Pastikan versi ini cocok dengan versi Kotlin-mu
}