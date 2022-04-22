package com.sampleproject.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import androidx.core.view.isVisible
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.sampleproject.utils.provider.ConnectivityStatusProvider.ConnectivityStatus
import com.sampleproject.utils.provider.ConnectivityStatusProvider.ConnectivityStatus.CONNECTED
import com.sampleproject.utils.provider.ConnectivityStatusProvider.ConnectivityStatus.DISCONNECTED
import com.sampleproject.R
import com.sampleproject.databinding.InternetConnectionStatusBinding
import com.sampleproject.utils.ui.doOnClick

class CheckInternetConnection(private val binding: InternetConnectionStatusBinding) {

    private val context = binding.root.context

    private var firstInternetConnection = true

    fun checkInternetWhenOpenApp() {
        if (!checkForInternet()) {
            setResourcesInternetStatus(
                context.getColor(R.color.red),
                context.getString(R.string.no_internet_connection),
                R.drawable.ic_cross
            )
        }
    }

    fun setStatusConnectedResources(status: ConnectivityStatus) {
        when (status) {
            CONNECTED -> {
                if (!firstInternetConnection) {
                    setResourcesInternetStatus(
                        context.getColor(R.color.green),
                        context.getString(R.string.internet_connection_restored),
                        R.drawable.ic_done
                    )
                }
            }

            DISCONNECTED -> {
                firstInternetConnection = false
                setResourcesInternetStatus(
                    context.getColor(R.color.red),
                    context.getString(R.string.no_internet_connection),
                    R.drawable.ic_cross
                )
            }
        }
    }

    private fun checkForInternet(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    private fun setResourcesInternetStatus(
        @ColorInt color: Int,
        text: String,
        @DrawableRes drawableRes: Int,
    ) {
        with(binding) {
            animateIn()
            internetConnectionConstraint.backgroundTintList =
                android.content.res.ColorStateList.valueOf(color)
            messageTextView.text = text
            statusImageView.setImageResource(drawableRes)
            okTextView.doOnClick {
                animateOut()
            }
        }
    }

    private fun animateIn() {
        ViewCompat.animate(binding.internetConnectionConstraint)
            .alpha(1.0f)
            .translationY(0f)
            .setInterpolator(FastOutSlowInInterpolator())
            .setListener(object : ViewPropertyAnimatorListener {
                override fun onAnimationStart(view: View) {
                    view.isVisible = true
                }

                override fun onAnimationEnd(view: View) = Unit
                override fun onAnimationCancel(view: View?) = Unit
            }).start()
    }

    private fun animateOut() {
        ViewCompat.animate(binding.internetConnectionConstraint)
            .alpha(1.0f)
            .translationY((-binding.internetConnectionConstraint.height).toFloat())
            .setInterpolator(FastOutSlowInInterpolator())
            .setListener(object : ViewPropertyAnimatorListener {
                override fun onAnimationStart(view: View?) = Unit
                override fun onAnimationEnd(view: View) {
                    view.isVisible = false
                }

                override fun onAnimationCancel(view: View?) = Unit
            }).start()
    }
}
