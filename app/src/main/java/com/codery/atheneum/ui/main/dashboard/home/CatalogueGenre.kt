package com.codery.atheneum.ui.main.dashboard.home

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class CatalogueGenre(val genreId : String,val catGenreName: String) : Parcelable

object DiffCatalogueGenre : DiffUtil.ItemCallback<CatalogueGenre>(){
    override fun areItemsTheSame(oldItem: CatalogueGenre, newItem: CatalogueGenre): Boolean {
        return oldItem.catGenreName == newItem.catGenreName
    }

    override fun areContentsTheSame(oldItem: CatalogueGenre, newItem: CatalogueGenre): Boolean {
        return oldItem == newItem
    }
}