package com.codery.atheneum.models

import android.os.Parcelable
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