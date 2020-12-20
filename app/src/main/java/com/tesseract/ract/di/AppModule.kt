package com.tesseract.ract.di

import android.content.Context
import com.tesseract.launchersdk.LauncherSdk
import com.tesseract.launchersdk.appinfo.LauncherManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideLaunchManager(@ApplicationContext context: Context): LauncherManager =
        LauncherSdk.getInstance(context)
}