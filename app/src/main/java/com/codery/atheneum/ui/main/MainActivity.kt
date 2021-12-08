package com.codery.atheneum.ui.main

import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.codery.atheneum.R
import com.codery.atheneum.databinding.ActivityMainBinding
import com.codery.atheneum.ui.main.dashboard.DashboardFragmentDirections
import com.manavtamboli.axion.binding.BindingActivity
import com.manavtamboli.axion.navigation.findNavController

class MainActivity : BindingActivity<ActivityMainBinding>(ActivityMainBinding::class.java){

    private val viewModel : MainViewModel by viewModels()

    private val navController by lazy { supportFragmentManager.findNavController(R.id.main_nav_host) }

    override fun onStart() {
        super.onStart()
        viewModel.navigation.observe(this){
            if (it != null) navController.navigate(it)
        }
    }

}

class MainViewModel : ViewModel(){
    private val _navigation = MutableLiveData<NavDirections?>()
    val navigation : LiveData<NavDirections?> = _navigation

    fun navigate(navDirections: NavDirections){
        _navigation.value = navDirections
        _navigation.value = null
    }
}