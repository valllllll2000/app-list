package com.vaxapp.appslist

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

internal class AppListViewModel(
    private val appStateUseCase: AppStateUseCase,
    private val application: Application
) : AndroidViewModel(application) {

    val state = mutableStateOf<AppUIState>(AppUIState.Loading)

    init {
        state.value = AppUIState.Loading
        viewModelScope.launch {
            state.value =
                AppUIState.AppUIStateReady(appStateUseCase.getApps(application.packageManager))
        }
    }
}

internal sealed class AppUIState {
    data class AppUIStateReady(val apps: List<InstalledApp>) : AppUIState()
    object Loading : AppUIState()
}
