package app.re_eddit.di.component

import app.re_eddit.api.factory.RepositoryFactory
import app.re_eddit.api.factory.WebServiceFactory
import app.re_eddit.presentation.TopicViewModel

open class AppComponent {
    private val service = WebServiceFactory.webService

    private val repositoryFactory = RepositoryFactory(service)

    open val viewModel by lazy { TopicViewModel(repositoryFactory.create()) }
}