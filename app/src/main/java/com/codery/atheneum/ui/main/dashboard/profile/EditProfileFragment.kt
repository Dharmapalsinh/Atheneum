package com.codery.atheneum.ui.main.dashboard.profile

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.codery.atheneum.databinding.FragmentEditProfileBinding
import com.codery.atheneum.ui.main.MainViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.manavtamboli.axion.binding.BindingFragment

class EditProfileFragment : BindingFragment<FragmentEditProfileBinding>(FragmentEditProfileBinding::class.java) {

    private val viewmodel:EditProfileViewModel by viewModels()
    val mainViewModel:MainViewModel by activityViewModels()

    override fun FragmentEditProfileBinding.initialize() {

        viewmodel.user.observe(viewLifecycleOwner){
            it ?: return@observe
            txtPhone2.setText(it.phone)
            txtName2.text = it.name
            txtAddress2.setText(it.address)

        }
        viewmodel.state.observe(viewLifecycleOwner) {
            binding.registerProgress.visibility=if (it is SetDataState.Loading) View.VISIBLE else View.GONE
            when (it) {
                is SetDataState.Failed -> {
                    it.message
                }
                SetDataState.success->{
//                    mainViewModel.navigate(DashboardFragmentDirections.actionDashboardFragmentToEditProfileFragment())
//                    findNavController().navigate(R.id.action_editProfileFragment_to_dashboardFragment)
//                    mainViewModel.navigate(HomeFragmentDirections.homeToProfile())
                    requireActivity().onBackPressed()
                }
            }
        }


        btnUpdate.setOnClickListener {
            viewmodel.state.value=SetDataState.Loading
            viewmodel.SetData(txtName2.text.toString(),txtPhone2.text.toString(),txtAddress2.text.toString())


        }
    }

}

sealed class SetDataState {
    object Idle : SetDataState()
    object Loading : SetDataState()
    object success : SetDataState()
    data class Failed(val message : String) : SetDataState()
}

class EditProfileViewModel(application: Application):AndroidViewModel(application){
    val db=Firebase.firestore
    val user:MutableLiveData<ProfileFragment.ProfileUser?> = MutableLiveData(null)
    val state:MutableLiveData<SetDataState> = MutableLiveData(SetDataState.Idle)

    init {
        fetchData()
    }
    fun SetData(_name:String,_phone:String,_address:String) {

        val data= hashMapOf(
            "Name" to _name,
            "address" to _address,
            "phone" to _phone
        )

        val email= Firebase.auth.currentUser?.email.toString()
        db.collection("DGV")
            .whereEqualTo("Email",email)
            .get()


            .addOnSuccessListener {
                //state success
                it.documents[0].reference.update(data as Map<String, Any>)
                Toast.makeText(getApplication(),"Updated!!", Toast.LENGTH_SHORT).show()
                state.value=SetDataState.success
//                findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)

            }
            .addOnFailureListener { exception ->
                //state failed
                state.value=SetDataState.Failed("Error updating documents $exception")
                Log.w("tagged", "Error updating documents.", exception)
            }
    }

    private fun fetchData() {

        val email= Firebase.auth.currentUser?.email.toString()
        db.collection("users").document(Firebase.auth.currentUser!!.uid)
            .get()

            .addOnSuccessListener {
                val name=it.getString("name")
                val address=(it.getString("address"))
                val phone=(it.getString("phone"))
                user.value= ProfileFragment.ProfileUser(name,phone,address)

            }
            .addOnFailureListener { exception ->
                Log.w("tagged", "Error getting documents.", exception)
            }
    }

}