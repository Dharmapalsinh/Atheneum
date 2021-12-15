package com.codery.atheneum.data.repos

import com.codery.atheneum.data.Bindings
import com.codery.atheneum.models.Genre
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.manavtamboli.firefly.firestore.realtime.realtimeDocuments
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take

class AllGenresRepo(private val externalScope : CoroutineScope) {

    val allGenres : StateFlow<List<Genre>> = Firebase.firestore.collection(Bindings.Collections.Genre)
        .realtimeDocuments(Genre)
        .stateIn(externalScope, SharingStarted.Eagerly, emptyList())

    fun genreById(id : String) = allGenres.value.find { it.id == id } ?: throw IllegalArgumentException("Illegal id")

    fun topGenres() = allGenres.take(10)
        .stateIn(externalScope, SharingStarted.Eagerly, emptyList())
}