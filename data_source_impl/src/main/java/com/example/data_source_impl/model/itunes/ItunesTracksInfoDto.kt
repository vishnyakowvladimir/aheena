package com.example.data_source_impl.model.itunes

import androidx.annotation.Keep

@Keep
class ItunesTracksInfoDto(
    val resultCount: Int?,
    val results: List<ItunesTrack>?,
) {

    @Keep
    class ItunesTrack(
        val trackId: Long,
        val trackName: String?,
        val artistName: String?,
        val previewUrl: String?,
    )
}
