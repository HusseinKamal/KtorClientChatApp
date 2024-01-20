package com.hussein.ktorclientchatapp.presentation.chat

import com.hussein.ktorclientchatapp.domain.model.Message

data class ChatState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false
)