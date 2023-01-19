package com.example.giphyapp.presentation.detailed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.giphyapp.data.local.GiphyEntity
import com.example.giphyapp.databinding.DetailedGifItemBinding


class DetailedAdapter : PagingDataAdapter<GiphyEntity, DetailedAdapter.GifViewHolder>(
    COMPARATOR
) {
    var detailedClickListener: DetailedClickListener? = null

    inner class GifViewHolder(private val binding: DetailedGifItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindGif(gif: GiphyEntity) {
            binding.apply {
                titleGif.text = gif.title
                Glide.with(this.root)
                    .load(gif.url)
                    .fitCenter()
                    .into(gifImage)
                deleteDetailed.setOnClickListener {
                    detailedClickListener?.deleteGif(gif)

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val binding =
            DetailedGifItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GifViewHolder(binding)
    }


    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        getItem(position)?.let { holder.bindGif(it) }
    }


    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<GiphyEntity>() {
            override fun areItemsTheSame(oldItem: GiphyEntity, newItem: GiphyEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GiphyEntity, newItem: GiphyEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
    interface DetailedClickListener {
        fun deleteGif (gif: GiphyEntity?)
    }

}