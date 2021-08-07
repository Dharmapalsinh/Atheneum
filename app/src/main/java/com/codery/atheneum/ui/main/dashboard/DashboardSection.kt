package com.codery.atheneum.ui.main.dashboard

import androidx.navigation.NavDirections
import com.codery.atheneum.ui.main.dashboard.home.HomeFragmentDirections
import com.codery.atheneum.ui.main.dashboard.issued.IssuedBooksFragmentDirections
import com.codery.atheneum.ui.main.dashboard.library.LibraryFragmentDirections
import com.codery.atheneum.ui.main.dashboard.profile.ProfileFragmentDirections

enum class DashboardSection {
    Home,
    Issued,
    Library,
    Profile;

    companion object {
        fun getDirs(sections : Pair<DashboardSection, DashboardSection>) : NavDirections? {
            val (old, new) = sections
            return when(old) {
                Home -> when(new) {
                    Issued -> HomeFragmentDirections.homeToIssued()
                    Library -> HomeFragmentDirections.homeToLibrary()
                    Profile -> HomeFragmentDirections.homeToProfile()
                    else -> null
                }
                Issued -> when (new){
                    Home -> IssuedBooksFragmentDirections.issuedToHome()
                    Library -> IssuedBooksFragmentDirections.issuedToLibrary()
                    Profile -> IssuedBooksFragmentDirections.issuedToProfile()
                    else -> null
                }
                Library -> when (new){
                    Home -> LibraryFragmentDirections.libraryToHome()
                    Issued -> LibraryFragmentDirections.libraryToIssued()
                    Profile -> LibraryFragmentDirections.libraryToProfile()
                    else -> null
                }
                Profile -> when (new){
                    Home -> ProfileFragmentDirections.profileToHome()
                    Issued -> ProfileFragmentDirections.profileToIssued()
                    Library -> ProfileFragmentDirections.profileToLibrary()
                    else -> null
                }
            }
        }
    }
}