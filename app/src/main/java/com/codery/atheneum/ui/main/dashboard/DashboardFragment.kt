package com.codery.atheneum.ui.main.dashboard

import com.codery.atheneum.R
import com.codery.atheneum.databinding.FragmentDashboardBinding
import com.codery.atheneum.ui.main.dashboard.DashboardViewModel.Companion.DashboardVM
import com.manavtamboli.axion.binding.BindingFragment
import com.manavtamboli.axion.lifecycle.flows
import com.manavtamboli.axion.navigation.findNavController
import com.manavtamboli.axion.navigation.safeNavigate

class DashboardFragment : BindingFragment<FragmentDashboardBinding>(FragmentDashboardBinding::class.java){

    private val viewModel by DashboardVM()
    private val navController by lazy { childFragmentManager.findNavController(R.id.dash_nav_host) }

    init {
        flows {
            collectFlow(viewModel.section){
                DashboardSection.getDirs(it)?.let(navController::safeNavigate)
                binding.currentSection = it.second
            }
        }
    }

    override fun FragmentDashboardBinding.initialize() {
        vm = viewModel
    }
}