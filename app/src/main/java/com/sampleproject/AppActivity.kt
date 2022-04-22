package com.sampleproject

import android.graphics.Rect
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.TooltipCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type
import androidx.core.view.forEach
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.sampleproject.databinding.ActivityMainBinding
import com.sampleproject.utils.CheckInternetConnection
import com.sampleproject.utils.navigation.DIALOGS
import com.sampleproject.utils.navigation.SCREENS
import com.sampleproject.utils.navigation.findNavController
import com.sampleproject.utils.navigation.setCurrentDialogScreenWithNavController
import com.sampleproject.utils.navigation.setCurrentScreenWithNavController
import com.sampleproject.utils.ui.doOnClick
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppActivity : AppCompatActivity() {

    private val viewModel: AppActivityVM by viewModels()

    private lateinit var binding: ActivityMainBinding

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private lateinit var internetConnection: CheckInternetConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        firebaseAnalytics = Firebase.analytics
        internetConnection = CheckInternetConnection(binding.internetConnectionStatus)
        setContentView(binding.root)
        activateSecurityMod()
        observeConnectionStatus()
        setBottomNavigationVisibility(false, findNavController(R.id.navHostFragment))
        internetConnection.checkInternetWhenOpenApp()
        binding.addProcedureFab.doOnClick {
        }
    }

    private fun observeConnectionStatus() {
        viewModel.connectivityLiveData.observe(this) { status ->
            internetConnection.setStatusConnectedResources(status)
        }
    }

    fun navigateToScreen(screen: SCREENS) {
        hideKeyboard()
        findNavController(R.id.navHostFragment).apply {
            setCurrentScreenWithNavController(screen)
            setBottomNavigationVisibility(screen.bottomNavigationVisibility, this)
        }
    }

    fun navigateToScreen(dialog: DIALOGS) {
        findNavController(R.id.navHostFragment).apply {
            setCurrentDialogScreenWithNavController(dialog)
        }
    }

    private fun activateSecurityMod() {
        // TODO пример
        /*if (BuildConfig.IS_SECURED) {
            window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        }*/
    }

    private fun hideKeyboard() {
        val imm = applicationContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    private fun setBottomNavigationVisibility(visibility: Boolean, navController: NavController) {
        if (visibility) {
            binding.bottomNavigationView.setupWithNavController(navController)
        }
        with(binding) {
            bottomBar.isVisible = visibility
            addProcedureFab.isVisible = visibility
        }
        //Необходимо для отключения tooltips(текстовых сообщений) при longClick (Alexander Yanchelenko / 29.12.2021)
        binding.bottomNavigationView.menu.forEach {
            TooltipCompat.setTooltipText(findViewById(it.itemId), null)
        }
    }

    fun selectBottomNavigation(item: MenuItem) {
        if (findNavController(R.id.navHostFragment).currentDestination?.id == item.itemId) return
        when (item.itemId) {
            // TODO пример
            R.id.navigation_profile -> { navigateToScreen(SCREENS.MAIN) }
            //R.id.navigation_favorites -> showDialogWithCurrentDescription(R.string.in_progress_section_description_favorites, R.string.title_understandable)
        }
    }

    //убирает клавиатуру,убирает фокус с EditText'a
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_UP) {
            val view = currentFocus
            hideKeyboard()
            if (view is EditText) {
                val outRect = Rect()
                view.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    view.clearFocus()
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    fun syncSetting(isIntegratedCalendar: Boolean, isReceivingNews: Boolean, isReceivingNotifications: Boolean) {
        viewModel.sendSettingsParameters(isIntegratedCalendar)
    }

    fun showOrHideBottomBar(isVisible: Boolean) {
        with(binding) {
            if (bottomBar.isVisible == isVisible) return
            bottomBar.isVisible = isVisible
            addProcedureFab.isVisible = isVisible
            if (isVisible) {
                addProcedureFab.requestFocusFromTouch()
                addProcedureFab.postDelayed({
                    addProcedureFab.clearFocus()
                }, 1)
            }
        }
    }

    private fun openOrHideKeyboardListener() {
        window.decorView.setOnApplyWindowInsetsListener { view, insets ->
            val insetsCompat = WindowInsetsCompat.toWindowInsetsCompat(insets, view)
            with(binding) {
                if (!bottomBar.isGone) {
                    bottomBar.isInvisible = insetsCompat.isVisible(Type.ime())
                    addProcedureFab.isInvisible = insetsCompat.isVisible(Type.ime())
                }
                view.onApplyWindowInsets(insets)
            }
        }
    }
}
