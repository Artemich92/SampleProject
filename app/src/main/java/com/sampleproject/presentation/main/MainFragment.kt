package com.sampleproject.presentation.main

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sampleproject.R
import com.sampleproject.databinding.FragmentMainBinding
import com.sampleproject.presentation.main.MainContract.Action.OnLogIn
import com.sampleproject.presentation.main.MainContract.Action.OnUiReady
import com.sampleproject.presentation.main.MainContract.Effect.LogIn
import com.sampleproject.utils.base.mvi.BaseFragment
import com.sampleproject.utils.ui.doOnClick
import dagger.hilt.android.AndroidEntryPoint

private const val TRUE_PASSWORD = "12345678"
private const val FALSE_PASSWORD = "123"
private const val PHONE_NUMBER = +71212121212

@AndroidEntryPoint
class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val viewModel: MainVM by viewModels()
    private val binding: FragmentMainBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.trueRequestButton.doOnClick {
            viewModel.setAction(OnLogIn(PHONE_NUMBER, TRUE_PASSWORD))
        }
        binding.falseRequestButton.doOnClick {
            viewModel.setAction(OnLogIn(PHONE_NUMBER, FALSE_PASSWORD))
        }
    }

    override fun initObservers() {
        viewModel.uiState observe { state ->
            binding.progressBar.isVisible = state.isLoading

            if (!state.isLoading) {
                // Nothing
            }
        }

        viewModel.uiEffect observe { effect ->
            when (effect) {
                is LogIn -> setBackground(binding.rootLayout, effect.isLogin)
            }
        }

        viewModel.setAction(OnUiReady)
    }

    private fun setBackground(view: ConstraintLayout, isTrueResponse: Boolean?) {
        when (isTrueResponse) {
            true -> view.background = ContextCompat.getDrawable(view.context, R.color.green)
            false -> view.background = ContextCompat.getDrawable(view.context, R.color.red)
            null -> view.background = ContextCompat.getDrawable(view.context, R.color.white)
        }
    }
}
