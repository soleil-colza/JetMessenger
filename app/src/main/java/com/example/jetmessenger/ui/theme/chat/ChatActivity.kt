package com.example.jetmessenger.ui.theme.chat

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jetmessenger.data.repository.ChatRepository
import com.example.jetmessenger.data.repository.ChatRepositoryImpl
import com.example.jetmessenger.ui.theme.JetMessengerTheme

class ChatActivity : ComponentActivity() {

    private val viewModel: ChatViewModel by viewModels {
        ViewModelFactory(ChatRepositoryImpl())
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetMessengerTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ChatScreen(viewModel = viewModel)
                }
            }
        }
    }

    inner class ViewModelFactory(
        private val repository: ChatRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>) : T {
            if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
                return ChatViewModel(repository) as T
            }
            throw IllegalArgumentException()
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatScreen(viewModel: ChatViewModel) {

    val inputText by viewModel.textState.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = { viewModel.sendMessage(inputText) } //　これがいらない？？
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
                value = inputText,
                onValueChange = { newText -> viewModel.updateText(newText) },
                label = { Text("Type whatever you like 🙌🏻") }
            )
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Preview
@Composable
fun PreviewChatScreen() {

    val repository = MockChatRepository()

    val viewModel = ChatViewModel(repository)

    JetMessengerTheme {
        ChatScreen(viewModel = viewModel)
    }
}

class MockChatRepository : ChatRepository {
    override suspend fun sendMessage(message: String) {
    }
}