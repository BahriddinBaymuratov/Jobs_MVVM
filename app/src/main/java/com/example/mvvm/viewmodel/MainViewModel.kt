package com.example.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mvvm.model.JobToSave
import com.example.mvvm.model.RemoteJob
import com.example.mvvm.repository.JobRepository
import com.example.mvvm.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.http.Query

class MainViewModel(
    app: Application,
    private val repository: JobRepository
) : AndroidViewModel(app) {
    private var _remoteJobs: MutableLiveData<Resource<RemoteJob>> = MutableLiveData()
    val remoteJobs: LiveData<Resource<RemoteJob>> get() = _remoteJobs

    private val _searchedJobs: MutableLiveData<Resource<RemoteJob>> = MutableLiveData()
    val searchedJobs: LiveData<Resource<RemoteJob>> get() = _searchedJobs

    init {
        getAllRemoteJobs()
    }

    fun getAllRemoteJobs() {
        viewModelScope.launch {
            _remoteJobs.postValue(Resource.Loading())
            delay(1000L)
            try {
                val response = repository.getAllJobs()
                if (response.isSuccessful) {
                    _remoteJobs.postValue(Resource.Success(response.body()!!))
                }
            } catch (e: Exception) {
                _remoteJobs.postValue(Resource.Error(e.message!!))
            }
        }
    }

    fun getAllLocalJobs() = repository.getAllFavoriteJobs()
    fun saveFavoriteJob(job: JobToSave) {
        viewModelScope.launch {
            repository.saveJob(job)
        }
    }

    fun deleteJob(job: JobToSave) {
        viewModelScope.launch {
            repository.deleteJob(job)
        }
    }

    fun searchJob(query: String) {
        viewModelScope.launch {
            _searchedJobs.postValue(Resource.Loading())
            try {
                val response = repository.searchJob(query)
                if (response.isSuccessful) {
                    _searchedJobs.postValue(Resource.Success(response.body()!!))
                }
            } catch (e: Exception) {
                _searchedJobs.postValue(Resource.Error(e.message!!))
            }
        }
    }

    fun empty() {
        _remoteJobs = MutableLiveData()
    }
}