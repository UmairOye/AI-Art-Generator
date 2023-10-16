package com.example.imageartgenerator.ui.fragments

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.imageartgenerator.R
import com.example.imageartgenerator.adapters.modelsAdapter
import com.example.imageartgenerator.data.remote.viewModels.ArtViewModel
import com.example.imageartgenerator.databinding.FragmentHomeBinding
import com.example.imageartgenerator.models.DreamBoothModel
import com.example.imageartgenerator.models.DreamBoothRequest
import com.example.imageartgenerator.utils.Constants.API_KEY
import com.example.imageartgenerator.utils.Constants.TAG
import com.example.imageartgenerator.utils.addOnBackPressedCallback
import com.example.imageartgenerator.utils.showToast
import kotlinx.coroutines.launch
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


class Home : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ArtViewModel by activityViewModels()
    private lateinit var adapter: modelsAdapter
    private val modelList: ArrayList<DreamBoothModel> = ArrayList()
    private var currentModel: String = "MidJourney V4"
    private var currentWidthAndHeight: String = "512"
    private var imageUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        addOnBackPressedCallback { requireActivity().finishAffinity() }
        adapter = modelsAdapter()

        modelList.apply {
            add(DreamBoothModel(getString(R.string.midjourney_v4), "midjourney"))
            add(DreamBoothModel(getString(R.string.anything_v3), "anything-v3"))
            add(DreamBoothModel(getString(R.string.anything_v4), "anything-v4"))
            add(DreamBoothModel(getString(R.string.dream_shaper), "dream-shaper-8797"))
            add(DreamBoothModel(getString(R.string.realistic_vision), "realistic-vision-v13"))
            add(DreamBoothModel(getString(R.string.f222), "f222-diffusion"))
            add(DreamBoothModel(getString(R.string.vintedois), "vintedois-diffusion"))
            add(DreamBoothModel(getString(R.string.meinamix), "meinamix"))
            add(DreamBoothModel(getString(R.string.inkmix), "inkmix"))
            add(DreamBoothModel(getString(R.string.redream), "redream"))

            add(DreamBoothModel("SDXL 1.0", "sdxl"))
            add(DreamBoothModel("Ganyu Lora", "ganyu-lora"))
            add(DreamBoothModel("mix4cutegirl", "mix4cutegirl"))
            add(DreamBoothModel("moxin_1", "moxin"))
            add(DreamBoothModel("Pepe Frog", "pepe-frog"))
            add(DreamBoothModel("TimeLessXL", "timelessxl"))
            add(DreamBoothModel("sdxlceshi", "sdxlceshi"))
            add(DreamBoothModel("Juggernaut XL (SDXL model)", "juggernaut-xl"))

        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
        adapter.submitList(modelList)


        adapter.setOnClickListener(listener = object : modelsAdapter.OnClickListener {
            override fun onItemClick(artModel: String) {
                currentModel = artModel
                binding.recyclerView.postDelayed({
                    adapter.notifyDataSetChanged()
                }, 500)
            }
        })


        binding.radioGroup.setOnCheckedChangeListener { radioGroup, _ ->
            when (radioGroup.checkedRadioButtonId) {
                R.id.radio256 -> {
                    currentWidthAndHeight = "256"
                }

                R.id.radio512 -> {
                    currentWidthAndHeight = "512"
                }

                R.id.radio1024 -> {
                    currentWidthAndHeight = "1024"
                }
            }
        }


        binding.btnGenerate.setOnClickListener {
            Log.d(TAG, currentModel)
            if (binding.edPrompt.text.toString().isNotEmpty()) {
                binding.progressBar2.visibility = View.VISIBLE
                val dreamBoothRequest = DreamBoothRequest(
                    key = API_KEY,
                    model_id = currentModel,
                    prompt = binding.edPrompt.text.toString(),
                    negative_prompt = binding.edNegativePrompt.text.toString(),
                    width = currentWidthAndHeight,
                    height = currentWidthAndHeight,
                    samples = "1",
                    num_inference_steps = "30",
                    safety_checker = "no",
                    enhance_prompt = "yes",
                    seed = null,
                    guidance_scale = 7.5,
                    multi_lingual = "no",
                    panorama = "no",
                    self_attention = "no",
                    upscale = "no",
                    embeddings_model = null,
                    lora_model = null,
                    tomesd = "yes",
                    clip_skip = "2",
                    use_karras_sigmas = "yes",
                    vae = null,
                    lora_strength = null,
                    scheduler = "UniPCMultistepScheduler",
                    webhook = null,
                    track_id = null
                )


                Log.d(TAG, currentModel)
                lifecycleScope.launch {
                    viewModel.makeApiRequest(dreamBoothRequest)

                    viewModel.response.observe(viewLifecycleOwner) {
                        binding.progressBar2.visibility = View.GONE
                        if (it!!.status == "success") {
                            imageUrl = it.output[0]
                            try {
                                val bundle = Bundle()
                                bundle.putString("url", imageUrl)
                                findNavController().navigate(
                                    R.id.action_home2_to_downloader,
                                    bundle
                                )
                            } catch (ex: Exception) {
                                ex.printStackTrace()
                            }
                        } else {
                            showToast(it.status)
                        }
                    }
                }
            } else {
                showToast("fill prompt field first!")
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}