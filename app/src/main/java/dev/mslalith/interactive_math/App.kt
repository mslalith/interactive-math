package dev.mslalith.interactive_math

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(BreadcrumbDebugTree())
    }
}

private class BreadcrumbDebugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String {
        return "(${element.fileName}:${element.lineNumber}) ${element.methodName}"
    }
}