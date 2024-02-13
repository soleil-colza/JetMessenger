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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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

        val viewModel = ViewModelProvider(
            this,
            ChatViewModelFactory(
                SendMessageRepositoryImpl(Dispatchers.IO, sendMessageApi),
                GetMessagesRepositoryImpl(Dispatchers.IO, getMessagesApi)
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
                onClick = { onClickFabButton(textState) },
                containerColor = Color(0xFF2E3A59)
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = Color.White
                )
            }
        },

        ) { paddingValues ->

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
                items(messagesState.value) { message ->
                    MessageCard(message = message)
                }
            }

            TextField(
                modifier = Modifier
                    .padding(start = 20.dp, bottom = 16.dp)
                    .align(Alignment.BottomStart),
                colors = TextFieldDefaults.textFieldColors(Color(0xFF2E3A59)), //適用されてない気がする
                value = textState,
                onValueChange = { onUpdateText(it) },
                label = { Text("入力してね 🙌🏻") }
            )
        }
    }
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
            text = "👤　${message.author.username}",
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
    JetMessengerTheme {
        ChatScreen(
            textState = "Preview",
            onUpdateText = {},
            onClickFabButton = {},
            viewModel = ChatViewModel(
                SendMessageRepositoryImpl(Dispatchers.IO, sendMessageApi),
                GetMessagesRepositoryImpl(Dispatchers.IO, getMessagesApi)
            )
        )
    }
}
