package com.masjoel.myrecyclerviewkotlin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hero(
    var name: String="",
    var detail: String="",
    var photo: Int = 0
/*    var photo: String=""*/
): Parcelable