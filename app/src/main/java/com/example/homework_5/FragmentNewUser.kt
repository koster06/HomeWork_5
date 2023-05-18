package com.example.homework_5

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.homework_5.databinding.FragmentNewUserBinding
import com.example.lib.UserRequestLib
import com.example.lib.UserServiceLib
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class FragmentNewUser: Fragment() {

    lateinit var binding: FragmentNewUserBinding
    private val userService by lazy {
        Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(UserServiceLib::class.java)
    }
    private var disposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNewUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.button.isEnabled = s.toString().isNotBlank() && binding.etJob.text.toString().isNotBlank()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not used
            }
        })

        binding.etJob.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.button.isEnabled = s.toString().isNotBlank() && binding.etName.text.toString().isNotBlank()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not used
            }
        })

        with(binding) {button.setOnClickListener {
            val userRequest = UserRequestLib(etName.text.toString(), etJob.text.toString())
            disposable = userService.createUser(userRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Toast.makeText(context, "User created successfully", Toast.LENGTH_SHORT)
                            .show()
                        requireActivity().onBackPressed()
                    },
                    { error ->
                        Log.e("test", "Error creating user", error)
                        Toast.makeText(context, "Error creating user", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable?.dispose()
    }

}