package com.codery.atheneum.models

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.codery.atheneum.app.InvalidDocument
import com.codery.atheneum.data.Bindings
import com.google.firebase.firestore.DocumentSnapshot
import com.manavtamboli.firefly.firestore.Transformer
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre(
    val id : String,
    val name : String
) : Parcelable {

    companion object : Transformer<Genre> {
        override fun transform(snap: DocumentSnapshot): Genre {
            val id = snap.id
            val name = snap.get(Bindings.Genre.name) as? String ?: throw InvalidDocument("No Genre Name")
            return Genre(id, name)
        }

        object Diff : DiffUtil.ItemCallback<Genre>(){
            override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem == newItem
            }
        }
    }
}