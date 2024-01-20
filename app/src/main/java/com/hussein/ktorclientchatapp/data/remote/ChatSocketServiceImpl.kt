package com.hussein.ktorclientchatapp.data.remote

import com.hussein.ktorclientchatapp.data.remote.dto.MessageDto
import com.hussein.ktorclientchatapp.domain.model.Message
import com.hussein.ktorclientchatapp.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.WebSocketSession
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ChatSocketServiceImpl(
    private val client:HttpClient
) : ChatSocketService {

    private var sockect : WebSocketSession? = null
    override suspend fun initSession(username: String): Resource<Unit> {
        return try {
            sockect = client.webSocketSession{
                url(ChatSocketService.EndPoints.ChatSocket.url)
            }
            if(sockect?.isActive == true){
                Resource.Success(Unit)
            }
            else Resource.Error("Couldn't establish connection.")
        }
        catch (e:Exception){
            e.printStackTrace()
            Resource.Error(e.localizedMessage?:"Unknown Error")
        }
    }

    override suspend fun sendMessage(message: String) {
        try {
            sockect?.send(Frame.Text(message))
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun observeMessage(): Flow<Message> {
        return try {
            sockect?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text}
                ?.map {
                    val json = (it as? Frame.Text)?.readText() ?: ""
                    val messageDto = Json.decodeFromString<MessageDto>(json)
                    messageDto.toMessage()
                } ?: flow {  }
        }
        catch (e:Exception){
            e.printStackTrace()
            flow {  }
        }
    }

    override suspend fun closeSession() {
        sockect?.close()
    }
}