package com.example.imageartgenerator.data.remote.api

import com.example.imageartgenerator.BuildConfig
import com.example.imageartgenerator.domain.models.DreamBoothRequest
import com.example.imageartgenerator.domain.models.DreamBoothResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST(BuildConfig.END_POINT)
    fun makeApiRequest(@Body requestBody: DreamBoothRequest): Call<DreamBoothResponse>
}