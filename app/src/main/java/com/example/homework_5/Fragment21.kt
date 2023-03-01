package com.example.homework_5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.homework_5.databinding.FragmentFragment21Binding


class Fragment21 : Fragment() {

    private lateinit var communicator: Communicator
    lateinit var binding: FragmentFragment21Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFragment21Binding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        communicator = activity as Communicator

        // there are button and edittext id which we have saved
        val button: Button = binding.sendBtn
        val textMessage: EditText = binding.messageInput

        // pass data to our interface while
        // button clicked using setOnClickListener
        button.setOnClickListener {
            communicator.passData(textMessage.text.toString())
        }
    }
}