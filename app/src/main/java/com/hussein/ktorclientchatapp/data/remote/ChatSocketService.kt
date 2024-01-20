package com.hussein.ktorclientchatapp.data.remote

import com.hussein.ktorclientchatapp.domain.model.Message
import com.hussein.ktorclientchatapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChatSocketService {

    suspend fun initSession(
        username:String
    ):Resource<Unit>

    suspend fun sendMessage(message:String)

    fun observeMessage():Flow<Message>

    suspend fun closeSession()

    companion object{
        const val BASE_URL="ws://10.0.2.2:8080"//for test emulator else add ip address of device
    }

    sealed class EndPoints(
        val url:String
    ){
        object ChatSocket:EndPoints("$BASE_URL/chat-socket")
    }

}