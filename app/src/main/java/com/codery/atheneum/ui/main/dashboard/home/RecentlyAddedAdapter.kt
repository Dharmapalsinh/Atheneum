package com.codery.atheneum.ui.main.dashboard.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codery.atheneum.databinding.FragmentHomeBinding
import com.codery.atheneum.databinding.ItemNewlyCatalogueBinding

class RecentlyAddedAdapter(val onClick : (CatalogueNewly) -> Unit) : ListAdapter<CatalogueNewly, CatalogueViewHolder>(DiffCatalogueNewly){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogueViewHolder {
        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val binding : ItemNewlyCatalogueBinding = ItemNewlyCatalogueBinding.inflate(inflater,parent,false)
        return CatalogueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatalogueViewHolder, position: Int) {
        val newlyCatalogue : CatalogueNewly =getItem(position)
        holder.binding.txtNewlyBookName.setText(newlyCatalogue.newlyAuthor)
        holder.binding.txtNewlyAuthor.setText(newlyCatalogue.newlyAuthor)
        holder.binding.txtNewlyView.setText(newlyCatalogue.newlyView)

        holder.binding.txtNewlyView.setOnClickListener {
            onClick(newlyCatalogue)
        }
    }
}

class CatalogueViewHolder(val binding : ItemNewlyCatalogueBinding) : RecyclerView.ViewHolder(binding.root)

