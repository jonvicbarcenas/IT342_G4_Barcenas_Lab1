package com.dainsleif.miniappauth.network

import android.content.Context
import com.dainsleif.miniappauth.R
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    @Volatile
    private var api: AuthApi? = null

    fun getApi(context: Context): AuthApi {
        return api ?: synchronized(this) {
            api ?: buildRetrofit(context).create(AuthApi::class.java).also { api = it }
        }
    }

    private fun buildRetrofit(context: Context): Retrofit {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val baseUrl = context.getString(R.string.base_url)

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
