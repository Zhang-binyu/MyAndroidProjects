package com.example.chat.ui.pages

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chat.viewmodel.MainViewModel

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbyScreen(
    navigationController:NavHostController,
    mainViewModel: MainViewModel
) {
    // Define a variable to record the message.
    var editMessage by remember {
        mutableStateOf("")
    }

    // Define a value to record the context.
    val context = LocalContext.current

    // Define a value to record the message list.
    val messageList by mainViewModel.messageList.collectAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Lobby", fontSize = 24.sp)

                        IconButton(onClick = {
                            mainViewModel.closeConnection()
                            Toast.makeText(context,mainViewModel.webSocketService.userName + " has already left the lobby.",Toast.LENGTH_SHORT).show()
                            navigationController.navigate("WelcomePage"){
                                popUpTo("WelcomePage"){
                                    inclusive = true
                                }
                            }
                        }) {
                            Image(imageVector = Icons.Default.ExitToApp, contentDescription = null)
                        }
                    }
                }
            )
        },
        content = {paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .size(650.dp)
                        .padding(20.dp),
                    content = {
                        items(messageList){message ->
                            if (message.message.isNotEmpty()){
                                Column{
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = if (message.isReceivedMessage) Arrangement.Start else Arrangement.End
                                    ) {
                                        Text(text = if (message.isReceivedMessage) "Server" else mainViewModel.webSocketService.userName)
                                    }
                                    
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = if (message.isReceivedMessage) Arrangement.Start else Arrangement.End
                                    ) {
                                        Card(
                                            modifier = Modifier.padding(bottom = 12.dp),
                                            colors = if (message.isReceivedMessage){
                                                CardDefaults.cardColors(
                                                    containerColor = Color.Cyan
                                                )
                                            } else {
                                                CardDefaults.cardColors(
                                                    containerColor = Color.Green
                                                )
                                            }

                                        ) {
                                            Text(
                                                modifier = Modifier.padding(16.dp),
                                                text = message.message,
                                                fontSize = 18.sp
                                            )
                                        }
                                    }
                                }
                                
                                
                            }
                        }
                    }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = editMessage,
                        onValueChange = { editMessage = it },
                        placeholder = {
                            Text(text = "Write your message here,please.")
                        }
                    )
                    IconButton(
                        onClick = {
                            if (editMessage.isNotEmpty()) {
                                mainViewModel.sendMessage(editMessage)
                                editMessage = ""
                            }else{
                                Toast.makeText(context,"Message cannot be empty!",Toast.LENGTH_SHORT).show()
                            }
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = null)
                    }
                }
            }
        }
    )
}