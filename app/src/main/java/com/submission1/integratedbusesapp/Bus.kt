package com.submission1.integratedbusesapp

import com.google.gson.annotations.SerializedName

data class Bus(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("route")
    val route: String? = null,

    @field:SerializedName("passenger_count")
    val passenger_count: Int? = null,

    @field:SerializedName("position")
    val items: List<BusPosition?>? = null
)

data class BusPosition(

    @field:SerializedName("lat")
    val lat: Int? = null,

    @field:SerializedName("long")
    val long: Int? = null

)


