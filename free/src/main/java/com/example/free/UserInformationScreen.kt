package com.example.free

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.lib.UserLib

@Composable
fun UserInformationScreen(userId: String?, navController: NavHostController) {

    Log.d("test", userId.toString())
    val userState = remember { mutableStateOf<UserLib?>(null) }

    LaunchedEffect(userId) {
        val userService = createUserService()
        try {
            val response = userService.getUser(userId?.toIntOrNull() ?: 0)
            if (response.isSuccessful) {
                userState.value = response.body()
                Log.d("test", userState.value.toString())
            } else {
                //
            }
        } catch (e: Exception) {
            //
        }
    }

    val user = userState.value

    if (user != null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = rememberImagePainter(data = user.avatar),
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "ID: ${user.id}",
                fontSize = 20.sp
            )
            Text(
                text = "Email: ${user.email}",
                fontSize = 20.sp
            )
            Text(
                text = "First Name: ${user.first_name}",
                fontSize = 20.sp
            )
            Text(
                text = "Last Name: ${user.last_name}",
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {
                    navController.navigate("screen_1")
                }
            ) {
                Text(text = "Home")
            }
        }
    } else {
        //
    }
}