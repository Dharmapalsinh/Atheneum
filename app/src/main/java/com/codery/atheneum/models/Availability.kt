package com.codery.atheneum.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

sealed class Availability : Parcelable {

    @Parcelize
    object Available : Availability()

    @Parcelize
    data class Occupied(val est : LocalDate) : Availability()

    @Parcelize
    object Unavailable : Availability()
}