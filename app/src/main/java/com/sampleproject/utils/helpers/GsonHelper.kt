package com.sampleproject.utils.helpers

import com.google.gson.Gson
import com.sampleproject.utils.helpers.GsonHelper.Companion
import timber.log.Timber

class GsonHelper {
    companion object {
        val gson = Gson()
    }
}

inline fun <reified T> String?.fromJson(): T? {
    return try {
        Companion.gson.fromJson(this, T::class.java)
    } catch (e: Exception) {
        Timber.e(e)
        null
    }
}

fun <T> T.toJson(): String? {
    return try {
        Companion.gson.toJson(this)
    } catch (e: Exception) {
        Timber.e(e)
        null
    }
}
