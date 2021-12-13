package com.codery.atheneum.ui.main.books

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.codery.atheneum.data.Bindings
import com.codery.atheneum.models.Book
import com.codery.atheneum.models.Genre
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BookViewerViewModel(application : Application) : AndroidViewModel(application)  {

    init {
        addBookDetailsFromFireStore()
    }

    val book_info = MutableLiveData<List<Book>>(emptyList())

    fun addBookDetailsFromFireStore() {
        val db = Firebase.firestore

        db.collection("Book-Entry")
            .get()
            .addOnSuccessListener {
                val booksList = it.documents.map{ document ->
                val book_author = document.get("book_author") as String
                val book_name = document.get("book_name") as String
                val book_desc = document.get("book_desc") as String
                val book_length = document.get("book_length") as String
                val book_publisher = document.get("book_publisher") as String
                val book_genre = document.get("book_genre") as List<String>

                val listOfGenre = book_genre.map {

                }

//                val book1 = Book(document.id,book_name,book_author,"img",book_desc,book_genre ,book_length.toInt(),book_publisher,true)
//                return@map book1
                }
            }
            .addOnFailureListener {
                Toast.makeText(getApplication(), "Book-Details Fetching Error", Toast.LENGTH_SHORT).show()
            }
    }
}