package com.example.projetkotlin

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Basket(var books: ArrayList<Book>) : Parcelable{}

