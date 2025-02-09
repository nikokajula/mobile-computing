package com.example.hw1

import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun NewMessageScreen(
    onNavigateToConversation: () -> Unit
) {
    var text by remember { mutableStateOf("Input Text") }

    TextField(
        value = text,
        onValueChange = { text = it }
    )
    Button(onClick = {
        text
        onNavigateToConversation()
    }) {
        text = "Back"
    }
}