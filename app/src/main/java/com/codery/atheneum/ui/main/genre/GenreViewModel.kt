package com.codery.atheneum.ui.main.genre

import androidx.lifecycle.ViewModel
import com.codery.atheneum.data.BookRepo
import com.codery.atheneum.data.GenreRepo

class DataViewModel : ViewModel() {
    private val genreRepo = GenreRepo()
    private val bookRepo = BookRepo(genreRepo)
    val books=bookRepo.Books
}