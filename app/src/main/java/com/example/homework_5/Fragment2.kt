package com.example.homework_5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.homework_5.databinding.Fragment2Binding

class Fragment2 : Fragment() {

    lateinit var binding: Fragment2Binding
    private val shareModelString: DataShare by activityViewModels()
    private val shareModelButton: DataShare by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Fragment2Binding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.bCallFragment1.setOnClickListener {
            shareModelButton.shareForActivity.value = 3
        }
        binding.tvFrag2.text = shareModelString.shareForFragment2.value
    }

    companion object {
       @JvmStatic
        fun newInstance() = Fragment2()
    }
}