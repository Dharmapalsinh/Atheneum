package com.codery.atheneum.ui.login

import android.content.Intent
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.codery.atheneum.R
import com.codery.atheneum.databinding.FragmentLogInBinding
import com.codery.atheneum.ui.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.manavtamboli.axion.binding.BindingFragment
import com.manavtamboli.firefly.auth.GoogleSignInContract


class LogInFragment : BindingFragment<FragmentLogInBinding>(FragmentLogInBinding::class.java) {

    private val auth: FirebaseAuth = Firebase.auth
    val gso by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    private val googleSignInClient: GoogleSignInClient by lazy { GoogleSignIn.getClient(requireContext(), gso) }

    private val viewModel : LoginViewModel by viewModels()


    private val launcher = registerForActivityResult(GoogleSignInContract()){
        it.onSuccess { acc ->
            viewModel.firebaseAuthWithGoogle(acc.idToken ?: throw IllegalArgumentException("Id token cannot be null, but was null."))
        }
        .onFailure {
                Log.d("error","onfailure $it")
        }
    }


    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null){
            // user is signed in
//            findNavController().navigate(R.id.action_logInFragment_to_dashboardFragment)
        }
    }

    private fun signIn() {
        launcher.launch(googleSignInClient)
    }

    private fun signOut(){
        auth.signOut()
        googleSignInClient.signOut()
    }

    override fun FragmentLogInBinding.initialize() {
        btnSignIn.setOnClickListener {
            signIn()
        }
        btnSignOut.setOnClickListener {
            signOut()
        }
        viewModel.isRegister.observe(viewLifecycleOwner){ isRegistered ->
            isRegistered ?: return@observe
            if (isRegistered){
                Log.d("tagged","isregisterd true")
                val  intent=Intent(requireContext(),MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }

            else{
//                Log.d("tagged","false isregistered")
                findNavController().navigate(R.id.action_logInFragment_to_registerFragment)
            }

        }


    }
}

data class Email(val email:String)

class LoginViewModel : ViewModel(){

    val isRegister : MutableLiveData<Boolean> = MutableLiveData()
    private val auth = Firebase.auth
    lateinit var emailList:MutableLiveData<Email>

    // Idle
    fun firebaseAuthWithGoogle(idToken: String) {
        // Loading
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                val checkemail = auth.currentUser?.email
                val collectionReference = Firebase.firestore.collection("DGV")
                    .whereEqualTo("Email", auth.currentUser?.email)
                    .get()
                    .addOnSuccessListener {
                        if (it.documents.isEmpty()) {
                            // UnRegistered User
                            isRegister.value=false

                        } else {
                            // Registered User
                            isRegister.value = true
                        }

                    }
                    .addOnFailureListener {
                        // Failed
                    }
            }
            .addOnFailureListener {
                // Failed
            }
    }
}