package com.sampleproject.utils.navigation

import androidx.navigation.NavDirections
import com.sampleproject.R

enum class SCREENS(
    val screenId: Int,
    val bottomNavigationVisibility: Boolean,
    var navDirections: NavDirections? = null
) {
    MAIN(R.id.navigation_main, false)
}
