package com.example.core_impl.network

import com.example.core.network.RetrofitBuilderFactory
import com.example.core.network.RetrofitBuilderFactory.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
//import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitBuilderFactoryImpl @Inject constructor() : RetrofitBuilderFactory {

    override fun createBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
//            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
    }
}
