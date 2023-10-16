package com.example.imageartgenerator.models

data class ApiResponse(val status: String, val generationTime: Double, val id: Int, val output: List<String>)
