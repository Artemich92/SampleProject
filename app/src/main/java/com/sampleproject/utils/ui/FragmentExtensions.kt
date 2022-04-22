package com.sampleproject.utils.ui

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sampleproject.R

fun Fragment.openAppSettings() {
    val intent = Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        addCategory(Intent.CATEGORY_DEFAULT)
        data = Uri.parse("package:" + requireActivity().packageName)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
    }
    requireActivity().startActivity(intent)
}

fun Fragment.roundedBackground(dialog: Dialog): Dialog {
    (dialog as BottomSheetDialog).behavior.addBottomSheetCallback(object :
        BottomSheetBehavior.BottomSheetCallback() {

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheet.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_bottom_sheet)
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit
    })
    return dialog
}

fun Fragment.replaceFragment(fragment: Fragment) {
    parentFragmentManager.beginTransaction()
        .replace(R.id.navHostFragment, fragment)
        .commit()
}
