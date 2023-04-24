package com.example.homework_5

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.homework_5.databinding.FragmentUserDetailsBinding
import dataclasses.UserService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.disposables.Disposable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class FragmentUserDetails : Fragment() {

    private lateinit var binding: FragmentUserDetailsBinding
    private val userService by lazy {
        Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(UserService::class.java)
    }
    private lateinit var disposable: Disposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = arguments?.getInt("userId") ?: 0
        disposable = userService.getUser(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { user ->
                    Log.i("test", user.data.id.toString())
                    binding.textViewName.text = user.data.first_name
                    binding.textViewEmail.text = user.data.email
                    Glide.with(this)
                        .load(user.data.avatar)
                        .circleCrop()
                        .into(binding.imageView2)
                },
                { error ->
                    Log.e("UserFragment", "Error fetching user data", error)
                    Toast.makeText(context, "Error fetching user data", Toast.LENGTH_SHORT).show()
                }
            )



    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.dispose()
    }
}
