package com.example.chat.viewmodel

import androidx.lifecycle.ViewModel
import com.example.chat.websocket.WebSocketService

class MainViewModel : ViewModel() {
    val webSocketService = WebSocketService()

    val isConnected = webSocketService.isConnected
    val messageList = webSocketService.messages
    val senderName = webSocketService.senderName
    val userName = webSocketService.userName

    override fun onCleared() {
        super.onCleared()
        webSocketService.shutDown()
    }

    fun sendMessage(newMessage:String){
        webSocketService.sendMessage(newMessage)
    }

    fun connectToTheServer(){
        webSocketService.connectToServer()
    }

    fun closeConnection(){
        webSocketService.closeConnection()
    }

    fun setUserName(newUserName:String){
        webSocketService.setUserName(newUserName)
    }
}