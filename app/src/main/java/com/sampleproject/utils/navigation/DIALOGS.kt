package com.sampleproject.utils.navigation

import androidx.navigation.NavDirections

enum class DIALOGS(
    val screenId: Int,
    var navDirections: NavDirections? = null
) {
    //ACCESS_LIMITED(R.id.accessLimitedDialogFragment),
}
