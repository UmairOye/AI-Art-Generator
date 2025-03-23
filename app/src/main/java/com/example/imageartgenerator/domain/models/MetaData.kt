package com.example.imageartgenerator.domain.models

import com.google.gson.annotations.SerializedName

data class MetaData(
    val prompt: String?,
    @SerializedName("model_id") val modelId: String?,
    @SerializedName("negative_prompt") val negativePrompt: String?,
    val scheduler: String?,
    @SerializedName("safety_checker") val safetyChecker: String?,
    @SerializedName("W") val width: Int?,
    @SerializedName("H") val height: Int?,
    @SerializedName("guidance_scale") val guidanceScale: Double?,
    val seed: Long?,
    val steps: Int?,
    @SerializedName("n_samples") val nSamples: Int?,
    @SerializedName("instant_response") val instantResponse: String?,
    @SerializedName("ip_adapter_id") val ipAdapterId: String?,
    @SerializedName("enhance_prompt") val enhancePrompt: String?,
    @SerializedName("ip_adapter_scale") val ipAdapterScale: Double?,
    @SerializedName("ip_adapter_image") val ipAdapterImage: String?,
    val upscale: String?,
    @SerializedName("use_karras_sigmas") val useKarrasSigmas: String?,
    @SerializedName("algorithm_type") val algorithmType: String?,
    @SerializedName("safety_checker_type") val safetyCheckerType: String?,
    val vae: String?,
    val lora: String?,
    @SerializedName("lora_strength") val loraStrength: Double?,
    @SerializedName("clip_skip") val clipSkip: Int?,
    val watermark: String?,
    val temp: String?,
    val base64: String?,
    val webhook: String?,
    @SerializedName("track_id") val trackId: String?,
    val id: String?,
    @SerializedName("file_prefix") val filePrefix: String?,
    @SerializedName("is_sdxl") val isSdxl: Boolean?,
    val output: List<String>?
)
