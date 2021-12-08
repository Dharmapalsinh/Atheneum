package com.codery.atheneum.ui.main.dashboard.issued

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class IssuedBookViewModel(application : Application) :  AndroidViewModel(application){
    val db = Firebase.firestore
    init{
        getIssuedBookFromFireStore()
    }

    val getIssueBookList : MutableLiveData<List<GetIssueBook>> = MutableLiveData()

    fun getIssuedBookFromFireStore(){
        db.collection("issued_books")
            .get()
            .addOnSuccessListener {
                getIssueBookList.value = it.documents.map{ document ->
                    val bookName = document.get("book_name") as String
                    val bookAuthor = document.get("book_author") as String

                    val getIssueBook = GetIssueBook(document.id,bookName, bookAuthor)
                    return@map getIssueBook
                }
            }
            .addOnFailureListener {
                Toast.makeText(getApplication(), "Failed Get All Books",Toast.LENGTH_SHORT).show()
            }
    }
}