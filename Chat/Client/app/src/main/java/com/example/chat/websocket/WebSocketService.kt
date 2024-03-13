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
    private val _isConnected = MutableStateFlow(false)
    val isConnected = _isConnected.asStateFlow()

    private val _messages = MutableStateFlow(emptyList<Message>())
    val messages = _messages.asStateFlow()

    private val okHttpClient = OkHttpClient()
    private var webSocket:WebSocket? = null

    var userName:String = ""
    var serverAddress:String =""

    private val webSocketListener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            webSocket.send("$userName device detected.")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            _messages.update {
                val messageList = it.toMutableList()
                messageList.add(Message(message = text, isReceivedMessage = true))
                messageList
            }
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            _isConnected.value = false
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            _isConnected.value = false
        }
    }

    private fun createRequest():Request{
        return Request.Builder().url("ws://$serverAddress").build()
    }

    fun connectToServer(){
        try {
            webSocket = okHttpClient.newWebSocket(createRequest(),webSocketListener)
            _isConnected.value = true
        }catch (e:Exception){
            _isConnected.value = false
        }
    }

    fun sendMessage(message:String){
        if (_isConnected.value){
            webSocket?.send(text = message)
            _messages.update {
                val messageList = it.toMutableList()
                messageList.add(Message(message, isReceivedMessage = false))
                messageList
            }
        }
    }

    fun closeConnection(){
        webSocket?.close(1000,"Connection closed.")
        _isConnected.value = false
    }

    fun shutDown(){
        okHttpClient.dispatcher.executorService.shutdown()
    }
}