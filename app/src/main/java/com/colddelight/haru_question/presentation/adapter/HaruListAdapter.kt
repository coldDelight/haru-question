package com.colddelight.haru_question.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.colddelight.domain.model.DomainQnA
import com.colddelight.haru_question.databinding.ItemRecyclerListBinding

class HaruListAdapter :RecyclerView.Adapter<HaruListAdapter.ViewHolder>() {
    private var items: List<DomainQnA> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setItem(items[position])
    }

    inner class ViewHolder(private val binding: ItemRecyclerListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setItem(item: DomainQnA){
            binding.tvListQuestion.text = item.question
            binding.tvListNum.text = item.id.toString()
            binding.tvListDate.text = item.date
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun setData(newItems: List<DomainQnA>) {
        this.items = newItems
        notifyDataSetChanged()
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount():Int{
        return items.size
    }




}