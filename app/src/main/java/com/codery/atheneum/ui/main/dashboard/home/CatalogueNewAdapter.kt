package com.codery.atheneum.ui.main.dashboard.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codery.atheneum.databinding.ItemNewlyCatalogueBinding

class CatalogueNewAdapter(val onClick : (NewCatalogue) -> Unit) : androidx.recyclerview.widget.ListAdapter<NewCatalogue,CatalogueNewViewHolder>(DiffCatalogueNewly){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogueNewViewHolder {
        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val binding : ItemNewlyCatalogueBinding = ItemNewlyCatalogueBinding.inflate(inflater,parent,false)
        return CatalogueNewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatalogueNewViewHolder, position: Int) {
        val newlyCatalogue : NewCatalogue = getItem(position)

    }

}
class CatalogueNewViewHolder(val binding: ItemNewlyCatalogueBinding) : RecyclerView.ViewHolder(binding.root)