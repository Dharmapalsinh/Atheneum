package com.codery.atheneum.ui.main.books

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.codery.atheneum.models.AllBooks
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AllBookViewModel(application: Application) : AndroidViewModel(application) {

    val books= MutableLiveData<List<AllBooks>>(emptyList())
    init {
        fetchData()
    }


    private fun fetchData() {

        val db= Firebase.firestore
        db.collection("Book-Entry")
            .get()

            .addOnSuccessListener {
                val finallist = it.documents.map { doc ->

                    val BookName: String = doc.get("book_name") as String
                    val Author: String = doc.get("book_author") as String


                    val Book1 = AllBooks(BookName, Author)
                    return@map Book1

                }

                books.value=finallist

            }
            .addOnFailureListener { exception ->
                Toast.makeText(getApplication(),"BookList Failed", Toast.LENGTH_LONG).show()
            }
    }
}