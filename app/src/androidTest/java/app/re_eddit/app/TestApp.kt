package app.re_eddit.app

import app.re_eddit.MainApp
import app.re_eddit.app.di.TestComponent
import app.re_eddit.di.component.AppComponent

open class TestApp : MainApp() {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = TestComponent(this)
    }

    override fun appComponent(): AppComponent {
        return appComponent
    }
}