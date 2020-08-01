package app.re_eddit.app.di

import android.content.Context
import app.re_eddit.api.factory.RepositoryFactory
import app.re_eddit.api.service.RedditService
import app.re_eddit.di.component.AppComponent
import app.re_eddit.presentation.TopicViewModel

open class TestComponent(context: Context) : AppComponent(context) {
    lateinit var service: RedditService

    private val repositoryFactory by lazy { RepositoryFactory(service) }

    override val viewModel by lazy { TopicViewModel(repositoryFactory.create()) }
}