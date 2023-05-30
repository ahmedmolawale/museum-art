package com.appsfactory.remote.apiservice

import com.appsfactory.remote.model.ArtDetailsRemoteModel
import com.appsfactory.remote.model.ArtResponseWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface ArtApiService {

    @GET("search")
    suspend fun searchArtCollection(@Query("q") query: String): Response<ArtResponseWrapper>

    @GET("objects/{objectId}")
    suspend fun getArtDetails(@Path("objectId") objectId: Long): Response<ArtDetailsRemoteModel>
}