package com.codery.atheneum.ui.main.dashboard.profile

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import coil.load
import com.codery.atheneum.databinding.FragmentProfileBinding
import com.codery.atheneum.ui.main.MainViewModel
import com.codery.atheneum.ui.main.dashboard.DashboardFragmentDirections
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.manavtamboli.axion.binding.BindingFragment
import com.manavtamboli.axion.ui.toast

class ProfileFragment : BindingFragment<FragmentProfileBinding>(FragmentProfileBinding::class.java) {

    //TODO:Add LogOut button
    private val viewModel : ProfileViewModel by viewModels()

    private val mainViewModel:MainViewModel by activityViewModels()

    override fun FragmentProfileBinding.initialize() {
        profileImage.load(Firebase.auth.currentUser?.photoUrl)
        viewModel.user.observe(viewLifecycleOwner) {
            it ?: return@observe
            txtPhoneNumber.text = it.phone
            txtProfileName.text = it.name
            txtAddress.text = it.address
        }
        viewModel.state.observe(viewLifecycleOwner){
            it ?:return@observe
            loginProgress.visibility=if (it is State.Loading) View.VISIBLE else View.GONE
            when(it){
                is State.Failed-> {
                    toast(it.message)
                }
                else -> {}
            }
        }

        imgEditAdd.setOnClickListener {
            mainViewModel.navigate(DashboardFragmentDirections.actionDashboardFragmentToEditProfileFragment())
        }

    }

    data class ProfileUser(val name: String?, val phone: String?, val address: String?)

    sealed class State {
        object Idle : State()
        object Loading : State()
        object Successed:State()
        data class Failed(val message : String) : State()
    }

    class ProfileViewModel : ViewModel() {

        val user: MutableLiveData<ProfileUser?> = MutableLiveData(null)
        val state : MutableLiveData<State> = MutableLiveData(State.Idle)

        init {
            listenForRealtimeUpdates()
        }

        private var reg : ListenerRegistration? = null

        private fun listenForRealtimeUpdates(){
            val db = Firebase.firestore
            val query = db.collection("users").document(Firebase.auth.currentUser!!.uid)

            reg = query.addSnapshotListener { snap, exception ->
                    when {
                        snap != null -> {
                            val add = snap.getString("address")
                            val name = snap.getString("name")
                            val phone = snap.getString("phone")
                            user.value = ProfileUser(name, phone, add)
                            state.value=State.Successed
                        }
                        exception != null -> {
                            state.value = State.Failed("exception")
                            user.value = null
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