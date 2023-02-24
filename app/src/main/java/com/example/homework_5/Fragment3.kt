package com.example.homework_5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.homework_5.databinding.Fragment1Binding
import com.example.homework_5.databinding.Fragment3Binding

class Fragment3 : Fragment() {

    lateinit var binding: Fragment3Binding
    private val shareModelButton: DataShare by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Fragment3Binding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.bToFrag4.setOnClickListener {
            shareModelButton.shareForActivity.value = 4
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = Fragment3()
    }
}