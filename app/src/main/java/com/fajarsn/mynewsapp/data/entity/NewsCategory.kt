package com.fajarsn.mynewsapp.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsCategory(var name: String, var value: String, var photo: Int) : Parcelable
