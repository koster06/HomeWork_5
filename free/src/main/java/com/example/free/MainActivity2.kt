package com.example.free

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.free.ui.theme.HomeWork_5Theme
import com.example.free.useless.UserRepositoryFree
import com.example.free.useless.UserViewModelFactoryFree
import com.example.free.useless.UserViewModelFree
import com.example.lib.UserLib

class MainActivity2 : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            HomeWork_5Theme {
                NavHost(
                    navController = navController,
                    startDestination = "screen_1"
                ) {
                    composable("screen_1") {
                        Screen1 {
                            navController.navigate("screen_2")
                        }
                    }

                    composable("screen_2") {
                        NewUserScreen() {
                            navController.navigate("screen_1") {
                                popUpTo("screen_1") {
                                    inclusive = true
                                }

                            }
                        }
                    }
                    composable("screen_3/{userId}") { backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userId")
                        UserInformationScreen(userId) {
                            navController.popBackStack()
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun Screen1(
    onClick: () -> Unit
) {

    val userService = createUserService()
    val userRepository = UserRepositoryFree(userService)
    val viewModelFactory = UserViewModelFactoryFree(userRepository)
    val viewModel: UserViewModelFree = viewModel(factory = viewModelFactory)

    HomeWork_5Theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background

        ) {
            Screen(viewModel)
            MyScreenContent(onClick)
        }
    }
}
@Composable
fun Screen (viewModel: UserViewModelFree) {

    val users: List<UserLib> by viewModel.users.observeAsState(emptyList())
    val showDialog = remember { mutableStateOf(false) }
    val selectedUser = remember { mutableStateOf<UserLib?>(null) }

    if (users.isNotEmpty()) {
        LazyColumn {
            items(users.size) { index ->
                val user = users[index]
                Row(
                    modifier = Modifier
                        .clickable {
                            selectedUser.value = user
//                            showDialog.value = true
                        }
                        .padding(top = 18.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberImagePainter(data = user.avatar),
                        contentDescription = "User Avatar",
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .size(88.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    )
                    Spacer(modifier = Modifier.width(18.dp))
                    Text(
                        text = user.first_name + " " + user.last_name,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 18.dp)
                    )
                }
            }

        }

    }
//    if (showDialog.value) {
//        AlertDialog(
//            onDismissRequest = { showDialog.value = false },
//            title = { Text(text = "Limited Access") },
//            text = { Text(text = "You are using the free version and cannot view user details.") },
//            confirmButton = {
//                Button(
//                    onClick = { showDialog.value = false }
//                ) {
//                    Text(text = "OK")
//                }
//            }
//        )
//    }
}
@Composable
fun MyScreenContent(onClick: () -> Unit) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        FloatingActionButton(
            onClick = {
                onClick()
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )
        {
            Text(text = "Add")
        }
    }
}