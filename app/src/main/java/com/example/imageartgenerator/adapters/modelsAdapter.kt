package com.example.imageartgenerator.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imageartgenerator.databinding.ListItemModelsBinding
import com.example.imageartgenerator.models.DreamBoothModel

class modelsAdapter : RecyclerView.Adapter<modelsAdapter.TopicAdapterViewHolder>() {
    private var mainList: List<DreamBoothModel> = ArrayList()
    private var listener: OnClickListener? = null
    private var selectedPosition: Int = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicAdapterViewHolder {
        val binding =
            ListItemModelsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopicAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mainList.size
    }

    override fun onBindViewHolder(holder: TopicAdapterViewHolder, position: Int) {
        holder.bind(mainList[position])
    }


    inner class TopicAdapterViewHolder(private val binding: ListItemModelsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DreamBoothModel) {
            binding.modelName.text = item.name
            binding.checkBox.isChecked = selectedPosition == adapterPosition

            binding.cdMain.setOnClickListener {
                selectedPosition = adapterPosition
                listener?.onItemClick(item.model_name)
            }

            binding.modelName.isSelected = true
            binding.checkBox.isClickable = false
        }


    }

    fun submitList(list: List<DreamBoothModel>) {
        this.mainList = list
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onItemClick(artModel: String)
    }

    fun setOnClickListener(listener: OnClickListener) {
        this.listener = listener
    }
}