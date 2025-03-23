package com.example.imageartgenerator.data.remote.repo

import android.content.Context
import android.util.Log
import com.example.imageartgenerator.R
import com.example.imageartgenerator.data.remote.api.ApiService
import com.example.imageartgenerator.domain.models.DreamBoothModel
import com.example.imageartgenerator.domain.models.DreamBoothRequest
import com.example.imageartgenerator.domain.models.DreamBoothResponse
import com.example.imageartgenerator.domain.models.MetaData
import com.example.imageartgenerator.domain.models.RequestState
import com.example.imageartgenerator.domain.models.utils.Constants.TAG
import com.example.imageartgenerator.domain.repository.ArtRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ArtRepositoryImplementation @Inject constructor(private val artApi: ApiService,
    @ApplicationContext private val context: Context) :
    ArtRepository {
    override fun makeApiRequest(requestBody: DreamBoothRequest): Flow<RequestState<MetaData?>> =
        flow {
            emit(RequestState.Loading)

            try {
                val response = suspendCoroutine { continuation ->
                    artApi.makeApiRequest(requestBody)
                        .enqueue(object : Callback<DreamBoothResponse> {
                            override fun onResponse(
                                call: Call<DreamBoothResponse>,
                                response: Response<DreamBoothResponse>
                            ) {
                                continuation.resume(response)
                            }

                            override fun onFailure(call: Call<DreamBoothResponse>, t: Throwable) {
                                continuation.resumeWithException(t)
                            }
                        })
                }

                if (response.isSuccessful && response.body() != null) {
                    emit(RequestState.Success(response.body()!!.meta))
                    Log.d(TAG, "onResponse: ${response.body()?.meta}")
                } else {
                    emit(RequestState.Error(Exception("Response not successful")))
                    Log.d(TAG, "onResponse: not successful")
                }

            } catch (e: Exception) {
                emit(RequestState.Error(e))
                Log.d(TAG, "onFailure: ${e.message}")
            }
        }.flowOn(Dispatchers.IO)


    override fun getModelsList(): List<DreamBoothModel> {
        val artModels = ArrayList<DreamBoothModel>()
        artModels.apply {
            add(DreamBoothModel(context.getString(R.string.midjourney_v4), "midjourney"))
            add(DreamBoothModel(context.getString(R.string.anything_v3), "anything-v3"))
            add(DreamBoothModel(context.getString(R.string.anything_v4), "anything-v4"))
            add(DreamBoothModel(context.getString(R.string.dream_shaper), "dream-shaper-8797"))
            add(DreamBoothModel(context.getString(R.string.realistic_vision), "realistic-vision-v13"))
            add(DreamBoothModel(context.getString(R.string.f222), "f222-diffusion"))
            add(DreamBoothModel(context.getString(R.string.vintedois), "vintedois-diffusion"))
            add(DreamBoothModel(context.getString(R.string.meinamix), "meinamix"))
            add(DreamBoothModel(context.getString(R.string.inkmix), "inkmix"))
            add(DreamBoothModel(context.getString(R.string.redream), "redream"))

            add(DreamBoothModel("SDXL 1.0", "sdxl"))
            add(DreamBoothModel("Ganyu Lora", "ganyu-lora"))
            add(DreamBoothModel("mix4cutegirl", "mix4cutegirl"))
            add(DreamBoothModel("moxin_1", "moxin"))
            add(DreamBoothModel("Pepe Frog", "pepe-frog"))
            add(DreamBoothModel("TimeLessXL", "timelessxl"))
            add(DreamBoothModel("sdxlceshi", "sdxlceshi"))
        }


        return artModels
    }
}