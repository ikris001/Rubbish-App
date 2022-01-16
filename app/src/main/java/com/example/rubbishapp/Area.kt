package com.example.rubbishapp

import com.google.android.gms.maps.model.Polygon
import java.time.LocalDateTime

data class Area(
    val id:String,
    val shape:Polygon,
    val reportedBy:User?,
    val dateTime:LocalDateTime)
