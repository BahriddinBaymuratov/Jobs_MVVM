package com.example.mvvm.network

import com.example.mvvm.model.RemoteJob
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("remote-jobs")
    suspend fun getRemoteJobs(
        @Query("limit") limit: Int = 25
    ): Response<RemoteJob>

    @GET("remote-job")
    suspend fun searchRemoteJob(
        @Query("search") query: String
    ): Response<RemoteJob>
}