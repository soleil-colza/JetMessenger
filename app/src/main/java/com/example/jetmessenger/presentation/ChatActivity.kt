package com.example.jetmessenger.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jetmessenger.data.ChatRepositoryImpl
import com.example.jetmessenger.ui.theme.JetMessengerTheme
import kotlinx.coroutines.Dispatchers

class ChatActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(
            this,
            ChatViewModel.ChatViewModelFactory(ChatRepositoryImpl(Dispatchers.IO))
        )[ChatViewModel::class.java]


        setContent {
            JetMessengerTheme {
                val textState = viewModel.textState.collectAsStateWithLifecycle()

                ChatScreen(
                    textState = textState.value,
                    onUpdateText = viewModel::updateText,
                    onClickFabButton = viewModel::sendMessage,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatScreen(
    textState: String,
    onUpdateText: (String) -> Unit,
    onClickFabButton: (String) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = { onClickFabButton(textState) }
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send"
                )
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            TextField(
                modifier = Modifier.padding(horizontal = 16.dp),
                value = textState,
                onValueChange = { onUpdateText(it) },
                label = { Text("Type whatever you like 🙌🏻") }
            )
        }
    }
}


@Preview
@Composable
private fun ChatScreenPreview() {
    JetMessengerTheme {
        ChatScreen(
            textState = "Preview",
            onUpdateText = {},
            onClickFabButton = {}
        )
    }
}