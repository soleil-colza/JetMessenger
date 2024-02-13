package com.example.jetmessenger.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetmessenger.data.ChatUiState
import com.example.jetmessenger.data.ReceivedMessage
import com.example.jetmessenger.data.api.sendMessageApi
import com.example.jetmessenger.data.repository.GetMessagesRepositoryImpl
import com.example.jetmessenger.data.repository.SendMessageRepositoryImpl
import com.example.jetmessenger.ui.theme.JetMessengerTheme
import getMessagesApi
import kotlinx.coroutines.Dispatchers

class ChatActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetMessengerTheme {
                val viewModel: ChatViewModel = viewModel(
                    factory = ChatViewModelFactory(
                        SendMessageRepositoryImpl(Dispatchers.IO, sendMessageApi),
                        GetMessagesRepositoryImpl(Dispatchers.IO, getMessagesApi)
                    )
                )
                val uiState = viewModel.uiState.collectAsState()
                ChatScreen(
                    uiState = uiState.value,
                    onUpdateText = { newText -> viewModel.updateText(newText) }
                ) { viewModel.sendMessage(uiState.value.inputText) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    uiState: ChatUiState,
    onUpdateText: (String) -> Unit,
    onClickFabButton: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onClickFabButton,
                containerColor = Color(0xFF2E3A59)
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = Color.White
                )
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp, bottom = 72.dp),
                    reverseLayout = true
                ) {
                    items(uiState.messages) { message ->
                        MessageCard(message = message)
                    }
                }

                TextField(
                    modifier = Modifier
                        .padding(start = 20.dp, bottom = 16.dp)
                        .align(Alignment.BottomStart),
                    colors = TextFieldDefaults.textFieldColors(Color(0xFF2E3A59)),
                    value = uiState.inputText,
                    onValueChange = { onUpdateText(it) },
                    label = { Text("入力してね 🙌🏻") }
                )
            }
        }
    )
}

@Composable
fun MessageCard(message: ReceivedMessage) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFC0C8DC))
    ) {
        Text(
            text = "👤　${message.author}",
            modifier = Modifier.padding(4.dp)
        )
        Text(
            text = "✉️　${message.content}",
            modifier = Modifier.padding(4.dp)
        )
        Text(
            text = "🕰　${message.timestamp}",
            modifier = Modifier.padding(4.dp)
        )
    }
}


@Preview
@Composable
private fun ChatScreenPreview() {
    val uiState = ChatUiState(inputText = "Preview")
    JetMessengerTheme {
        ChatScreen(
            uiState = uiState,
            onUpdateText = {}
        ) {
        }
    }
}