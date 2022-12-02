package com.example.mvvm.repository

import androidx.room.Query
import com.example.mvvm.database.JobDao
import com.example.mvvm.model.JobToSave
import com.example.mvvm.network.ApiService

class JobRepository(
    private val apiService: ApiService,
    private val db: JobDao
) {
    suspend fun getAllJobs(limit: Int = 25) = apiService.getRemoteJobs(limit)
    suspend fun saveJob(job:JobToSave) = db.addFavorite(job)
    fun getAllFavoriteJobs() = db.getALlFavoriteJob()
    suspend fun deleteJob(job: JobToSave) = db.deleteFavJob(job)
    suspend fun searchJob(query: String) = apiService.searchRemoteJob(query)
}