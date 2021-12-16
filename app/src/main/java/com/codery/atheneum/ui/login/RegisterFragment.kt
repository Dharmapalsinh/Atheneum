package com.codery.atheneum.ui.login

import android.content.Intent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.codery.atheneum.R
import com.codery.atheneum.databinding.FragmentRegisterBinding
import com.codery.atheneum.ui.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.manavtamboli.axion.binding.BindingFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegisterFragment : BindingFragment<FragmentRegisterBinding>(FragmentRegisterBinding::class.java) {

    private val viewModel : RegisterViewModel by viewModels()
    private val auth: FirebaseAuth = Firebase.auth
    private val googleSignInClient: GoogleSignInClient by lazy { GoogleSignIn.getClient(requireContext(), gso) }
    private val gso by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    init {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect {
                    binding.registerProgress.visibility=if (it is RegistrationState.Loading) View.VISIBLE else View.GONE
                    when(it){
                        is RegistrationState.Failed ->{
                            it.message
                        }
                        RegistrationState.Added ->{
                            // navigate to main activity
                            val  intent= Intent(requireContext(), MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun signOut(){
        auth.signOut()
        googleSignInClient.signOut()
        findNavController().navigate(R.id.action_registerFragment_to_logInFragment)
    }

    override fun FragmentRegisterBinding.initialize() {

        btnRegister.setOnClickListener {
            viewModel.adduser(txtName.text.toString(),txtAddress.text.toString(),txtPhone.text.toString())
            viewModel.state.value=RegistrationState.Loading
        }

        btnSignOut.setOnClickListener {
            signOut()
        }


    }

    sealed class RegistrationState {
        object Idle : RegistrationState()
        object Loading : RegistrationState()
        object Added : RegistrationState()
//        object Unregistered : LoginState()
        data class Failed(val message : String) : RegistrationState()
    }


    class RegisterViewModel:ViewModel(){

        val state : MutableStateFlow<RegistrationState> = MutableStateFlow(RegistrationState.Idle)
        fun adduser(Name:String, Address:String, Phone:String){

            val signedInUser= Firebase.auth.currentUser
            if (signedInUser != null){
                val userId=signedInUser.uid
                val email=signedInUser.email!!

                val db = Firebase.firestore

                // Create a new user with a first and last name
                val user = hashMapOf(
                        "Name" to Name,
                        "address" to Address,
                        "phone" to Phone,
                        "Id" to userId,
                        "Email" to email
                )

                db.collection("DGV").document(Firebase.auth.currentUser!!.uid)
                    .set(user)
                    .addOnSuccessListener {
                        state.value=RegistrationState.Added
                    }
                    .addOnFailureListener { e ->
                        state.value=RegistrationState.Failed("Error adding document - $e")
                    }
            }
        }

    }


    }


