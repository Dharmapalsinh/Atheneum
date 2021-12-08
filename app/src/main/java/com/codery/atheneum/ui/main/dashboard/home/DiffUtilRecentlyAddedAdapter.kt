package com.codery.atheneum.ui.main.dashboard.home

import android.media.browse.MediaBrowser
import androidx.recyclerview.widget.DiffUtil

data class  CatalogueNewly (val newlyBook : String,val newlyAuthor : String,val newlyView : String)

    object DiffCatalogueNewly : DiffUtil.ItemCallback<CatalogueNewly>(){
        override fun areItemsTheSame(oldItem: CatalogueNewly, newItem: CatalogueNewly): Boolean {
            return oldItem.newlyBook == newItem.newlyBook
        }

        override fun areContentsTheSame(oldItem: CatalogueNewly, newItem: CatalogueNewly): Boolean {
            return oldItem == newItem
        }
    }
