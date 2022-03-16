package com.example.jetpackcomposetest.retrofit

import com.example.jetpackcomposetest.LatestRates
import com.google.gson.JsonElement
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitDao {
    @GET("latest")
    fun getLatestRates(): Call<LatestRates>
}