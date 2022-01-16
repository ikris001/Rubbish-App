package com.example.rubbishapp

import com.google.android.gms.maps.model.Polygon
import com.google.android.gms.maps.model.PolygonOptions
import java.time.LocalDateTime

data class Area(
    val id:String,
    val shape:PolygonOptions,
    val reportedBy:User?,
    val dateTime:LocalDateTime)
