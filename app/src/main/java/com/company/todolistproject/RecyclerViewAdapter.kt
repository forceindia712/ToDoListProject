package com.company.todolistproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.recyclerview.widget.RecyclerView
import com.company.todolistproject.databinding.ItemRecyclerviewBinding
import com.company.todolistproject.databinding.ItemWebviewBinding

class RecyclerViewAdapter (private var list: ArrayList<MyItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_NORMAL = 0
    private val VIEW_TYPE_WEBVIEW = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == VIEW_TYPE_NORMAL) {
            val bindingNormal = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MyViewHolder(bindingNormal)
        } else {
            val bindingWebView = ItemWebviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MyViewHolderWebView(bindingWebView)
        }

    }

    private inner class MyViewHolder(val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val containerView: View
            get() = binding.root
    }

    private inner class MyViewHolderWebView(val binding: ItemWebviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val containerView: View
            get() = binding.root
    }

    override fun getItemViewType(position: Int): Int {
        return if(list[position].link == true) VIEW_TYPE_WEBVIEW else VIEW_TYPE_NORMAL
    }

    private fun MyItem(viewHolder: MyViewHolder, position: Int) {
        val item = list[position]
        viewHolder.binding.item.text = item.string
        viewHolder.binding.BOTTOMEND.setOnClickListener {
            clickListener?.onItemClick(item, viewHolder, position)
        }
    }

    private fun MyWebView(viewHolder: MyViewHolderWebView, position: Int) {
        val item = list[position]
        viewHolder.binding.webview.webViewClient = WebViewClient()
        viewHolder.binding.webview.loadUrl(item.string!!)
        viewHolder.binding.webview.settings.javaScriptEnabled = true
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is MyViewHolder -> MyItem(holder, position)
            is MyViewHolderWebView -> MyWebView(holder, position)
        }
    }

    interface ClickListener {
        fun onItemClick(item: MyItem, holder: RecyclerView.ViewHolder, position: Int)
    }

    fun setClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    private var clickListener: ClickListener? = null

}