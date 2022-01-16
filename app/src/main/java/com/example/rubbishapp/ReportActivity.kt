package com.example.rubbishapp

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_report.*
import com.google.android.gms.common.api.GoogleApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.sql.Time
import java.time.LocalDateTime


class ReportActivity : AppCompatActivity(), OnMapReadyCallback{

    private lateinit var mMap:GoogleMap
    var points:MutableList<LatLng> = mutableListOf()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var auth: FirebaseAuth
    var userTemp:User? = User()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        val mapFragment = supportFragmentManager
            .findFragmentById(com.example.rubbishapp.R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        auth = FirebaseAuth.getInstance()

        val database = FirebaseDatabase.getInstance()
        val path: String = "Users/" + auth.currentUser?.uid.toString()
        val ref: DatabaseReference = database.getReference(path)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    userTemp= snapshot.getValue(User::class.java)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Location access denied", Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
        fusedLocationClient.lastLocation
                        .addOnSuccessListener {
                                location->
                            if (location != null) {
                                val cameraMove:LatLng = LatLng(location.latitude, location.longitude)
                                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition(cameraMove,20F, 0F, 0F)))
                }
                else{
                    Toast.makeText(this, "Location not retrieved", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Location failed to retrieve", Toast.LENGTH_SHORT).show()
            }
        mMap.setOnMapClickListener{
            points.add(it)
            mMap.addMarker(MarkerOptions().position(it))
        }
        submitAreaButton.setOnClickListener{
            val tempPoly:PolygonOptions =
                PolygonOptions()
                    .strokeColor(R.color.red_area)
                    .fillColor(R.color.red_area_transparent)
                    .addAll(points)
            val database = FirebaseDatabase.getInstance().getReference("Areas")
            points.clear()
            val tempArea = Area("",tempPoly, userTemp)
            database.push().setValue(tempArea).addOnFailureListener {
                Toast.makeText(this,"An error occurred. Try again...", Toast.LENGTH_SHORT).show()
            }
        }
    }
}