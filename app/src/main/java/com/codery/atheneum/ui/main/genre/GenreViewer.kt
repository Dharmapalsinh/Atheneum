package com.codery.atheneum.ui.main.genre

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.codery.atheneum.ui.main.dashboard.home.CatalogueGenre
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreViewer(val genreBookName : String,val genreAuthor : String,val genreViewerGenres : List<CatalogueGenre>?) :Parcelable

object GenreViewerDiff : DiffUtil.ItemCallback<GenreViewer>() {
    override fun areContentsTheSame(oldItem: GenreViewer, newItem: GenreViewer): Boolean {
        return oldItem.genreBookName == newItem.genreBookName
    }

    override fun areItemsTheSame(oldItem: GenreViewer, newItem: GenreViewer): Boolean {
        return oldItem == newItem
    }
}