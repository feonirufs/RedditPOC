package app.re_eddit.api.factory

import app.re_eddit.api.service.RedditService
import assertk.assertThat
import assertk.assertions.isInstanceOf
import org.junit.Test

class WebServiceFactoryTest {

    @Test
    fun `should return instance of RedditService`() {
        val api = WebServiceFactory.webService

        assertThat(api).isInstanceOf(RedditService::class.java)
    }
}