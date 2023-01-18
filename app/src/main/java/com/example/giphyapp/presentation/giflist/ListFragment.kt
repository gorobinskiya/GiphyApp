package com.example.giphyapp.presentation.giflist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.giphyapp.R
import com.example.giphyapp.core.hideKeyboard
import com.example.giphyapp.data.local.GiphyEntity
import com.example.giphyapp.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list), GiphyListAdapter.AdapterClickListener {
    private lateinit var binding: FragmentListBinding
    private val viewModel: GiftListViewModel by viewModels()
    val adapter = GiphyListAdapter()
    var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        adapter.adapterClickListener = this
        binding.gifsList.adapter = adapter
        collectFromViewModel()

        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                job?.cancel()
                job = MainScope().launch {
                    query?.let {
                        if (query.toString().isNotEmpty()) {
                            adapter.refresh()
                            requireActivity().hideKeyboard()
                            viewModel.launchSearch(query.toString())
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                job?.cancel()
                job = MainScope().launch {
                    delay(1500L)
                    newText?.let {
                        if (newText.toString().isNotEmpty()) {
                            adapter.refresh()
                            requireActivity().hideKeyboard()
                            viewModel.launchSearch(newText.toString())
                        }
                    }
                }
                return true
            }
        })


        return binding.root
    }

    private fun collectFromViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.gifs
                    .collectLatest {
                        adapter.submitData(it)
                    }
            }
        }
    }

    override fun onGifClicked(gif: GiphyEntity?) {

    }

    override fun onGifDeleted(gif: GiphyEntity?) {
        gif?.let { viewModel.blockGifById(it.id) }
    }

}