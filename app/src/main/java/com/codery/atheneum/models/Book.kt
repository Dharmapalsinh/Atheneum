package com.codery.atheneum.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val id : String,
    val name : String,
    val author : String,
    val image : String,
    val desc : String,
    val genres : List<Genre>,
    val length : Int,
    val publisher : String,
    val availability: Availability
) : Parcelable