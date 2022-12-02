package com.example.mvvm.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mvvm.model.JobToSave


@Dao
interface JobDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(job:JobToSave)

    @Query("SELECT * FROM job ORDER BY id DESC")
    fun getALlFavoriteJob(): LiveData<List<JobToSave>>

    @Delete
    suspend fun deleteFavJob(job: JobToSave)

}