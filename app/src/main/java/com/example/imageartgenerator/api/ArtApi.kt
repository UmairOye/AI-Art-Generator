package com.example.imageartgenerator.api

import com.example.imageartgenerator.models.ApiResponse
import com.example.imageartgenerator.models.DreamBoothRequest
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ArtApi {
    @POST("/api/v4/dreambooth")
    fun makeApiRequest(@Body requestBody: DreamBoothRequest): Call<ApiResponse>
}