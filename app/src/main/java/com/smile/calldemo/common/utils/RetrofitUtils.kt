package com.smile.calldemo.common.utils

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitUtils {

    companion object {
        val instance: RetrofitUtils by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { RetrofitUtils() }
    }

    private var client: OkHttpClient? = null
    private var defaultUrl: String = ""

    private val retrofitMap = mutableMapOf<String, Retrofit>()

    private val serviceMap = mutableMapOf<String, Any>()

    fun initRetrofit(url: String, token: String) {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY

        client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original = chain.request()

                    val request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Basic ${token.trim()}")
                        .build()

                    return chain.proceed(request)
                }

            })
            .build()

        this.defaultUrl = url

        createRetrofit(url)

    }


    private fun createRetrofit(url: String): Retrofit {

        if (client == null) {
            throw NullPointerException("client must not be empty")
        }
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(client!!)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofitMap[url] = retrofit
        return retrofit
    }

    private fun getRetrofit(url: String?): Retrofit {
        if (url == null) {
            throw NullPointerException("url must not be empty")
        }

        val retrofit = if (retrofitMap.containsKey(url)) {
            retrofitMap[url]
        } else {
            createRetrofit(url)
        }
        return retrofit!!
    }


    fun <T> createService(service: Class<T>) = createService(this.defaultUrl, service)


    fun <T> createService(url: String, service: Class<T>): T {
        if (retrofitMap.isEmpty()) {
            throw NullPointerException("You should call initRetrofit() first!")
        }
        if (serviceMap.containsKey(url + service.name)) {
            return serviceMap[url + service.name] as T
        } else {
            val retrofit = getRetrofit(url)
            val t = retrofit.create(service)
            serviceMap[url + service.name] = t!!
            return t
        }
    }

}