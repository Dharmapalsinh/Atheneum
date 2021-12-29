package com.codery.atheneum.models

import android.os.Parcelable
import com.codery.atheneum.R
import com.google.firebase.Timestamp
import com.manavtamboli.firefly.toLocalDate
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

sealed class Availability : Parcelable {

    @Parcelize
    object Available : Availability()

    @Parcelize
    data class Occupied(val est : LocalDate) : Availability()

    @Parcelize
    object Unavailable : Availability()

    fun visibility() = this !is Available
    fun title() = when(this) {
        Available -> "Available"
        is Occupied -> "Unavailable"
        Unavailable -> "Unavailable"
    }

    fun color() = when(this) {
        Available -> R.color.green
        is Occupied -> R.color.red
        Unavailable -> R.color.red
    }

    companion object {
        fun getAvailability(isAvailable : Boolean, est : Timestamp?): Availability {
            return if (isAvailable) Available
            else {
                if (est != null) Occupied(est.toLocalDate())
                else Unavailable
            }
        }
    }
}