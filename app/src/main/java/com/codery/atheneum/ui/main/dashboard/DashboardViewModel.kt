package com.codery.atheneum.ui.main.dashboard

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.codery.atheneum.ui.main.dashboard.DashboardSection.Home
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DashboardViewModel : ViewModel(){

    private val _section = MutableStateFlow(Home to Home)
    val section = _section.asStateFlow()

    fun updateSection(new : DashboardSection){
        _section.value = _section.value.second to new
    }

    companion object {
        fun DashboardFragment.DashboardVM() = viewModels<DashboardViewModel>()
    }
}