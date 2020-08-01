package app.re_eddit.api.factory

import app.re_eddit.BuildConfig
import app.re_eddit.BuildConfig.API_URL
import app.re_eddit.api.service.RedditService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


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

        val retrofit = Retrofit.Builder()
            .baseUrl(apiURL)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(provideMoshi()))
            .build()

        return retrofit.create(RedditService::class.java)
    }

    private fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    private fun createLogger(debuggable: Boolean): Interceptor {
        val logger = HttpLoggingInterceptor()
        logger.level = if (debuggable) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return logger
    }

    private fun createHttpClient(logger: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .addNetworkInterceptor { chain ->
                var request = chain.request()
                val url = request.url
                request = request.newBuilder()
                    .url(url.newBuilder()
                        .addQueryParameter("raw_json", "1") // So tokens aren't escaped
                        .build())
                    .build()
                chain.proceed(request)
            }
            .build()
    }
}