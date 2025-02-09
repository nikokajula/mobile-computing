package com.example.hw1

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp



@Composable
fun ProfileScreen(
    onNavigateToConversation: () -> Unit
) {
    Column(modifier = Modifier.padding(all = 20.dp)) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Profile of Lexi",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleSmall
        )

        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = null,
            modifier = Modifier
                .size(240.dp)
                .border(1.5.dp, MaterialTheme.colorScheme.primary)
        )

        Surface(
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 1.dp,
        ) {
            Text(
                "Hello you can send me a message if you need to ask something",
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(onClick = onNavigateToConversation) {
            Text(text = "Redundant Back button because these modern phones are bad and are missing physical buttons which always worked but the on screen buttons sometimes don't appear although gestures can be used but I have no experience with those")
        }
    }
}