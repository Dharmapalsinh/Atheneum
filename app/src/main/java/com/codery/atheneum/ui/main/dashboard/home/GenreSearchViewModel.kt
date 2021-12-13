package com.codery.atheneum.ui.main.dashboard.home

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.codery.atheneum.models.Genre
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GenreSearchViewModel(application : Application) : AndroidViewModel(application)  {
    init {
        addSearchGenresFromFireStore()
    }

    val genres = MutableLiveData<List<Genre>>(emptyList())

    private fun addSearchGenresFromFireStore(){
        val db = Firebase.firestore
        db.collection("Genres")
            .get()
            .addOnSuccessListener {
                val genList =it.documents.map{ document ->
                    val myGenre = document.get("genre") as String

                    val catGen = Genre(document.id,myGenre)
                    return@map catGen
                }
                genres.value = genList
            }
            .addOnFailureListener {
                Toast.makeText(getApplication(),"Genre Failed ${it}", Toast.LENGTH_SHORT).show()
            }
    }
}