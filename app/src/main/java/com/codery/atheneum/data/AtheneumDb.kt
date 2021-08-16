package com.codery.atheneum.data

import com.codery.atheneum.data.Bindings.Collections
import com.codery.atheneum.data.Bindings.Genre
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AtheneumDb {

    private val db = Firebase.firestore

    private val genreCollection get() = db.collection(Collections.Genre)

    fun topGenres() = genreCollection.whereEqualTo(Genre.top, true).limit(12)
    fun allGenres() = genreCollection
    fun genreById(id : String) = genreCollection.document(id)

    companion object
}

fun AtheneumDb.Companion.queryTopGenres() = AtheneumDb().topGenres()
fun AtheneumDb.Companion.queryAllGenres() = AtheneumDb().allGenres()
fun AtheneumDb.Companion.genreById(id : String) = AtheneumDb().genreById(id)