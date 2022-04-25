package com.sampleproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sampleproject.utils.provider.ConnectivityStatusProvider
import com.sampleproject.utils.provider.ConnectivityStatusProvider.ConnectivityStatus
import com.sampleproject.domain.repository.interfaces.IPreferencesRepository
import com.sampleproject.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AppActivityVM @Inject constructor(
    private val connectivityStatusProvider: ConnectivityStatusProvider,
    private val prefs: IPreferencesRepository,
) : BaseViewModel() {

    val connectivityLiveData: LiveData<ConnectivityStatus>
        get() = connectivityStatusProvider.connectionStatusLiveData

}
