package com.example.homework_5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.homework_5.databinding.FragmentFragment22Binding

class Fragment22 : Fragment() {

    var displayMessage:String? =""
    lateinit var binding: FragmentFragment22Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFragment22Binding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        displayMessage = arguments?.getString("message")
        binding.displayMessage.text = displayMessage
    }
}