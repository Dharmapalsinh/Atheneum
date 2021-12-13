package com.codery.atheneum.ui.main.genre

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codery.atheneum.models.Genre
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GenreViewerViewModel(application: Application,val genreId : String) : AndroidViewModel(application) {

    init {

    }

    val genreList : MutableLiveData<List<Genre>> = MutableLiveData()
    val genViewerBooks = MutableLiveData<List<GenreViewer>>(emptyList())

    val db = Firebase.firestore

    fun getGenresFromFireStore(){
        db.collection("Genres")
            .get()
            .addOnSuccessListener {

            }
            .addOnFailureListener {

            }
    }

    fun getGenreViewerBooksFromFireStore(){
        db.collection("Book-Entry").whereArrayContains("book_genre",genreId)
            .get()
            .addOnSuccessListener {
                val booksList = it.documents.map{ document ->
                    val bookName = document.get("book_name") as String
                    val bookAuthor = document.get("book_author") as String
                    val bookGenres = document.get("book_genre") as List<String>

                    val finalGenre = genreList.value?.filter {
                        return@filter it.id in bookGenres
                    }
                    val book1 = GenreViewer(bookName,bookAuthor,finalGenre)
                    return@map book1
                }
                genViewerBooks.value = booksList
            }
            .addOnFailureListener {
                Toast.makeText(getApplication(),"GenreViewer Books Failed ${it}",Toast.LENGTH_SHORT).show()
            }
    }
}

class GenreViewerViewModelFactory(val application: Application,val genreId : String) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GenreViewerViewModel(application,genreId) as T
    }
}