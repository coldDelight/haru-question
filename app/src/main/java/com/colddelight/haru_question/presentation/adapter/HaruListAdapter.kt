package com.colddelight.haru_question.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.colddelight.domain.model.DomainQnA
import com.colddelight.domain.model.DomainReQnA
import com.colddelight.haru_question.databinding.ItemRecyclerListBinding
import java.text.DecimalFormat

class HaruListAdapter :RecyclerView.Adapter<HaruListAdapter.ViewHolder>() {
    private var items: List<DomainReQnA> = ArrayList()
    lateinit var onItemClick : (List<Int>)->Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            onItemClick(listOf(items[position].id,items[position].a_id))
        }
        holder.setItem(items[position])
    }

    inner class ViewHolder(private val binding: ItemRecyclerListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setItem(item: DomainReQnA){
            binding.tvListQuestion.text = item.question
            binding.tvListNum.text = DecimalFormat("000").format(item.id)
            binding.tvListDate.text = item.date
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    internal fun setData(newItems: List<DomainReQnA>) {
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