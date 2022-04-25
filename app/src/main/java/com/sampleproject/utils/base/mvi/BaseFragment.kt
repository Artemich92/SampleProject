package com.sampleproject.utils.base.mvi

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.sampleproject.AppActivity
import com.sampleproject.utils.base.toolbar.ToolbarConfig
import com.sampleproject.utils.navigation.DIALOGS
import com.sampleproject.utils.navigation.SCREENS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createToolbarConfig()
        initObservers()
    }

    open fun createToolbarConfig(): ToolbarConfig? = null

    open fun initObservers() = Unit

    inline infix fun <T> Flow<T>.observe(crossinline consumer: (T) -> Unit) {
        lifecycleScope.launchWhenStarted {
            this@observe.collect {
                consumer(it)
            }
        }
    }

    private fun navigateTo(screen: SCREENS) {
        if (activity is AppActivity) {
            (activity as AppActivity).navigateToScreen(screen)
        }
    }

    private fun navigateTo(dialog: DIALOGS) {
        if (activity is AppActivity) {
            (activity as AppActivity).navigateToScreen(dialog)
        }
    }

    /** Collect flow when lifecycle go to STARTED state */
/*    inline fun <T> Flow<T>.collectWhenStarted(
        scope: LifecycleCoroutineScope,
        crossinline action: suspend (value: T) -> Unit
    ): Job = scope.launchWhenStarted {
        val collector = FlowCollector<T> { value -> action(value) }
        collect(collector)
    }*/
}
