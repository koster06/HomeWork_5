package com.example.free

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.free.ui.theme.HomeWork_5Theme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import coil.compose.rememberImagePainter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    private val userService = createUserService()
    private val userRepository = UserRepositoryFree(userService)
    private val viewModelFactory = UserViewModelFactoryFree(userRepository)
    private val viewModel: UserViewModelFree by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    val users: List<UserFree> by viewModel.users.observeAsState(emptyList())

    if (users.isNotEmpty()) {
        LazyColumn {
            items(users.size) { index ->
                val user = users[index]
                Row(
                    modifier = Modifier
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

