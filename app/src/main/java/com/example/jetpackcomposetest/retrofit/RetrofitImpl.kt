package com.example.jetpackcomposetest.retrofit

import android.util.Log
import com.example.jetpackcomposetest.LatestRates
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://forex.cbm.gov.mm//api/"

private val retrofit by lazy {
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient().newBuilder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private val retrofitDao: RetrofitDao by lazy { retrofit.create(RetrofitDao::class.java) }

fun getLatestRates(onResult: (LatestRates?) -> Unit) {
    retrofitDao.getLatestRates().enqueue(object : Callback<LatestRates> {
        override fun onResponse(call: Call<LatestRates>, response: Response<LatestRates>) {
            onResult(response.body())
        }

        override fun onFailure(call: Call<LatestRates>, t: Throwable) {
            Log.d("onFailure", t.toString())
            onResult(null)
        }
    })
}