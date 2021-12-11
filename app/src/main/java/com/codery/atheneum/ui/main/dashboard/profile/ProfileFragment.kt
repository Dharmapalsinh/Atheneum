package com.codery.atheneum.ui.main.dashboard.profile

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codery.atheneum.databinding.FragmentProfileBinding
import com.codery.atheneum.ui.main.MainViewModel
import com.codery.atheneum.ui.main.dashboard.DashboardFragmentDirections
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.manavtamboli.axion.binding.BindingFragment
import com.manavtamboli.axion.extensions.log
import com.manavtamboli.firefly.firestore.Transformer
import com.manavtamboli.firefly.firestore.realtime.realtime
import com.manavtamboli.firefly.firestore.realtime.realtimeDocuments
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.stateIn

class ProfileFragment : BindingFragment<FragmentProfileBinding>(FragmentProfileBinding::class.java) {

    private val viewModel : ProfileViewModel by viewModels()

    private val mainViewModel:MainViewModel by activityViewModels()

    override fun FragmentProfileBinding.initialize() {

        viewModel.user.observe(viewLifecycleOwner) {
            it ?: return@observe
            txtPhoneNumber.text = it.phone
            txtProfileName.text = it.name
            txtAddress.text = it.address

        }
        imgEditAdd.setOnClickListener {
            mainViewModel.navigate(DashboardFragmentDirections.actionDashboardFragmentToEditProfileFragment())
        }

    }

    data class ProfileUser(val name: String?, val phone: String?, val address: String?)

    class ProfileViewModel : ViewModel() {

        val user: MutableLiveData<ProfileUser?> = MutableLiveData(null)

        init {
            listenForRealtimeUpdates()
        }

        var reg : ListenerRegistration? = null

        private fun listenForRealtimeUpdates(){
            val db = Firebase.firestore
            val email = Firebase.auth.currentUser?.email.toString()
            val query = db.collection("DGV").whereEqualTo("Email", email)

            reg = query.addSnapshotListener { snap, exception ->
                    when {
                        snap != null -> {
                            val add = snap.documents[0].getString("address")
                            val name = snap.documents[0].getString("Name")
                            val phone = snap.documents[0].getString("phone")
                            user.value = ProfileUser(name,phone,add)
                        }
                        exception != null -> {
                            user.value = null
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
}