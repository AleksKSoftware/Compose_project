package no.kindly.android

import android.app.Application
import no.kindly.android.sytac.ui.di.setupDependency

/**
 * [App] class for maintaining global application state and contains injection of [Koin].
 */
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        setupDependency(this)
    }
}