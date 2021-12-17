package com.codery.atheneum.models

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.codery.atheneum.app.InvalidDocument
import com.codery.atheneum.data.Bindings
import com.codery.atheneum.data.repos.AllGenresRepo
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.manavtamboli.firefly.firestore.Transformer
import com.manavtamboli.firefly.toLocalDate
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Book(
    val id : String,
    val name : String,
    val author : String,
    val image : String,
    val desc : String,
    val genres : List<Genre>,
    val length : Int,
    val publisher : String,
    val addedOn : LocalDateTime,
    val availability: Availability
) : Parcelable {

    companion object : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }

    class BookTransformer(private val genreRepo: AllGenresRepo) : Transformer<Book> {

        @Suppress("Unchecked_Cast")
        override fun transform(snap: DocumentSnapshot): Book {
            val id = snap.id
            val name = snap.get(Bindings.Book.name) as? String ?: throw InvalidDocument("No Book Name")
            val author = snap.get(Bindings.Book.author) as? String ?: throw InvalidDocument("No Book Author")
            val image = snap.get(Bindings.Book.image) as? String ?: throw InvalidDocument("No Book Image")
            val desc = snap.get(Bindings.Book.desc) as? String ?: throw InvalidDocument("No Book Description")
            val length = snap.get(Bindings.Book.length) as? Long ?: throw InvalidDocument("No Book Length")
            val publisher = snap.get(Bindings.Book.publisher) as? String ?: throw InvalidDocument("No Book Publisher")
            val rawGenres = snap.get(Bindings.Book.genres) as? List<String> ?: throw InvalidDocument("No Book Genres")
            val genres = rawGenres.map { genreId -> genreRepo.genreById(genreId) }
            val available = snap.get(Bindings.Book.available) as? Boolean ?: throw InvalidDocument("No Book Availabilsnapy Status")
            val est = snap.get(Bindings.Book.est) as? Timestamp?
            val createdAt = snap.get(Bindings.Book.addedOn) as Timestamp
            val availability = Availability.getAvailability(available, est)
            return Book(id, name, author, image, desc, genres, length.toInt(), publisher, createdAt.toLocalDate().atStartOfDay(), availability)
        }
    }
}