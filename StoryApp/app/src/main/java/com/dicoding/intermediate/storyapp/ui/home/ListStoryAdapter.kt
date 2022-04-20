package com.dicoding.intermediate.storyapp.ui.home

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.intermediate.storyapp.R
import com.dicoding.intermediate.storyapp.databinding.ItemRowStoryBinding
import com.dicoding.intermediate.storyapp.service.response.ListStoryItem
import com.dicoding.intermediate.storyapp.ui.detail.DetailActivity
import com.dicoding.intermediate.storyapp.ui.detail.DetailActivity.Companion.EXTRA_DATA

class ListStoryAdapter(private val listStories: List<ListStoryItem>) :
    RecyclerView.Adapter<ListStoryAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val binding: ItemRowStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListStoryItem) {
            binding.apply {
                tvTitle.text = story.name
                Glide.with(itemView.context)
                    .load(story.photo)
                    .fitCenter()
                    .apply(
                        RequestOptions
                            .placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    ).into(ivStory)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(EXTRA_DATA, story)

                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(ivStory, "story"),
                            Pair(tvTitle, "name"),
                            Pair(tvDescItem, "desc")
                        )
                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRowStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listStories[position])
    }

    override fun getItemCount(): Int = listStories.size
}