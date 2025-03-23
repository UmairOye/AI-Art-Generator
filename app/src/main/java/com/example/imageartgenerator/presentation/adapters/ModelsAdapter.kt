package com.example.imageartgenerator.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imageartgenerator.databinding.ListItemModelsBinding
import com.example.imageartgenerator.domain.models.DreamBoothModel

class ModelsAdapter : RecyclerView.Adapter<ModelsAdapter.TopicAdapterViewHolder>() {

    private var mainList: List<DreamBoothModel> = ArrayList()
    private var listener: OnClickListener? = null
    private var selectedPosition: Int = RecyclerView.NO_POSITION
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicAdapterViewHolder {
        val binding =
            ListItemModelsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopicAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mainList.size
    }

    override fun onBindViewHolder(holder: TopicAdapterViewHolder, position: Int) {
        holder.bind(mainList[position], position)
    }

    inner class TopicAdapterViewHolder(private val binding: ListItemModelsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DreamBoothModel, position: Int) {
            binding.modelName.text = item.name
            binding.checkBox.isChecked = selectedPosition == position

            binding.cdMain.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)
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
