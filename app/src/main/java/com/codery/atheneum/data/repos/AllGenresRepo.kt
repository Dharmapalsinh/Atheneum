package com.codery.atheneum.data.repos

import com.codery.atheneum.app.DocumentNotFound
import com.codery.atheneum.data.AtheneumDb
import com.codery.atheneum.data.genreById
import com.codery.atheneum.data.queryAllGenres
import com.codery.atheneum.models.Genre
import com.manavtamboli.axion.core.Delicate
import com.manavtamboli.firefly.firestore.pagination.TypedPagination.Companion.paginateIn
import com.manavtamboli.firefly.firestore.single.fetch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class AllGenresRepo(externalScope : CoroutineScope) {

    private val source = AtheneumDb.queryAllGenres().paginateIn(externalScope, Genre)

    @Delicate("Must start collecting immediately, to avoid data loss.")
    private val _singleFetches = MutableSharedFlow<Genre>(extraBufferCapacity = 1)

    val genres = source.allDocuments.combine(_singleFetches){ all, s -> all.toSet() + s }
        .stateIn(externalScope, SharingStarted.Eagerly, setOf())

    fun fetch() = source.fetch(BATCH_COUNT)

    suspend fun genreById(id : String) : Genre {
        val existing = genres.value.find { it.id == id }
        return existing
            ?: (AtheneumDb.genreById(id).fetch(Genre) ?: throw DocumentNotFound("Invalid Genre Id"))
                .also { _singleFetches.tryEmit(it) }
    }

    companion object {
        private const val BATCH_COUNT = 15L
    }
}