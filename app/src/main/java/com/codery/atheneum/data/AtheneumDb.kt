package com.codery.atheneum.data

import com.codery.atheneum.data.Bindings.Book
import com.codery.atheneum.data.Bindings.Collections
import com.codery.atheneum.data.Bindings.Genre
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.manavtamboli.firefly.toFirebaseTimestamp
import java.time.LocalDate

class AtheneumDb {

    private val db = Firebase.firestore

    private val genreCollection get() = db.collection(Collections.Genre)
    private val bookCollection get() = db.collection(Collections.Book)

    fun topGenres() = genreCollection.whereEqualTo(Genre.top, true).limit(12)
    fun allGenres() = genreCollection
    fun genreById(id : String) = genreCollection.document(id)

    // Books that are added in last 7 days
    fun newlyAdded() = bookCollection.whereGreaterThan(Book.addedOn, LocalDate.now().minusDays(7).toFirebaseTimestamp())
        .orderBy(Book.addedOn)

    companion object
}

fun AtheneumDb.Companion.queryTopGenres() = AtheneumDb().topGenres()
fun AtheneumDb.Companion.queryAllGenres() = AtheneumDb().allGenres()
fun AtheneumDb.Companion.genreById(id : String) = AtheneumDb().genreById(id)

fun AtheneumDb.Companion.queryNewlyAdded() = AtheneumDb().newlyAdded()