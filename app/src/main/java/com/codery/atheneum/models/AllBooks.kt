package com.codery.atheneum.models

import androidx.recyclerview.widget.DiffUtil

data class AllBooks(val BookName:String,val Author:String) {
}
object Bookdiff: DiffUtil.ItemCallback<AllBooks>(){
    override fun areItemsTheSame(oldItem: AllBooks, newItem: AllBooks): Boolean {
        return oldItem.BookName==newItem.BookName
    }

    override fun areContentsTheSame(oldItem: AllBooks, newItem: AllBooks): Boolean {
        return oldItem==newItem
    }

}