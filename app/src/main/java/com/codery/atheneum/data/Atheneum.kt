package com.codery.atheneum.data

import com.codery.atheneum.app.RequiresAtheneum
import com.codery.atheneum.data.repos.AllGenresRepo
import kotlinx.coroutines.CoroutineScope

class Atheneum private constructor(val genreRepo : AllGenresRepo) {

    companion object {

        @Volatile
        private var instance : Atheneum? = null

        fun initialize(mainScope : CoroutineScope): Atheneum {
            return instance ?:
            synchronized(this) {
                val genreRepo = AllGenresRepo(mainScope)
                Atheneum(genreRepo)
            }
        }

        @RequiresAtheneum
        val GenreRepo get() = instance!!.genreRepo
    }
}