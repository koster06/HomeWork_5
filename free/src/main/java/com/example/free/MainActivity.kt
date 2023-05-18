package com.example.free

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.free.ui.theme.HomeWork_5Theme
import com.example.lib.UserLib
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    private val userService = createUserService()
    private val userRepository = UserRepositoryFree(userService)
    private val viewModelFactory = UserViewModelFactoryFree(userRepository)
    private val viewModel: UserViewModelFree by viewModels { viewModelFactory }
    private val analytics: FirebaseAnalytics = Firebase.analytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val serviceIntent = Intent(this, MyStartedService::class.java)
        startService(serviceIntent)

        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT) {
            param(FirebaseAnalytics.Param.ITEM_ID, "id_Item")
            param(FirebaseAnalytics.Param.ITEM_NAME, "user")
            param(FirebaseAnalytics.Param.CONTENT_TYPE, "object")
        }
        setContent {
            HomeWork_5Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserListScreen(viewModel)
                }
            }
        }
    }

    private fun createUserService(): UserServiceFree {
        return Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(UserServiceFree::class.java)
    }
}


@Composable
fun UserListScreen(viewModel: UserViewModelFree) {

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
                            showDialog.value = true
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
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "Limited Access") },
            text = { Text(text = "You are using the free version and cannot view user details.") },
            confirmButton = {
                Button(
                    onClick = { showDialog.value = false }
                ) {
                    Text(text = "OK")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserListScreenPreview() {
    val viewModel = UserViewModelFree(UserRepositoryFree(userService = Retrofit.Builder()
        .baseUrl("https://reqres.in/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(UserServiceFree::class.java)))
    UserListScreen(viewModel)
}

