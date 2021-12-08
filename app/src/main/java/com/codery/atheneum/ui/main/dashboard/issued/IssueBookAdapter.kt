package com.codery.atheneum.ui.main.dashboard.issued

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codery.atheneum.databinding.ItemIssueBookBinding
import com.codery.atheneum.models.DiffIssuedBook
import com.codery.atheneum.models.IssuedBook

class IssueBookAdapter(val onClick : (IssuedBook)): ListAdapter<IssuedBook, IssueBookViewHolder>(DiffIssuedBook) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueBookViewHolder {
        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val binding : ItemIssueBookBinding = ItemIssueBookBinding.inflate(inflater,parent,false)
        return IssueBookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IssueBookViewHolder, position: Int) {
        val issuedBook : IssuedBook = getItem(position)
        holder.binding.issueBookName.setText(issuedBook.bookId)//
//        holder.binding.issueBookAuthor.setText(issuedBook.)
    }
}

class IssueBookViewHolder(val binding: ItemIssueBookBinding) : RecyclerView.ViewHolder(binding.root)