package com.codery.atheneum.ui.main.dashboard.issued

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.codery.atheneum.ui.main.books.getIssueBook
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class IssueBookViewModel(application: Application) : AndroidViewModel(application) {
    val db = Firebase.firestore
    init {
        getIssuedBooksFromFireStore()
    }

    val getIssueBookList : MutableLiveData<List<getIssueBook>> = MutableLiveData()
    var Books = MutableLiveData<List<IssuedBookCls>>(emptyList())

    fun getAllBooks(){
        db.collection("Book-Entry")
            .get()
            .addOnSuccessListener {
                getIssueBookList.value = it.documents.map { document ->
                    val bookName = document.get("book_name") as String
                    val bookAuthor = document.get("book_author") as String

                    val getIssueBook = getIssueBook(document.id,bookName,bookAuthor)
                    return@map getIssueBook
                }
            }
            .addOnFailureListener {
                Toast.makeText(getApplication(), "Error get All Books", Toast.LENGTH_SHORT).show()
            }
    }

    fun getIssuedBooksFromFireStore(){
        db.collection("issued_books")
            .get()
            .addOnSuccessListener {
                val issuedBookList = it.documents.map{ document ->
                    val firstFormatter = DateTimeFormatter.ofPattern("dd MMM uuuu", Locale.ENGLISH)
                    val dateIssuedOn = document.get("date_book_issued_on") as String
                    val dateBookDueOn = document.get("date_book_due_on") as String
                    val dateSubmittedOn = document.get("date_book_submitted_on") as String
                    val issuedBookId = document.get("issued_book_name") as String

                    val issueOn : LocalDate= LocalDate.parse(dateIssuedOn,firstFormatter)
                    val dueOn : LocalDate= LocalDate.parse(dateBookDueOn,firstFormatter)
                    val submittedOn : LocalDate= LocalDate.parse(dateSubmittedOn,firstFormatter)

                    val finalId = getIssueBookList.value?.filter {
                        return@filter it.bookId in issuedBookId
                    }
                    val issueBookCls = IssuedBookCls(issuedBookId,issueOn,dueOn,submittedOn)
                    return@map issueBookCls
                }
            }
            .addOnFailureListener {
                Toast.makeText(getApplication(), "Error get issue Book ${it}", Toast.LENGTH_SHORT).show()
            }
    }
}