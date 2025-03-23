package com.example.imageartgenerator.domain.models

import com.google.gson.annotations.SerializedName

data class DreamBoothResponse(
    val status: String,
    val tip: String?,
    @SerializedName("tip_1") val tip1: String?,
    val eta: Double?,
    val messege: String?,
    @SerializedName("webhook_status") val webhookStatus: String?,
    @SerializedName("fetch_result") val fetchResult: String?,
    val id: Long?,
    val output: List<String>?,
    val meta: MetaData?,
    @SerializedName("future_links") val futureLinks: List<String>?
)