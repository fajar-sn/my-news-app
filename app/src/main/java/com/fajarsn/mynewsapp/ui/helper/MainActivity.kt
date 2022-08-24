package com.fajarsn.mynewsapp.ui.helper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fajarsn.mynewsapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }
}