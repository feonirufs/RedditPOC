package app.re_eddit

import android.app.Application
import app.re_eddit.di.component.AppComponent

open class MainApp : Application() {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = AppComponent()
    }

    open fun appComponent() = appComponent
}