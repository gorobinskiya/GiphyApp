package com.example.giphyapp.presentation.detailed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.giphyapp.data.local.GiphyEntity
import com.example.giphyapp.databinding.FragmentDetailedBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailedFragment : Fragment(), DetailedAdapter.DetailedClickListener {

    private lateinit var binding: FragmentDetailedBinding
    private val viewPagerGifAdapter = DetailedAdapter()
    private val args: DetailedFragmentArgs by navArgs()
    private val viewModel: DetailedViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailedBinding.inflate(inflater, container, false)
        viewPagerGifAdapter.detailedClickListener = this
        binding.viewPager.adapter = viewPagerGifAdapter
        viewModel.launchSearch(args.gif.searchQuery)
        collectFromViewModel()
        viewModel.chan.observe(viewLifecycleOwner) {
            binding.viewPager.setCurrentItem(args.position, false)
        }
        return binding.root
    }


    private fun collectFromViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.gifs
                    .collectLatest {
                        viewPagerGifAdapter.submitData(it)
                    }
            }
        }
    }

    override fun deleteGif(gif: GiphyEntity?) {
        gif?.let { viewModel.blockGifById(it.id) }
    }

}