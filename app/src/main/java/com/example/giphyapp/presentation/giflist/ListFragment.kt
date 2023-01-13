package com.example.giphyapp.presentation.giflist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.giphyapp.R
import com.example.giphyapp.databinding.FragmentListBinding


class ListFragment : Fragment(R.layout.fragment_list) {
private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        binding.testText.setOnClickListener{
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_listFragment_to_detailedFragment)
        }

        return binding.root
    }


}