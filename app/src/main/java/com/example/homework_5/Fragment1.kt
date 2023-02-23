package com.example.homework_5

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.homework_5.databinding.Fragment1Binding


class Fragment1 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("test", "onCreate")
    }

    lateinit var binding: Fragment1Binding //add binding in Fragment1
    private val shareModel: DataShare by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("test", "onCreateView")

        binding = Fragment1Binding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("test", "onViewCreated")

        shareModel.shareForFragment1.observe(activity as LifecycleOwner, {
            binding.tvFragment1.text = it
        })


    }

    override fun onStart() {
        super.onStart()

        Log.d("test", "onStart")
    }

    override fun onResume() {
        super.onResume()

        Log.d("test", "onResume")
    }

    override fun onStop() {
        Log.d("test", "onStop")

        super.onStop()
    }

    override fun onDestroyView() {
        Log.d("test", "onDestroyView")

        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d("test", "onDestroy")

        super.onDestroy()
    }

    override fun onDetach() {
        Log.d("test", "onDetach")

        super.onDetach()
    }

    companion object {
        @JvmStatic
        fun newInstance() = Fragment1()
    }

}