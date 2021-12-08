package com.codery.atheneum.ui.main.books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codery.atheneum.databinding.ItemAllbooksBinding
import com.codery.atheneum.models.AllBooks
import com.codery.atheneum.models.Bookdiff

class AllBooksAdapter: ListAdapter<AllBooks, AllBookViewHolder>(Bookdiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllBookViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding=ItemAllbooksBinding.inflate(inflater,parent,false)
        return AllBookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllBookViewHolder, position: Int) {
        val Book1:AllBooks=getItem(position)
        holder.binding.txtBookname.text=Book1.BookName
        holder.binding.txtAuthor.text=Book1.Author

    }


}

class AllBookViewHolder(val binding: ItemAllbooksBinding): RecyclerView.ViewHolder(binding.root){

}