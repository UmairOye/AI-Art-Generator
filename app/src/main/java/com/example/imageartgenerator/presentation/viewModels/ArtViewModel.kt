package com.example.imageartgenerator.presentation.viewModels

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imageartgenerator.domain.models.DreamBoothModel
import com.example.imageartgenerator.domain.models.DreamBoothRequest
import com.example.imageartgenerator.domain.models.MetaData
import com.example.imageartgenerator.domain.models.RequestState
import com.example.imageartgenerator.domain.models.utils.showToast
import com.example.imageartgenerator.domain.repository.ArtRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(private val artRepository: ArtRepository,
    @ApplicationContext private val  context: Context): ViewModel() {
    private val _artState = MutableStateFlow<RequestState<MetaData?>>(RequestState.Idle)
    val artState = _artState.asStateFlow()


    fun downloadImageNew(filename: String, downloadUrlOfImage: String) {
        try {
            val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
            val downloadUri = Uri.parse(downloadUrlOfImage)
            val request = DownloadManager.Request(downloadUri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(filename)
                .setMimeType("image/jpeg")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_PICTURES,
                    File.separator + filename + ".jpg"
                )
            dm!!.enqueue(request)
            context.showToast("Download started.")
        } catch (e: Exception) {
            context.showToast("Download failed ${e.message}.")
        }
    }


    fun generateAIArt(model: String, prompt: String, negativePrompt: String, width: Int, height: Int){
        viewModelScope.launch {
            val dreamBoothRequest = DreamBoothRequest(
                modelId = model,
                prompt = prompt,
                negativePrompt = negativePrompt,
                samples = 1,
                width = width,
                height = height,
                steps = 30,
                cfgScale = 8.0,
            )
            artRepository.makeApiRequest(dreamBoothRequest)
                .onStart {  }
                .onCompletion {  }
                .collect{ response ->
                    _artState.value = response
                }
        }
    }


    fun getModelsList(): List<DreamBoothModel>{
        return artRepository.getModelsList()
    }

    fun changeArtState(){
        _artState.value = RequestState.Idle
    }
}