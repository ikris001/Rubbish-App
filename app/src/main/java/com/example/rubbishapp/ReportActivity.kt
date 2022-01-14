package com.example.rubbishapp

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polygon
import com.google.android.gms.maps.model.PolygonOptions
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_report.*

import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.SupportMapFragment


class ReportActivity : AppCompatActivity(), OnMapReadyCallback{

    private lateinit var mMap:GoogleMap
    var points:MutableList<LatLng> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        val mapFragment = supportFragmentManager
            .findFragmentById(com.example.rubbishapp.R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener{
            points.add(it)
            Toast.makeText(this, "Point added", Toast.LENGTH_SHORT).show()
        }
        submitAreaButton.setOnClickListener{
            var tempPoly:Polygon = mMap.addPolygon(
                PolygonOptions()
                    .strokeColor(Color.RED)
                    .fillColor(Color.WHITE)
                    .addAll(points))
            val database = FirebaseDatabase.getInstance().getReference("Areas")
            database.child(tempPoly.id).setValue(tempPoly.points).addOnSuccessListener {
                Toast.makeText(this,"Woo-hoo It's Done :)", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this,"Oh No It's not done :(", Toast.LENGTH_SHORT).show()
            }
        }
    }
}