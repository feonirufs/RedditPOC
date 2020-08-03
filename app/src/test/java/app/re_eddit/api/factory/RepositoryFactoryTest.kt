package app.re_eddit.api.factory

import app.re_eddit.api.repository.TopicRepositoryImpl
import app.re_eddit.api.service.RedditService
import assertk.assertThat
import assertk.assertions.isInstanceOf
import io.mockk.mockk
import org.junit.Test

class RepositoryFactoryTest {
    private val api = mockk<RedditService>()
    private val repositoryFactory = RepositoryFactory(api)

    @Test
    fun `should return instance of TopicReposiory`() {
        val repository = repositoryFactory.create()

        assertThat(repository).isInstanceOf(TopicRepositoryImpl::class.java)
    }
}