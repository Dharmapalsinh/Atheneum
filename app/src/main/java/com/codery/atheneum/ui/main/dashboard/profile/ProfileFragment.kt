package com.codery.atheneum.ui.main.dashboard.profile

import android.content.Intent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import coil.load
import coil.transform.CircleCropTransformation
import com.codery.atheneum.R
import com.codery.atheneum.databinding.FragmentProfileBinding
import com.codery.atheneum.ui.login.LoginActivity
import com.codery.atheneum.ui.main.MainActivity
import com.codery.atheneum.ui.main.MainViewModel
import com.codery.atheneum.ui.main.dashboard.DashboardFragmentDirections
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.manavtamboli.axion.binding.BindingFragment
import com.manavtamboli.axion.ui.toast

class ProfileFragment : BindingFragment<FragmentProfileBinding>(FragmentProfileBinding::class.java) {

    //TODO:Round Profile image
    private val auth: FirebaseAuth = Firebase.auth
    private val googleSignInClient: GoogleSignInClient by lazy { GoogleSignIn.getClient(requireContext(), gso) }
    private val gso by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    private fun signOut(){
        auth.signOut()
        googleSignInClient.signOut()
    }
    private val viewModel : ProfileViewModel by viewModels()

    private val mainViewModel:MainViewModel by activityViewModels()

    override fun FragmentProfileBinding.initialize() {
        profileImage.load(Firebase.auth.currentUser?.photoUrl){
            transformations(CircleCropTransformation())
        }
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

        btnEdit.setOnClickListener {
            mainViewModel.navigate(DashboardFragmentDirections.actionDashboardFragmentToEditProfileFragment())
        }

        btnLogout.setOnClickListener {
            signOut()
            val  intent= Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
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