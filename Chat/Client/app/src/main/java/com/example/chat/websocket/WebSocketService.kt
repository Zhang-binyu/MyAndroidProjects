package com.example.chat.websocket

import com.example.chat.data.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class WebSocketService {
    // Define a value to record the connection status.
    private val _isConnected = MutableStateFlow(false)
    val isConnected = _isConnected.asStateFlow()

    // Define a value to record the message list.
    private val _messages = MutableStateFlow(emptyList<Message>())
    val messages = _messages.asStateFlow()

    // Define a value to record the sender name.
    private val _senderName = MutableStateFlow("")
    val senderName = _senderName.asStateFlow()

    // Define a value to instantiate the OkHttpClient object.
    private val okHttpClient = OkHttpClient()

    // Define a value to instantiate the WebSocket object.
    private var webSocket:WebSocket? = null

    // Define a variable to record the userName.
    private val _userName = MutableStateFlow("")
    var userName = _userName.asStateFlow()

    // Define a variable to record the server address.
    var serverAddress:String =""

    private val webSocketListener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            _isConnected.value = true
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            _senderName.value = getSenderName(text)
            _messages.update {
                val messageList = it.toMutableList()
                messageList.add(Message(message = getSenderMessageContent(text), isReceivedMessage = true))
                messageList
            }
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            _isConnected.value = false
            _messages.update {
                val messageList = it.toMutableList()
                messageList.clear()
                messageList
            }
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
            _isConnected.value = false
            _messages.update {
                val messageList = it.toMutableList()
                messageList.clear()
                messageList
            }
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            _isConnected.value = false
            _messages.update {
                val messageList = it.toMutableList()
                messageList.clear()
                messageList
            }
        }
    }

    private fun createRequest():Request{
        return Request.Builder().url("ws://$serverAddress").build()
    }

    fun connectToServer(){
        webSocket = okHttpClient.newWebSocket(createRequest(),webSocketListener)
    }

    fun sendMessage(message:String){
        if (_isConnected.value){
            webSocket?.send(text = message)
            _messages.update {
                val messageList = it.toMutableList()
                messageList.add(Message(getSenderMessageContent(message), isReceivedMessage = false))
                messageList
            }
        }
    }

    fun closeConnection(){
        webSocket?.close(1000,"Connection closed.")
    }

    fun shutDown(){
        okHttpClient.dispatcher.executorService.shutdown()
        _isConnected.value = false
    }

    // Define a function to get the sender name.
    fun getSenderName(input:String):String{
        val index = input.indexOf(":")
        return input.substring(0,index)
    }

    // Define a function to get the sender message content.
    fun getSenderMessageContent(input: String):String{
        val index = input.indexOf(":")
        return input.substring(index + 1)
    }

    // Define a function to set the user name.
    fun setUserName(newUserName:String){
        _userName.value = newUserName
    }
}