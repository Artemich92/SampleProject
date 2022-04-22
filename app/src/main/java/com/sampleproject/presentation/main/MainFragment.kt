package com.sampleproject.presentation.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.realcosmetology.android.utils.base.toolbar.TOOLBAR_COLOR_CHANGE_VALUE
import com.realcosmetology.android.utils.base.toolbar.ToolbarConfig
import com.realcosmetology.android.utils.base.toolbar.ToolbarIconType.ICON_ALERT_NON
import com.realcosmetology.android.utils.base.toolbar.ToolbarIconType.ICON_TEXT_REAL_COSMETOLOGY
import com.sampleproject.R
import com.sampleproject.databinding.FragmentMainBinding
import com.sampleproject.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainVM>(R.layout.fragment_main) {

    override val viewModel: MainVM by viewModels()

    override fun createToolbarConfig() = ToolbarConfig(
        binding.toolbar,
        homeDrawableRes = ICON_TEXT_REAL_COSMETOLOGY,
        firstIconDrawableRes = ICON_ALERT_NON
    ).apply {
        changeColorWhenScrolling(binding.scrollView, TOOLBAR_COLOR_CHANGE_VALUE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
    }
}
