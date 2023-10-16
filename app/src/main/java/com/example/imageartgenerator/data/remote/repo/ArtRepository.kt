package com.example.imageartgenerator.data.remote.repo

import android.util.Log
import com.example.imageartgenerator.api.ArtApi
import com.example.imageartgenerator.models.ApiResponse
import com.example.imageartgenerator.models.DreamBoothRequest
import com.example.imageartgenerator.utils.Constants.TAG
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArtRepository(private val artApi: ArtApi) {

    fun makeApiRequest(requestBody: DreamBoothRequest, onResponse: (ApiResponse?) -> Unit) {
        val call = artApi.makeApiRequest(requestBody)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if(response.isSuccessful)
                {
                    Log.d(TAG, "onResponse: ${response.body()!!.status}")
                    onResponse(response.body())
                }else{
                    Log.d(TAG, "onResponse: not successful")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                onResponse(ApiResponse(t.message.toString(), 0.0, 0, emptyList()))
            }
        })
    }

}