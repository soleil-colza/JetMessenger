package com.example.jetmessenger.ui.theme.chat

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetmessenger.data.repository.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repository: ChatRepository
) : ViewModel() {

    private val _textState = MutableStateFlow("")
    val textState = _textState.asStateFlow()

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun updateText(newText: String) {
        _textState.value = newText
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun sendMessage(newText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.sendMessage(newText)
            updateText("")
        }
    }
}
