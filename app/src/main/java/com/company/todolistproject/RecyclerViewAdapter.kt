package com.company.todolistproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.todolistproject.databinding.ItemRecyclerviewBinding

class RecyclerViewAdapter (private var list: ArrayList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val bindingWaluta = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(bindingWaluta)
    }

    private inner class MyViewHolder(val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val containerView: View
            get() = binding.root
    }

    private fun MyItem(viewHolder: MyViewHolder, position: Int) {
        val item = list[position]
        viewHolder.binding.item.text = item
        viewHolder.binding.BOTTOMEND.setOnClickListener {
            clickListener?.onItemClick(item, viewHolder, position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        MyItem(holder as MyViewHolder, position)
    }

    interface ClickListener {
        fun onItemClick(item: String, holder: RecyclerView.ViewHolder, position: Int)
    }

    fun setClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    private var clickListener: ClickListener? = null

}