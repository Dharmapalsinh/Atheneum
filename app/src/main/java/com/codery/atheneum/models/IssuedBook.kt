package com.codery.atheneum.models

import androidx.recyclerview.widget.DiffUtil
import java.time.LocalDate


sealed class IssuedBook {

    abstract val issueId : String
    abstract val bookId : String
    abstract val issuedOn : LocalDate
    abstract val dueOn : LocalDate
    abstract val submittedOn : LocalDate?

    data class Submitted(
        override val issueId: String,
        override val bookId: String,
        override val issuedOn: LocalDate,
        override val dueOn: LocalDate,
        override val submittedOn: LocalDate
    ) : IssuedBook()

    data class Pending(
        override val issueId: String,
        override val bookId: String,
        override val issuedOn: LocalDate,
        override val dueOn: LocalDate
    ) : IssuedBook(){
        override val submittedOn: LocalDate? = null
    }

    data class Overdue(
        override val issueId: String,
        override val bookId: String,
        override val issuedOn: LocalDate,
        override val dueOn: LocalDate,
        override val submittedOn: LocalDate?,
        val penalty : Int
    ) : IssuedBook()
}

object DiffIssuedBook : DiffUtil.ItemCallback<IssuedBook>(){
    override fun areItemsTheSame(oldItem: IssuedBook, newItem: IssuedBook): Boolean {
        return oldItem.bookId == newItem.bookId
    }

    override fun areContentsTheSame(oldItem: IssuedBook, newItem: IssuedBook): Boolean {
        return oldItem == newItem
    }
}
