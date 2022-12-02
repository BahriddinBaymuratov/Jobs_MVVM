package com.example.mvvm.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "job")
data class JobToSave(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val candidateRequiredLocation: String?,
    val category: String?,
    val companyLogoUrl: String?,
    val companyName: String?,
    val description: String?,
    val jobId: Int?,
    val jobType: String?,
    val publicationDate: String?,
    val salary: String?,
    val title: String?,
    val url: String?
)