package com.codery.atheneum.ui.main.books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codery.atheneum.databinding.ItemGenreBinding
import com.codery.atheneum.models.Genre

class GenreAdapter(private val onclick : (Genre) -> Unit) : ListAdapter<Genre,AvailBookViewHolder>(Genre.Companion.Diff){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvailBookViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemGenreBinding = ItemGenreBinding.inflate(inflater,parent,false)
        return AvailBookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AvailBookViewHolder, position: Int) {
        val genre : Genre = getItem(position)
        holder.binding.itemGenreName.text = genre.name
        holder.binding.root.setOnClickListener {
            onclick(genre)
        }
    }
}

class AvailBookViewHolder(val binding: ItemGenreBinding): RecyclerView.ViewHolder(binding.root)