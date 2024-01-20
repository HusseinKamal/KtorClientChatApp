package com.hussein.ktorclientchatapp.data.remote

import com.hussein.ktorclientchatapp.domain.model.Message

interface MessageService {

    suspend fun getAllMessages():List<Message>
    companion object{
        const val BASE_URL="http://10.0.2.2:8080"//for test emulator else add ip address of device
    }

    sealed class EndPoints(
        val url:String
    ){
        object GetAllMessages:EndPoints("$BASE_URL/messages")
    }
}