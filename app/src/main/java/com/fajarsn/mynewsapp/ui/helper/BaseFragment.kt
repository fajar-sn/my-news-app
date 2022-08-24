package com.fajarsn.mynewsapp.ui.helper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.fajarsn.mynewsapp.data.BaseResponse
import com.fajarsn.mynewsapp.data.Result
import com.fajarsn.mynewsapp.databinding.FragmentRecyclerViewBinding
import com.fajarsn.mynewsapp.databinding.LayoutErrorBinding

abstract class BaseFragment : Fragment() {
    protected var viewBinding: ViewBinding? = null
    protected val binding get() = viewBinding!!
    protected lateinit var viewModel: ViewModel
    protected lateinit var errorLayout: LayoutErrorBinding
    protected lateinit var progressBar: ProgressBar

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

    protected fun <T : BaseResponse> observeResultLiveData(
        result: Result?,
        retryButtonCallback: () -> Unit,
        successCallback: (Result.Success<T>) -> Unit,
    ) {
        if (result == null) return

        when (result) {
            is Result.Loading -> {
                progressBar.visibility = View.VISIBLE
                errorLayout.root.visibility = View.GONE
            }
            is Result.Error -> {
                progressBar.visibility = View.GONE
                errorLayout.root.visibility = View.VISIBLE
                errorLayout.errorTextView.text = result.error
                errorLayout.retryButton.setOnClickListener{ retryButtonCallback() }
            }
            is Result.Success<*> -> {
                progressBar.visibility = View.GONE
                errorLayout.root.visibility = View.GONE
                successCallback(result as Result.Success<T>)
            }
        }
    }
}

abstract class RecyclerViewFragment<VH : RecyclerView.ViewHolder, T> : BaseFragment() {
    protected lateinit var adapter: BaseAdapter<VH, T>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        return binding.root
    }
}