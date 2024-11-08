package com.example.data_source_impl.api

import com.example.data_source_impl.model.itunes.ItunesTracksInfoDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApi {

    @GET("search")
    suspend fun loadItunesTrackList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("term") term: String
    ): ItunesTracksInfoDto
}
