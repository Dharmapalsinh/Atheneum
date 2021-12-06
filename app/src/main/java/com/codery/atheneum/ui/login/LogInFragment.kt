package com.codery.atheneum.ui.login

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.codery.atheneum.R
import com.codery.atheneum.databinding.FragmentLogInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.manavtamboli.axion.binding.BindingFragment


class LogInFragment : BindingFragment<FragmentLogInBinding>(FragmentLogInBinding::class.java) {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    companion object{
        private const val RC_SIGN_IN = 120
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception=task.exception
            if(task.isSuccessful){
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d(ContentValues.TAG, "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(ContentValues.TAG, "Google sign in failed", e)
                }

            }
            else{
                Log.w("SignInActivity",exception.toString())

            }

        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null){
            // user is signed in
            findNavController().navigate(R.id.action_logInFragment_to_dashboardFragment)
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    private fun signOut(){
        auth.signOut()
        googleSignInClient.signOut()
    }

    private fun firebaseAuthWithGoogle(idToken: String) {

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener {


                val user = it.additionalUserInfo?.isNewUser
                if (user == true) {
                    findNavController().navigate(R.id.action_logInFragment_to_registerFragment)
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "signInWithCredential:success")
                } else {
                    val checkemail = auth.currentUser?.email
                    val collectionReference = Firebase.firestore.collection("DGV")
                        .whereEqualTo("Email", auth.currentUser?.email)
                        .get()
                        .addOnSuccessListener {
                            if (it.documents.isEmpty()) {
                                findNavController().navigate(R.id.action_logInFragment_to_registerFragment)
                            } else {
                                findNavController().navigate(R.id.action_logInFragment_to_dashboardFragment)
                            }
                        }
                        .addOnFailureListener {

                        }


                }

            }
            .addOnFailureListener {
                Log.w("tagged", "Error ")
            }
    }


    override fun FragmentLogInBinding.initialize() {
        // Initialize Firebase Auth
        auth = Firebase.auth

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)

        btnSignIn.setOnClickListener {
            signIn()
        }

//        binding.btnSignOut.setOnClickListener {
//
//            signOut()
//            Toast.makeText(context, "You're Signed out Successfully", Toast.LENGTH_SHORT).show()
//        }

    }






}