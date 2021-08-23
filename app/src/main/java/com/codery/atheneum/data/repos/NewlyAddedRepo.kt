package com.codery.atheneum.data.repos

import com.codery.atheneum.data.Atheneum
import com.codery.atheneum.data.AtheneumDb
import com.codery.atheneum.data.queryNewlyAdded
import com.codery.atheneum.models.Book
import com.manavtamboli.firefly.firestore.realtime.realtime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map

class NewlyAddedRepo(externalScope : CoroutineScope) {

    private val genreRepo = Atheneum.GenreRepo

    @Suppress("Unchecked_Cast")
    val books = AtheneumDb.queryNewlyAdded().realtime()
        .map { list -> list.map { Book.fromDocument(it, genreRepo) } }
}