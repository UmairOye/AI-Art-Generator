package com.example.imageartgenerator.domain.models

import com.example.imageartgenerator.BuildConfig
import com.google.gson.annotations.SerializedName

data class DreamBoothRequest(
    val key: String = BuildConfig.API_KEY,
    @SerializedName("model_id") val modelId: String,
    val prompt: String,
    @SerializedName("negative_prompt") val negativePrompt: String,
    val samples: Int,
    val width: Int,
    val height: Int,
    val steps: Int,
    @SerializedName("cfg_scale") val cfgScale: Double,
    val seed: String? = null,
    val webhook: String? = null
)