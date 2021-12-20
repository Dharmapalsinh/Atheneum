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


    init {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect {
                    binding.registerProgress.visibility=if (it is RegistrationState.Loading) View.VISIBLE else View.GONE
                    when(it){
                        is RegistrationState.Failed -> {
                            it.message
                        }
                        RegistrationState.Added ->{
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



    override fun FragmentRegisterBinding.initialize() {

        btnRegister.setOnClickListener {
            viewModel.adduser(txtName.text.toString(),txtAddress.text.toString(),txtPhone.text.toString())
            viewModel.state.value = RegistrationState.Loading
        }




    }

    sealed class RegistrationState {
        object Idle : RegistrationState()
        object Loading : RegistrationState()
        object Added : RegistrationState()
        data class Failed(val message : String) : RegistrationState()
    }


    class RegisterViewModel:ViewModel(){

        val state : MutableStateFlow<RegistrationState> = MutableStateFlow(RegistrationState.Idle)

        fun adduser(Name:String, Address:String, Phone:String){
            val signedInUser= Firebase.auth.currentUser
            if (signedInUser != null){
                val email = signedInUser.email

                // Create a new user with a first and last name
                val user = mapOf(
                        "name" to Name,
                        "address" to Address,
                        "phone" to Phone,
                        "email" to email
                )

                Firebase.firestore.collection("users").document(signedInUser.uid)
                    .set(user)
                    .addOnCompleteListener {
                        state.value = if (it.isSuccessful) RegistrationState.Added else RegistrationState.Failed("Cannot register.")
                    }
            }
        }

    }
}


