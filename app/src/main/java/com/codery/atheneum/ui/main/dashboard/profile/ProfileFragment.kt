package com.codery.atheneum.ui.main.dashboard.profile

import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.codery.atheneum.R
import com.codery.atheneum.databinding.FragmentProfileBinding
import com.codery.atheneum.models.User
import com.codery.atheneum.ui.main.MainViewModel
import com.codery.atheneum.ui.main.dashboard.DashboardFragmentDirections
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.manavtamboli.axion.binding.BindingFragment

class ProfileFragment : BindingFragment<FragmentProfileBinding>(FragmentProfileBinding::class.java) {

    private val viewModel: ProfileViewModel by viewModels()

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

    data class profileUser(val name: String?, val phone: String?, val address: String?)

    class ProfileViewModel : ViewModel() {

        val user: MutableLiveData<profileUser?> = MutableLiveData(null)

        init {
            val db = Firebase.firestore
            val email = Firebase.auth.currentUser?.email.toString()
            db.collection("DGV")
                .whereEqualTo("Email", email)
                .get()

                .addOnSuccessListener {
                    val add = it.documents.get(0).getString("address")
                    val name = it.documents.get(0).getString("Name")
                    val phone = it.documents.get(0).getString("phone")
                    Log.w("tagged", it.documents.get(0).get("address").toString())
//                binding.txtAddress2.text= it.documents.get(0).toString()
                    user.value = profileUser(name,phone,add)
                }
                .addOnFailureListener { exception ->
                    Log.w("tagged", "Error getting documents.", exception)
                }
        }
    }
}