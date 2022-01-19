package com.example.rubbishapp

import com.google.android.gms.maps.model.PolygonOptions

/**
 * An Area data class that is used to describe the areas that need cleaning on a map.
 *
 * @author Kristiyan Iliev
 * @param id to keep a unique String to identify an area
 * @param shape to keep details of the Polygons properties such as points, holes, clickability
 * @param reportedBy person who reported the area
 * @param colourStroke colour of the boundaries
 * @param colourFill colour of the filled area
 */

data class Area(
    val id:String,
    val shape: PolygonOptions?,
    val reportedBy:String,
    val colourStroke:Int,
    val colourFill:Int)
{
    constructor(): this("", null, "", 0, 0)
}
