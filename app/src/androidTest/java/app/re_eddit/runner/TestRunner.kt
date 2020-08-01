package app.re_eddit.runner

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import app.re_eddit.app.TestApp

class TestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, TestApp::class.java.name, context)
    }
}
