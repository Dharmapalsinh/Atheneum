package com.codery.atheneum.ui.main.dashboard.home

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize


@Parcelize
data class NewCatalogue(val newlyBook: String, val newlyAuthor: String, val newlyView: String) : Parcelable

object DiffCatalogueNewly : DiffUtil.ItemCallback<NewCatalogue>(){
    override fun areItemsTheSame(oldItem: NewCatalogue, newItem: NewCatalogue): Boolean {
        return oldItem.newlyBook == newItem.newlyBook
    }

    override fun areContentsTheSame(oldItem: NewCatalogue, newItem: NewCatalogue): Boolean {
        return oldItem == newItem
    }
}