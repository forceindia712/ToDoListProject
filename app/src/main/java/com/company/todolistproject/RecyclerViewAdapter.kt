package com.company.todolistproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.company.todolistproject.databinding.ItemRecyclerviewBinding
import com.company.todolistproject.databinding.ItemWebviewBinding

class RecyclerViewAdapter (private var list: ArrayList<MyItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_NORMAL = 0
    private val VIEW_TYPE_DELETED = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == VIEW_TYPE_NORMAL) {
            val bindingNormal = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MyViewHolder(bindingNormal)
        } else {
            val bindingWebView = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MyViewHolderDeleted(bindingWebView)
        }
    }

    private inner class MyViewHolder(val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val containerView: View
            get() = binding.root
    }

    private inner class MyViewHolderDeleted(val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val containerView: View
            get() = binding.root
    }

    override fun getItemViewType(position: Int): Int {
        return if(list[position].isDeleted) VIEW_TYPE_DELETED else VIEW_TYPE_NORMAL
    }

    private fun MyItem(viewHolder: MyViewHolder, position: Int) {
        val item = list[position]
        viewHolder.binding.item.text = item.text
        viewHolder.binding.data.text = FileHelper.toDate(item.data)
        viewHolder.binding.button2.visibility = View.GONE
        viewHolder.binding.BOTTOMEND.setOnClickListener {
            clickListener?.onItemClick(item, viewHolder, position)
        }
    }

    private fun MyDeleted(viewHolder: MyViewHolderDeleted, position: Int) {
        val item = list[position]
        viewHolder.binding.item.text = item.text
        viewHolder.binding.data.text = FileHelper.toDate(item.data)
        viewHolder.binding.button2.visibility = View.VISIBLE
        viewHolder.binding.button2.setOnClickListener{
            clickListener?.onButtonClick(item, viewHolder, position)
        }
        viewHolder.binding.BOTTOMEND.setOnClickListener {
            clickListener?.onItemClick(item, viewHolder, position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is MyViewHolder -> MyItem(holder, position)
            is MyViewHolderDeleted -> MyDeleted(holder, position)
        }
    }

    interface ClickListener {
        fun onItemClick(item: MyItem, holder: RecyclerView.ViewHolder, position: Int)
        fun onButtonClick(item: MyItem, holder: RecyclerView.ViewHolder, position: Int)
    }

    fun setClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    private var clickListener: ClickListener? = null
}