package com.codery.atheneum.models

import androidx.annotation.ColorRes
import androidx.recyclerview.widget.DiffUtil
import com.codery.atheneum.R
import com.codery.atheneum.data.repos.AllBooksRepo
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.manavtamboli.firefly.firestore.Transformer
import com.manavtamboli.firefly.toLocalDate
import java.time.LocalDate


sealed class IssuedBook {

    abstract val issueId : String
    abstract val book : Book
    abstract val issuedOn : LocalDate
    abstract val dueOn : LocalDate
    abstract val submittedOn : LocalDate?

    abstract val title : String
    abstract val backgroundColor : Int

    data class Submitted(
        override val issueId: String,
        override val book : Book,
        override val issuedOn: LocalDate,
        override val dueOn: LocalDate,
        override val submittedOn: LocalDate
    ) : IssuedBook() {

        override val title = "Submitted"

        @ColorRes
        override val backgroundColor = R.color.green
    }

    data class Pending(
        override val issueId: String,
        override val book : Book,
        override val issuedOn: LocalDate,
        override val dueOn: LocalDate
    ) : IssuedBook(){

        override val submittedOn: LocalDate? = null

        override val title: String = "Pending"

        @ColorRes
        override val backgroundColor = R.color.orange
    }

    data class Overdue(
        override val issueId: String,
        override val book : Book,
        override val issuedOn: LocalDate,
        override val dueOn: LocalDate,
        val penalty : Int
    ) : IssuedBook(){

        override val submittedOn: LocalDate? = null

        override val title: String = "Overdue"

        @ColorRes
        override val backgroundColor = R.color.red
    }

    class IssuedBookTransformer(private val booksRepo: AllBooksRepo) : Transformer<IssuedBook> {

        override fun transform(snap: DocumentSnapshot): IssuedBook {
            val issueId = snap.id
            val bookId = snap.get("bookId") as String
            val issuedOn = (snap.get("issuedOn") as Timestamp).toLocalDate()
            val dueOn = (snap.get("dueOn") as Timestamp).toLocalDate()
            val submittedOn = (snap.get("submittedOn") as Timestamp?)?.toLocalDate()
            val penalty = (snap.get("penalty") as Long?) ?: 0L

            val book = booksRepo.allBooks.value.find { it.id == bookId } ?: throw IllegalArgumentException("Book not found.")

            return when (submittedOn) {
                null -> {
                    if (dueOn < LocalDate.now()) Overdue(issueId, book, issuedOn, dueOn, penalty.toInt())
                    else Pending(issueId, book, issuedOn, dueOn)
                }
                else -> Submitted(issueId, book, issuedOn, dueOn, submittedOn)
            }
        }
    }
}

object DiffIssuedBook : DiffUtil.ItemCallback<IssuedBook>(){
    override fun areItemsTheSame(oldItem: IssuedBook, newItem: IssuedBook): Boolean {
        return oldItem.issueId == newItem.issueId
    }

    override fun areContentsTheSame(oldItem: IssuedBook, newItem: IssuedBook): Boolean {
        return oldItem == newItem
    }
}
