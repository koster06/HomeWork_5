package com.example.free

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.*
import com.example.free.useless.UserServiceFree
import com.example.lib.UserRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewUserScreen(onClick: () -> Unit)  {

    val name = remember { mutableStateOf("") }
    val workplace = remember { mutableStateOf("") }
    val scaffoldState = rememberScaffoldState()


    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        backgroundColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TextField(
                value = name.value,
                onValueChange = { newName -> name.value = newName },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                label = { Text(text = stringResource(R.string.name)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                )
            )

            TextField(
                value = workplace.value,
                onValueChange = { newWorkplace -> workplace.value = newWorkplace },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                label = { Text(text = stringResource(R.string.job)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                )
            )

            Button(
                onClick = {
                    createUser(name.value, workplace.value)
                    CoroutineScope(Dispatchers.Main).launch {
                        scaffoldState.snackbarHostState.showSnackbar("User was created")
                    }
                          },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                enabled = true
            ) {
                Text(text = "Post")
            }
            Button(
                onClick = { onClick() }
            ) {
                Text(text = "Cancel")
            }
        }
    }
}
fun createUserService(): UserServiceFree {
    return Retrofit.Builder()
        .baseUrl("https://reqres.in/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(UserServiceFree::class.java)
}
@OptIn(DelicateCoroutinesApi::class)
fun createUser(name: String, job: String) {
    val userRequest = UserRequest(name = name, job = job)
    val userService = createUserService()
    GlobalScope.launch(Dispatchers.Main) {
        try {
            withContext(Dispatchers.IO) {
                userService.createUser(userRequest)
            }
        } catch (e: Exception) {
            // ...
        }
    }
}
