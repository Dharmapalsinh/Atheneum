package com.codery.atheneum.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.codery.atheneum.R
import com.codery.atheneum.databinding.FragmentRegisterBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.manavtamboli.axion.binding.BindingFragment

class RegisterFragment : BindingFragment<FragmentRegisterBinding>(FragmentRegisterBinding::class.java) {

    override fun onStart() {
        super.onStart()
    }

    override fun FragmentRegisterBinding.initialize() {

        btnRegister.setOnClickListener {
            val signedInUser= Firebase.auth.currentUser
            if (signedInUser != null){
                val userId=signedInUser.uid
                val email=signedInUser.email!!
                val name=txtName.text.toString()
                val address=txtAddress.text.toString()
                val phone=txtPhone.text.toString()

                val db = Firebase.firestore

                // Create a new user with a first and last name
                val user = hashMapOf(
                    "Name" to name,
                    "address" to address,
                    "phone" to phone,
                    "Id" to userId,
                    "Email" to email
                )

// Add a new document with a generated ID
                db.collection("DGV")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d("tagged", "DocumentSnapshot added with ID: ${documentReference.id}")
//                        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToDashboardFragment())

                    }
                    .addOnFailureListener { e ->
                        Log.w("tagged", "Error adding document", e)
                    }


            }



        }



    }

}