package com.codery.atheneum

import com.google.firebase.Timestamp
import com.manavtamboli.firefly.toFirebaseTimestamp
import com.manavtamboli.firefly.toLocalDate
import java.time.LocalDate

fun main(){
    val currentDate = LocalDate.now()

    // convert LocalDate to Timestamp
    val timestamp: Timestamp = currentDate.toFirebaseTimestamp()

    // convert Timestamp to LocalDate
    val date: LocalDate = timestamp.toLocalDate()
}