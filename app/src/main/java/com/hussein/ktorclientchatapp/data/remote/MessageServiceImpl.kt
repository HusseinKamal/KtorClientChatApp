package com.hussein.ktorclientchatapp.data.remote

import com.hussein.ktorclientchatapp.data.remote.dto.MessageDto
import com.hussein.ktorclientchatapp.domain.model.Message
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class MessageServiceImpl(
    private val client: HttpClient
):MessageService {
    override suspend fun getAllMessages(): List<Message> {
        return try {
            client.get<List<MessageDto>>(MessageService.EndPoints.GetAllMessages.url).
                    map { it.toMessage() }
        }catch (e:Exception){
            e.printStackTrace()
            emptyList()
        }
    }
}