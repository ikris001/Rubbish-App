package com.example.rubbishapp

import com.google.android.gms.maps.model.PolygonOptions


data class Area(
    val id:String,
    val shape: PolygonOptions?,
    val reportedBy:String,
    val colourStroke:Int,
    val colourFill:Int)
{
    constructor(): this("", null, "", 0, 0)
}
