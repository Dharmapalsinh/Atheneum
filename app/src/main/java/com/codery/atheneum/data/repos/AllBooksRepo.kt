package com.codery.atheneum.data.repos

import com.codery.atheneum.data.Bindings
import com.codery.atheneum.models.Book
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.manavtamboli.firefly.firestore.realtime.realtimeDocuments
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

class AllBooksRepo(private val externalScope : CoroutineScope, genresRepo: AllGenresRepo) {

    private val db = Firebase.firestore

    val allBooks: StateFlow<List<Book>> = db.collection(Bindings.Collections.Book)
        .realtimeDocuments(Book.BookTransformer(genresRepo))
        .stateIn(externalScope, SharingStarted.Eagerly, emptyList())

    fun newlyAddedBooks() = allBooks.map {
        it.sortedByDescending { book ->
            book.addedOn
        }
    }.stateIn(externalScope, SharingStarted.Eagerly, emptyList())

    fun bookById(id : String): StateFlow<Book?> = allBooks.map {
        it.find { book ->
            book.id == id
        }
    }.stateIn(externalScope, SharingStarted.Eagerly, null)
}