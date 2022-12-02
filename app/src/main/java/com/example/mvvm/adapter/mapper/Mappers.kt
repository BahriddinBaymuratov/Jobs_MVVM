package com.example.mvvm.adapter.mapper

import com.example.mvvm.model.Job
import com.example.mvvm.model.JobToSave

fun Job.toJobToSave(): JobToSave {
    return JobToSave(
        candidateRequiredLocation = candidateRequiredLocation,
        category = category,
        companyLogoUrl = companyLogoUrl,
        companyName = companyName,
        description = description,
        jobId = id,
        jobType = jobType,
        publicationDate = publicationDate,
        salary = salary,
        title = title,
        url = url
    )
}