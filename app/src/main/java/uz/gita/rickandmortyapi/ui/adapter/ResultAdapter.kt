package uz.gita.rickandmortyapi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.rickandmortyapi.databinding.ItemLayoutBinding
import uz.gita.rickandmortyapi.model.Result

class ResultAdapter : PagingDataAdapter<Result, ResultAdapter.VH>(diffUtilCallback) {
    inner class VH(val binding : ItemLayoutBinding)  :RecyclerView.ViewHolder(binding.root){
        fun bind(result: Result){
            Glide.with(binding.root).load(result.image).into(binding.image)
            binding.location.text = result.location.name.toString()
            binding.name.text = result.name
            binding.subName.setText("${result.gender} - ${result.status}")
        }
        init {
            binding.container.setOnClickListener {
                onClickItemListener?.invoke(getItem(absoluteAdapterPosition)!!)
            }
        }
    }
    private var onClickItemListener : ((Result) -> Unit)? = null

    fun onClickItemListener(block : ((Result) -> Unit)){
        onClickItemListener = block
    }

    private var haveElementListener : (() -> Unit)? = null

    fun haveElementListener(block : (() -> Unit)){
        haveElementListener = block
    }


    companion object {
        val diffUtilCallback = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id && oldItem.name == newItem.name
            }

        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        return holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }
}