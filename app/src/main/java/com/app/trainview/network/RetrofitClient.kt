package com.app.trainview.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {


    private val gson: Gson = GsonBuilder().create()
    private const val API_TOKEN = "rg_71a1a1fc78d1469d89c86cca85d9d807"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val requestWithAuth: Request = chain.request().newBuilder()
                .header("Authorization", "Bearer $API_TOKEN")
                .build()
            // use request with auth for more headers
            // finally proceed with the last request
            chain.proceed(requestWithAuth)
        }
        .build()


    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.railradar.in/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

}


