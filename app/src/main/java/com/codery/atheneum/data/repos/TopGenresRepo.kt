package com.codery.atheneum.data.repos

import com.codery.atheneum.data.AtheneumDb
import com.codery.atheneum.data.queryTopGenres
import com.codery.atheneum.models.Genre
import com.google.firebase.firestore.FirebaseFirestoreException
import com.manavtamboli.firefly.firestore.single.fetch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlin.runCatching as catching

/**
 * Repository for Top Genres.
 * */
class TopGenresRepo(externalScope : CoroutineScope) {

    private val _exception = MutableSharedFlow<FirebaseFirestoreException>(extraBufferCapacity = 1)
    val exception = _exception.asSharedFlow()

    private val _genres = MutableSharedFlow<List<Genre>>(extraBufferCapacity = 1)
    val genres = _genres.asSharedFlow()

    init {
        externalScope.launch(Dispatchers.IO) {
            catching { AtheneumDb.queryTopGenres().fetch(Genre) }
                .onSuccess { _genres.tryEmit(it) }
                .onFailure { _exception.tryEmit(it as FirebaseFirestoreException) }
        }
    }
}