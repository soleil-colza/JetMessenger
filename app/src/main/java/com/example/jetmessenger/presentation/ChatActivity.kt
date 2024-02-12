package com.example.jetmessenger.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import api
import com.example.jetmessenger.data.ReceivedMessage
import com.example.jetmessenger.data.repository.GetMessagesRepositoryImpl
import com.example.jetmessenger.data.repository.SendMessageRepositoryImpl
import com.example.jetmessenger.ui.theme.JetMessengerTheme
import kotlinx.coroutines.Dispatchers

class ChatActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(
            this,
            ChatViewModelFactory(
                SendMessageRepositoryImpl(Dispatchers.IO),
                GetMessagesRepositoryImpl(Dispatchers.IO, api)
            )
        ).get(ChatViewModel::class.java)

        setContent {
            JetMessengerTheme {
                val textState = viewModel.textState.collectAsStateWithLifecycle()

                ChatScreen(
                    textState = textState.value,
                    onUpdateText = viewModel::updateText,
                    onClickFabButton = viewModel::sendMessage,
                    viewModel = viewModel
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    textState: String,
    onUpdateText: (String) -> Unit,
    onClickFabButton: (String) -> Unit,
    viewModel: ChatViewModel
) {

    LaunchedEffect(Unit) {
        viewModel.fetchMessages()
    }

    val messagesState = viewModel.messages.collectAsState()

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
            LazyColumn {
                items(messagesState.value) { message ->
                    MessageCard(message = message)
                }
            }

            TextField(
                modifier = Modifier.padding(horizontal = 16.dp),
                value = textState,
                onValueChange = { onUpdateText(it) },
                label = { Text("Type whatever you like 🙌🏻") }
            )
        }
    }
}



@Composable
fun MessageCard(message: ReceivedMessage) {
    Column {
        Text(text = "Message ID: ${message.id}")
        Text(text = "Channel ID: ${message.channelId}")
        Text(text = "Author: ${message.author.username}")
        Text(text = "Content: ${message.content}")
    }
}

@Preview
@Composable
private fun ChatScreenPreview() {
    JetMessengerTheme {
        ChatScreen(
            textState = "Preview",
            onUpdateText = {},
            onClickFabButton = {},
            viewModel = ChatViewModel(
                SendMessageRepositoryImpl(Dispatchers.IO),
                GetMessagesRepositoryImpl(Dispatchers.IO, api)
            )
        )
    }
}
