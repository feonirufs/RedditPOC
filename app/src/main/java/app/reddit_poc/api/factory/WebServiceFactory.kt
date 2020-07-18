package app.reddit_poc.api.factory

import app.reddit_poc.BuildConfig
import app.reddit_poc.BuildConfig.API_URL
import app.reddit_poc.api.service.RedditService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WebServiceFactory {
    val webService by lazy { createRedditWebService() }

    private fun createRedditWebService(): RedditService {
        val apiURL = API_URL
        val debuggable = BuildConfig.DEBUG
        val httpClient =
            createHttpClient(
                logger = createLogger(
                    debuggable
                )
            )
        val converter = GsonConverterFactory.create()

        val retrofit = Retrofit.Builder()
            .baseUrl(apiURL)
            .client(httpClient)
            .addConverterFactory(converter)
            .build()

        return retrofit.create(RedditService::class.java)
    }

    private fun createLogger(debuggable: Boolean): Interceptor {
        val logger = HttpLoggingInterceptor()
        logger.level = if (debuggable) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
        return logger
    }

    private fun createHttpClient(logger: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
    }
}