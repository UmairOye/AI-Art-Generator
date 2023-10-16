package com.example.imageartgenerator.ui.fragments

import android.app.DownloadManager
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.imageartgenerator.databinding.FragmentDownloaderBinding
import com.example.imageartgenerator.utils.addOnBackPressedCallback
import com.example.imageartgenerator.utils.showToast
import java.io.File
import kotlin.random.Random

class Downloader : Fragment() {
    private var _binding: FragmentDownloaderBinding? = null
    private val binding get() = _binding!!
    private var imageUrl =
        "https://pub-3626123a908346a7a8be8d9295f44e26.r2.dev/generations/0-a57661ce-2d06-4821-b4dd-2dabe702ef8c.png"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDownloaderBinding.inflate(inflater, container, false)
        addOnBackPressedCallback { findNavController().popBackStack() }

//        val imageUrl = requireArguments().getString("url")

        Glide.with(requireContext())
            .load(imageUrl!!)
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.visibility = View.GONE
                    showToast("Image failed to load: ${e!!.message}")
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable?>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.visibility = View.GONE
                    binding.outputImage.setImageDrawable(resource)
                    return true
                }

            }).into(binding.outputImage)



        binding.download.setOnClickListener {
            val random = Random
            val randomValue = random.nextInt(0, 1000)
            downloadImageNew("my-image-$randomValue", imageUrl!!)
        }




        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun downloadImageNew(filename: String, downloadUrlOfImage: String) {
        try {
            val dm = requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
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
            showToast("Download started.")
        } catch (e: Exception) {
            showToast("Download failed ${e.message}.")
        }
    }


}