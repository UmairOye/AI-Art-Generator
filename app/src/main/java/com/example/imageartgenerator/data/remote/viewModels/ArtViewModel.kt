package com.example.imageartgenerator.data.remote.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imageartgenerator.api.ApiClient
import com.example.imageartgenerator.data.remote.repo.ArtRepository
import com.example.imageartgenerator.models.ApiResponse
import com.example.imageartgenerator.models.DreamBoothRequest

class ArtViewModel(): ViewModel() {
    private val artAPi = ApiClient.artApi
    private val repository: ArtRepository = ArtRepository(artAPi)

    private val _response = MutableLiveData<ApiResponse?>()
    val response: LiveData<ApiResponse?> get() = _response

    fun makeApiRequest(requestBody: DreamBoothRequest) {
        repository.makeApiRequest(requestBody) {
            _response.postValue(it)
        }
    }
}