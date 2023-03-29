package com.example.homework_5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.homework_5.databinding.FragmentMessageBinding


class MessageFragment : Fragment() {

    lateinit var binding: FragmentMessageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner)
//            // здесь можно выполнить какие-то действия, например, закрыть фрагмент
//        requireActivity().supportFragmentManager.popBackStack()
//        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MessageFragment()
    }

//    override fun onBackPressed() {
//        requireActivity().onBackPressed()
//    }

}