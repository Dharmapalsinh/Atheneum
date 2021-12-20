package com.codery.atheneum.ui.main.dashboard.issued

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.load
import com.codery.atheneum.data.repos.AllBooksRepo
import com.codery.atheneum.databinding.FragmentIssuedBooksBinding
import com.codery.atheneum.databinding.ItemIssueBookBinding
import com.codery.atheneum.models.DiffIssuedBook
import com.codery.atheneum.models.IssuedBook
import com.codery.atheneum.ui.main.MainViewModel
import com.codery.atheneum.ui.main.dashboard.DashboardFragmentDirections
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.manavtamboli.axion.binding.AxionAdapter
import com.manavtamboli.axion.binding.BindingFragment
import com.manavtamboli.axion.lifecycle.AxionFactory
import com.manavtamboli.axion.lifecycle.flows
import com.manavtamboli.firefly.firestore.realtime.realtimeDocuments
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class IssuedBooksFragment : BindingFragment<FragmentIssuedBooksBinding>(FragmentIssuedBooksBinding::class.java){

    private val mainViewModel : MainViewModel by activityViewModels()
    private val viewModel : IssuedBooksViewModel by viewModels { AxionFactory<IssuedBooksViewModel, AllBooksRepo>(mainViewModel.booksRepo) }

    private val adapter by AxionAdapter(ItemIssueBookBinding::class.java, DiffIssuedBook){
        onBind {
            itemIssuedAuthor.text = it.book.author
            itemIssuedDue.text = it.dueOn.format(AtheneumDateTimeFormatter)
            itemIssuedImg.load(it.book.image)
            itemIssuedName.text = it.book.name
            itemIssuedStatus.text = it.title
            itemIssuedStatus.setBackgroundColor(requireContext().getColor(it.backgroundColor))
        }
        onItemClick {
            mainViewModel.navigate(DashboardFragmentDirections.viewIssuedBook(it))
        }
    }

    init {
        flows {
            collectFlow(viewModel.issuedBooks){
                adapter.submitList(it)
            }
        }
    }

    override fun FragmentIssuedBooksBinding.initialize() {
        issuedList.adapter = adapter
    }
}

val AtheneumDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy")

fun LocalDate.toFormattedDate() : String = format(AtheneumDateTimeFormatter)

class IssuedBooksViewModel(booksRepo: AllBooksRepo) : ViewModel(){

    private val db = Firebase.firestore
    private val uid = Firebase.auth.currentUser?.uid ?: throw IllegalArgumentException("No user found.")

    private val query = db.collection("issued")
        .whereEqualTo("userId", uid)

    val issuedBooks = query.realtimeDocuments(IssuedBook.IssuedBookTransformer(booksRepo))
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}