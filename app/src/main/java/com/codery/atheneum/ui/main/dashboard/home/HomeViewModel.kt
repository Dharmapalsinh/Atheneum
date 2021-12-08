package com.codery.atheneum.ui.main.dashboard.home

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeViewModel(application : Application) : AndroidViewModel(application) {

    private val db = Firebase.firestore

    init {
        addTopGenresFromFireStore()
        addNewlyBooksFromFireStore()
    }

    val genres = MutableLiveData<List<CatalogueGenre>>(emptyList())
    val viewBooks = MutableLiveData<List<CatalogueNewly>>(emptyList())

    private fun addTopGenresFromFireStore(){
        db.collection("Genres")
            .get()
            .addOnSuccessListener {
                val genList = it.documents.map{ document ->
                    val myGenre = document.get("genre") as String

                    val catGen = CatalogueGenre(document.id,myGenre)
                    return@map catGen
                }
                genres.value = genList
                Log.d("genre log",genList.toString())
            }
            .addOnFailureListener {
                Toast.makeText(getApplication(),"Genre Failed ${it}",Toast.LENGTH_SHORT).show()
            }
    }

    private fun addNewlyBooksFromFireStore(){
        db.collection("Book-Entry")
            .get()
            .addOnSuccessListener {
                val bookList = it.documents.map{ document ->
                    val bookName = document.get("book_name") as String
                    val bookAuthor = document.get("book_author") as String

                    val book1 = CatalogueNewly(bookName,bookAuthor,"VIEW")
                    return@map book1
                }
                viewBooks.value = bookList
            }
            .addOnFailureListener {
                Toast.makeText(getApplication(), "BookList Failed ${it}", Toast.LENGTH_SHORT).show()
            }
    }
}