package com.example.hw1

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun NewMessageScreen(
    applicationContext: Context,
    messages: SavedMessageDao,
    onNavigateToConversation: () -> Unit
) {
    var newMessageText by remember { mutableStateOf("Input Text") }
    var newImage  by remember { mutableStateOf<Uri?>(null) }
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            newImage = uri
        }
    }

    // Launch the photo picker and let the user choose only images.
    //pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

    Column(modifier = Modifier.padding(all = 20.dp)) {
        Button(onClick = { pickMedia.launch("image/*") }) {
            Text(text = "Load Image")
        }
        newImage?.let {
            AsyncImage(
                model = ImageRequest.Builder(applicationContext)
                    .data(newImage)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
            )
        }

        TextField(
            value = newMessageText,
            onValueChange = { newMessageText = it }
        )

        val messageId = messages.getMessageCount()

        Button(onClick = {
            val resolver = applicationContext.contentResolver
            val newFile = applicationContext.openFileOutput(messageId.toString(), Context.MODE_PRIVATE)
            var imageName: String? = null
            newImage?.let {
                resolver.openInputStream(it)?.copyTo(newFile)
                newFile.flush()
                imageName = messageId.toString()
            }

            messages.insert(
                SavedMessage(
                    messageId,
                    "Lexi",
                    newMessageText,
                    imageName
            )
            )

            onNavigateToConversation()
        }) {
            Text(text = "Save")
        }
    }
}