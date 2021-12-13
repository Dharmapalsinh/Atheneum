package com.codery.atheneum.data

import androidx.lifecycle.MutableLiveData
import com.codery.atheneum.models.Genre
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GenreRepo {

    private val db = Firebase.firestore

    val genres = MutableLiveData<List<Genre>>(emptyList())

    init {
        fetchGenres()
    }

    private fun fetchGenres() {
        db.collection("Genres")
            .get()
            .addOnSuccessListener {
                // Create a list from documents
                val genList: List<Genre> = it.documents.map { document : DocumentSnapshot ->
                    val genreName = document.get("genre") as String
                    Genre(document.id, genreName)
                }
                // Update the list
                genres.value = genList
            }
            .addOnFailureListener {
                throw RuntimeException()
            }
    }
}
data class book(val author:String, val name:String, val desc:String, val length:String, val publisher:String,
                val genres:List<Genre>)

class BookRepo(private val genreRepo: GenreRepo) {

    private val db = Firebase.firestore
    val allBooks=MutableLiveData<List<book>>()
    val finalgenres=MutableLiveData<List<Genre>>()

    init {
        fetch()
    }

    private fun fetch(){
        db.collection("Book-Entry")
            .get()
            .addOnSuccessListener {
                val bookList = it.documents.map { document ->
                    val author = document.get("book_author") as String
                    val name = document.get("book_name") as String
                    val desc = document.get("book_desc") as String
                    val length = document.get("book_length") as String
                    val publisher = document.get("book_publisher") as String

                    // Book specific genre ids
                    val genreIds : List<String> = document.get("book_genre") as List<String>

                    // All genres
                    val allGenres: List<Genre> = genreRepo.genres.value ?: emptyList()

                    // Book specific genres
                    val finalGenres = allGenres.filter {
                        it.id in genreIds
                    }
                    book(author,name,desc,length,publisher,finalGenres)
                }

                allBooks.value= bookList
//                finalgenres.value=bookList

            }
            .addOnFailureListener {
                throw RuntimeException()
            }
    }
}