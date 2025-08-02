package com.example.wishsphere.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.wishsphere.data.Wish

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WishListScreen(
    navToaddWish: () -> Unit,
    wishes: List<Wish>,
    onDeleteWish: (Wish) -> Unit,
    onItemClick: (Wish) -> Unit,
    onCheckedChange: (Wish, Boolean) -> Unit
) {
    // State untuk mengontrol dialog
    var showDeleteDialog by remember { mutableStateOf(false) }
    var wishToDelete by remember { mutableStateOf<Wish?>(null) }

    // Tampilkan AlertDialog jika showDeleteDialog bernilai true
    if (showDeleteDialog && wishToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Hapus Keinginan") },
            text = { Text("Yakin ingin menghapus '${wishToDelete!!.title}'?") },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteWish(wishToDelete!!)
                        showDeleteDialog = false
                    }
                ) {
                    Text("Ya, Hapus")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showDeleteDialog = false }
                ) {
                    Text("Batal")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Wishes", style = MaterialTheme.typography.headlineLarge) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navToaddWish,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add a Wish")
            }
        }
    ) { paddingValues ->
        if (wishes.isEmpty()) {
            // Tampilan jika daftar kosong
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Belum ada keinginan.\nAyo tambahkan satu!",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            }
        } else {
            // Tampilan jika daftar ada isinya
            LazyColumn(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                items(
                    items = wishes,
                    key = { wish -> wish.id }
                ) { wish ->
                    WishItem(
                        modifier = Modifier.animateItemPlacement(),
                        wish = wish,
                        onItemClick = { onItemClick(wish) },
                        onCheckedChange = { isChecked -> onCheckedChange(wish, isChecked) },
                        // Saat hapus diklik, tampilkan dialog
                        onDeleteClick = {
                            wishToDelete = wish
                            showDeleteDialog = true
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun WishItem(
    modifier: Modifier = Modifier,
    wish: Wish,
    onDeleteClick: () -> Unit,
    onItemClick: () -> Unit,
    onCheckedChange: (Boolean) -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onItemClick() },
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!wish.imageUri.isNullOrEmpty()) {
                AsyncImage(
                    model = wish.imageUri,
                    contentDescription = wish.title,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoCamera,
                        contentDescription = "No Image",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = wish.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (wish.isCompleted) Color.Gray else MaterialTheme.colorScheme.onSurface,
                    textDecoration = if (wish.isCompleted) TextDecoration.LineThrough else null
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Kategori: ${wish.category}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Checkbox(
                checked = wish.isCompleted,
                onCheckedChange = onCheckedChange
            )
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Wish",
                    tint = Color.Gray
                )
            }
        }
    }
}