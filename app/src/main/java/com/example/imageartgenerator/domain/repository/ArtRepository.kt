package com.example.imageartgenerator.domain.repository

import com.example.imageartgenerator.domain.models.DreamBoothModel
import com.example.imageartgenerator.domain.models.DreamBoothRequest
import com.example.imageartgenerator.domain.models.MetaData
import com.example.imageartgenerator.domain.models.RequestState
import kotlinx.coroutines.flow.Flow

interface ArtRepository {
    fun makeApiRequest(requestBody: DreamBoothRequest): Flow<RequestState<MetaData?>>
    fun getModelsList(): List<DreamBoothModel>
}