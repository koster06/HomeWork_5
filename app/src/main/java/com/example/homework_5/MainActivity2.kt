package com.example.homework_5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.homework_5.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    lateinit var binding: ActivityMain2Binding
    //private var userIntent: ActivityResultLauncher<Intent>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
//        userIntent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//            if (it.resultCode == RESULT_OK) {
//                it.data?.getSerializableExtra("user") as User
//            }
//        }

        val user = this.getIntent().getSerializableExtra("user") as User
            with(binding){
                textName.text = user.name
                textName2.text= user.secName
                textPhone.text = user.phone
                textAge.text= user.age
                textBirthday.text = user.birthday
                imageView.setImageResource(user.id)
            }
    }


}