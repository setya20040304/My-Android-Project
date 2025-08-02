package com.example.wishsphere.ui

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.wishsphere.data.Wish

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWishScreen(
    navigateUp: () -> Unit,
    onAddWish: (Wish) -> Unit,
    onUpdateWish: (Wish) -> Unit,
    wish: Wish? = null
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Lainnya") }
    var priority by remember { mutableStateOf("Normal") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    // 1. TAMBAHKAN STATE INI untuk menampung teks catatan
    var notes by remember { mutableStateOf("") }

    val context = LocalContext.current
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION
                context.contentResolver.takePersistableUriPermission(it, takeFlags)
                imageUri = it
            }
        }
    )
    val categoryOptions = listOf("Travel", "Pendidikan", "Karier", "Kesehatan", "Lainnya")
    val priorityOptions = listOf("Tinggi", "Normal", "Rendah")
    var isCategoryExpanded by remember { mutableStateOf(false) }
    var isPriorityExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = wish) {
        if (wish != null) {
            title = wish.title
            description = wish.description
            category = wish.category
            priority = wish.priority
            imageUri = wish.imageUri?.let { Uri.parse(it) }
            // 2. TAMBAHKAN BARIS INI untuk mengisi catatan saat mode edit
            notes = wish.notes ?: ""
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (wish == null) "Add Wish" else "Edit Wish") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Judul Keinginan") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Deskripsi") },
                modifier = Modifier.fillMaxWidth().height(120.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            ExposedDropdownMenuBox(
                expanded = isCategoryExpanded,
                onExpandedChange = { isCategoryExpanded = it }
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Kategori") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCategoryExpanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = isCategoryExpanded,
                    onDismissRequest = { isCategoryExpanded = false }
                ) {
                    categoryOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                category = option
                                isCategoryExpanded = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            ExposedDropdownMenuBox(
                expanded = isPriorityExpanded,
                onExpandedChange = { isPriorityExpanded = it }
            ) {
                OutlinedTextField(
                    value = priority,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Prioritas") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isPriorityExpanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = isPriorityExpanded,
                    onDismissRequest = { isPriorityExpanded = false }
                ) {
                    priorityOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                priority = option
                                isPriorityExpanded = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // 3. TAMBAHKAN TEXTFIELD INI untuk menampilkan kolom input catatan
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Catatan Progres") },
                modifier = Modifier.fillMaxWidth().height(120.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = { imagePickerLauncher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Pilih Gambar")
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Selected Image",
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    if (title.isNotEmpty()) {
                        val newWishData = Wish(
                            id = wish?.id ?: 0L,
                            title = title,
                            description = description,
                            isCompleted = wish?.isCompleted ?: false,
                            category = category,
                            priority = priority,
                            imageUri = imageUri?.toString(),
                            // 4. TAMBAHKAN NOTES DI SINI agar ikut tersimpan
                            notes = notes
                        )

                        if (wish != null) {
                            onUpdateWish(newWishData)
                        } else {
                            onAddWish(newWishData)
                        }
                        navigateUp()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}