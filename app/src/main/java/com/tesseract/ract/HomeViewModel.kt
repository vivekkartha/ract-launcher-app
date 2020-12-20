package com.tesseract.ract

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tesseract.launchersdk.appinfo.AppInfo
import com.tesseract.launchersdk.appinfo.LauncherManager
import javax.inject.Inject

class HomeViewModel @ViewModelInject constructor(private val launcherSdk: LauncherManager) :
    ViewModel() {

    private val _installedAppsLiveData = MutableLiveData<List<AppInfo>>()

    val installedAppsLiveData: LiveData<List<AppInfo>>
        get() = _installedAppsLiveData

    fun getInstalledApps() {
        launcherSdk.getInstalledApps { appsList ->
            _installedAppsLiveData.value = appsList
        }
    }
}