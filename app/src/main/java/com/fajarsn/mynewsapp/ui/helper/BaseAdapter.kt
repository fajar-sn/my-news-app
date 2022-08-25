package com.fajarsn.mynewsapp.ui.helper

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<VH : RecyclerView.ViewHolder, T>(protected open val list: List<T>) :
    RecyclerView.Adapter<VH>() {
    protected lateinit var onItemClickCallBack: OnItemClickCallBack<T>

    override fun getItemCount() = list.size

    fun setOnItemClickCallback(onItemClickCallBack: OnItemClickCallBack<T>) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    interface OnItemClickCallBack<T> {
        fun onItemClicked(data: T)
    }
}
