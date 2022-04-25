package com.sampleproject.domain.repository

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.sampleproject.domain.repository.interfaces.IPreferencesRepository

private const val PREFS = "sample_project_prefs"
private const val AUTH_DATA_KEY = "auth_data"

class PreferencesRepository(context: Context) : IPreferencesRepository {

    private val settings = EncryptedSharedPreferences.create(
        context,
        PREFS,
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override var token: String?
        get() = settings.getString(AUTH_DATA_KEY, null)
        set(value) = settings.edit().putString(AUTH_DATA_KEY, value).apply()
}
