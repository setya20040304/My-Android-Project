package com.example.wishsphere.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.wishsphere.data.Wish

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishDetailScreen(
    wish: Wish,
    onEditClick: () -> Unit,
    onNavigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detail Wish") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            wish.imageUri?.let { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = wish.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            Text("Judul:", style = MaterialTheme.typography.labelMedium)
            Text(wish.title, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))

            Text("Deskripsi:", style = MaterialTheme.typography.labelMedium)
            Text(wish.description, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(24.dp))

            Row {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Kategori:", style = MaterialTheme.typography.labelMedium)
                    Text(wish.category, style = MaterialTheme.typography.bodyLarge)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Prioritas:", style = MaterialTheme.typography.labelMedium)
                    Text(wish.priority, style = MaterialTheme.typography.bodyLarge)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            // TAMBAHKAN BAGIAN INI UNTUK MENAMPILKAN CATATAN
            // Hanya tampil jika catatannya tidak kosong
            if (!wish.notes.isNullOrEmpty()) {
                Text("Catatan Progres:", style = MaterialTheme.typography.labelMedium)
                Text(wish.notes, style = MaterialTheme.typography.bodyLarge)
            }

            Spacer(modifier = Modifier.weight(1f, fill = false))
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onEditClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Edit")
            }
        }
    }
}