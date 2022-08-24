package com.fajarsn.mynewsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.fajarsn.mynewsapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

abstract class BaseAdapter<VH : RecyclerView.ViewHolder, T>(protected open val list: List<T>) : RecyclerView.Adapter<VH>() {
    protected lateinit var onItemClickCallBack: OnItemClickCallBack

    override fun getItemCount() = list.size

    fun setOnItemClickCallback(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    interface OnItemClickCallBack {
        fun <E> onItemClicked(data: E)
    }
}

abstract class BaseFragment : Fragment() {
    protected var viewBinding: ViewBinding? = null
    protected val binding get() = viewBinding!!

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
        setupAction()
    }

    protected abstract fun setupView()
    protected abstract fun setupViewModel()
    protected abstract fun setupAction()
}