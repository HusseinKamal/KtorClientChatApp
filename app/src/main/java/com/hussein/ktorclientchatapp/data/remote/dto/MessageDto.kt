package com.hussein.ktorclientchatapp.data.remote.dto

import com.hussein.ktorclientchatapp.domain.model.Message
import kotlinx.serialization.Serializable
import java.text.DateFormat
import java.util.Date

//Here should be same names typically in server app
//Server App Message-->
// @Serializable
//data class Message(
//    val text:String,
//    val username:String,
//    val timeStamp : Long,
//    @BsonId
//    val id:String = ObjectId().toString()
//)
@Serializable
data class MessageDto(
    val text:String,
    val timeStamp:Long,
    val username:String,
    val id:String,
    ){
    fun toMessage():Message{
        val date = Date(timeStamp)
        val formattedDate  = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(date)
        return Message(
            text= text,
            formattedTime = formattedDate,
            username = username
        )
    }
}