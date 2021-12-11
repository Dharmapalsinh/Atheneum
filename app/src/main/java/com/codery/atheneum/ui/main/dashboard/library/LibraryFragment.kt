package com.codery.atheneum.ui.main.dashboard.library

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codery.atheneum.databinding.FragmentLibraryBinding
import com.codery.atheneum.ui.main.dashboard.profile.ProfileFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.manavtamboli.axion.binding.BindingFragment
import com.manavtamboli.axion.extensions.log

class LibraryFragment : BindingFragment<FragmentLibraryBinding>(FragmentLibraryBinding::class.java){

    private val viewModel : LibraryViewModel by viewModels()

    override fun FragmentLibraryBinding.initialize() {
        // Initialization Logic
        viewModel.Details.observe(viewLifecycleOwner) {
            it ?: return@observe
            txtLibOpenTime.text = it._opentime
            txtLibCloseTime.text = it._closetime
            txtLibToday.text = it._Today
        }
    }


}

data class LibraryDetails(val _opentime:String?, val _closetime:String?, val _Today:String?)
class LibraryViewModel:ViewModel(){
    val Details: MutableLiveData<LibraryDetails?> = MutableLiveData(null)

    init {
        listenForRealtimeUpdates()
    }

    var reg : ListenerRegistration? = null

    private fun listenForRealtimeUpdates(){
        val db = Firebase.firestore
        val query = db.collection("library_update")

        reg = query.addSnapshotListener { snap, exception ->
            when {
                snap != null -> {
                    val opentime = snap.documents[0].getString("lib_open_time")
                    val closetime = snap.documents[0].getString("lib_close_time")
                    val today = snap.documents[0].getString("lib_today")
                    Details.value = LibraryDetails(opentime, closetime, today)
                }
                exception != null -> {
                    Details.value = null
                    log("TAGGED", exception)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        reg?.remove()
    }
}
