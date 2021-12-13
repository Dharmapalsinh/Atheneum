package com.codery.atheneum.ui.main.dashboard.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codery.atheneum.databinding.ItemGenreSearchBinding
import com.codery.atheneum.models.Genre

class GenreSearchAdapter(val onClick: (CatalogueGenre) -> Unit) : ListAdapter<CatalogueGenre,GenreSearchViewHolder>(DiffCatalogueGenre) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreSearchViewHolder {
        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val binding : ItemGenreSearchBinding = ItemGenreSearchBinding.inflate(inflater,parent,false)
        return GenreSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreSearchViewHolder, position: Int) {
        val searchItem : CatalogueGenre = getItem(position)
        holder.binding.txtSeachItem.setText(searchItem.catGenreName)
        holder.binding.txtSeachItem.setOnClickListener{
            onClick(searchItem)
        }
    }
}

class GenreSearchViewHolder(val binding : ItemGenreSearchBinding) : RecyclerView.ViewHolder(binding.root)