package com.example.rubbishapp

import com.google.android.gms.maps.model.PolygonOptions


data class Area(
    val id:String,
    val shape:PolygonOptions,
    val reportedBy:User?, ){
    constructor(): this("", PolygonOptions(), User())
}
