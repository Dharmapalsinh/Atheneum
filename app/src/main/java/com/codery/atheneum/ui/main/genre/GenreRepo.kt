package com.codery.atheneum.ui.main.genre

import androidx.lifecycle.MutableLiveData
import com.codery.atheneum.models.Genre
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GenreRepo  {
    private val db = Firebase.firestore

    val genres = MutableLiveData<List<Genre>>(emptyList())

    init {
        fetchGenre()
    }
    private fun fetchGenre(){
        db.collection("Genres").get()
            .addOnSuccessListener {
                val genList : List<Genre>
            }
            .addOnFailureListener {

            }
    }
}