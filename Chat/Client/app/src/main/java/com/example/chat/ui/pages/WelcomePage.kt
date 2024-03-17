package com.example.chat.ui.pages

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chat.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomePage(
    navigationController: NavController,
    mainViewModel: MainViewModel
) {
    // Create a variable to deposit the server address.
    var inputWebSocketAddress by remember {
        mutableStateOf("110.41.54.8:8080/")
    }

    // Create a variable to deposit the user name.
    var userName by remember {
        mutableStateOf("Example")
    }

    // Instantiate a object to get the context
    val context = LocalContext.current

    // Define a value to record the connection status.
    val connectionStatus by mainViewModel.isConnected.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Welcome!", fontSize = 36.sp)
            })
        },
        floatingActionButton = {
            Card(
                modifier = Modifier
                    .clickable {
                        when{
                            inputWebSocketAddress.isEmpty() && userName.isEmpty() -> Toast.makeText(context,"The server address and the user name cannot be empty!",Toast.LENGTH_SHORT).show()
                            inputWebSocketAddress.isEmpty() -> Toast.makeText(context,"The server address cannot be empty!",Toast.LENGTH_SHORT).show()
                            userName.isEmpty() -> Toast.makeText(context,"The user name cannot be empty!",Toast.LENGTH_SHORT).show()
                            inputWebSocketAddress.isNotEmpty() && userName.isNotEmpty() -> {
                                mainViewModel.setUserName(userName)
                                mainViewModel.webSocketService.serverAddress = inputWebSocketAddress

                                mainViewModel.connectToTheServer()
                            }
                        }
                    }
            ) {
                Icon(
                    modifier = Modifier.padding(16.dp).size(32.dp),
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = null
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(modifier = Modifier.fillMaxWidth(0.8f), text = "Server address:", fontSize = 16.sp)
                Card(
                    modifier = Modifier.fillMaxWidth(0.8f)
                ){
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                    ) {
                        OutlinedTextField(
                            value = inputWebSocketAddress,
                            onValueChange = {inputWebSocketAddress = it}
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(modifier = Modifier.fillMaxWidth(0.8f), text = "What's your name?", fontSize = 16.sp)
                Card(
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = userName,
                            onValueChange = { userName = it }
                        )
                    }
                }
            }
        }
    }

    // Define a variable to record the accounts of operations returned.
    var userBackHandler = 0

    // Define a variable makes it easier for us to finish the activity.
    val activity = context as? Activity ?: return

    // Listen for the return key.
    BackHandler(enabled = true) {
        if (userBackHandler == 0){
            Toast.makeText(context,"Press again to exit.",Toast.LENGTH_SHORT).show()
            userBackHandler++

            Handler(Looper.getMainLooper()).postDelayed({
                userBackHandler = 0
            }, 2000)
        }else{
            activity.finish()
        }
    }

    // Handle the connection.
    LaunchedEffect(connectionStatus){
        if (connectionStatus){
            navigationController.navigate("LobbyScreen")
            Toast.makeText(context,"Successfully connected to the server!",Toast.LENGTH_SHORT).show()
        }
    }
}