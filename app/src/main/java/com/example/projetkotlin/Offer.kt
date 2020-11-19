package com.example.projetkotlin

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Offer(
    val offers: List<OfferX>
) : Parcelable

@Parcelize
data class OfferX(
    val sliceValue: Int,
    val type: String,
    val value: Int
) : Parcelable