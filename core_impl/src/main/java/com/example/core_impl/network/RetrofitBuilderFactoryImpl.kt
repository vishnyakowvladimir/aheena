package com.example.core_impl.network

import com.example.core_api.network.RetrofitBuilderFactory
import com.example.core_api.network.RetrofitBuilderFactory.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Inject

class RetrofitBuilderFactoryImpl @Inject constructor() : RetrofitBuilderFactory {

    override fun createBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
    }
}
