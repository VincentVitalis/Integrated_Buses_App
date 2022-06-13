package com.submission1.integratedbusesapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Buses(
    val id: Int,
    val name: String,
    val route_start: String,
    val route_end: String,
    val passenger_count: String,
    val maxcapacity: String
): Parcelable

