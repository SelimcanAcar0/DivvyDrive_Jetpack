package com.example.divvydrivestaj.widgets

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

@Composable
fun dosyaSecmeButon(onClick: (Uri) -> Unit) {
    val context = LocalContext.current
    val getContent = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            onClick(uri)
        }
    }

    IconButton(
        onClick = {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                getContent.launch("image/*")
            } else {
                // İzin alınmamışsa izin iste
                // Bu noktada izinleri yönetmek için gerekli işlemleri ekleyebilirsiniz.
            }
        },
        modifier = Modifier
            .background(Color.Gray, shape = MaterialTheme.shapes.small)
            .padding(16.dp)
    ) {
        Icon(imageVector = Icons.Default.Done, contentDescription = "Select File")
    }
}