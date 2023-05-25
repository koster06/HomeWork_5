package com.example.free

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.free.ui.theme.HomeWork_5Theme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeWork_5Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                ) {
//                    Screen(viewModel,)
//                    MyScreenContent(onClick)
                }

            }
        }
    }

//    private fun createUserService(): UserServiceFree {
//        return Retrofit.Builder()
//            .baseUrl("https://reqres.in/api/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//            .build()
//            .create(UserServiceFree::class.java)
//    }



//    @Preview(showBackground = true)
//    @Composable
//    fun UserListScreenPreview() {
//        val viewModel = UserViewModelFree(
//            UserRepositoryFree(userService = Retrofit.Builder()
//            .baseUrl("https://reqres.in/api/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//            .build()
//            .create(UserServiceFree::class.java))
//        )
//        Screen(viewModel)
//    }

}







