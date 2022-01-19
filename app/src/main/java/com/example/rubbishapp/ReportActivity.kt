package com.example.rubbishapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_report.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

/**
 * ReportActivity class that displays a map and lets you select points on it which are converted into
 * a shape and then recorded as an area that needs cleaning.
 *
 * @author Kristiyan Iliev
 * @property mMap links to the displayed map
 * @see activity_report linked view to this activity
 */

class ReportActivity : AppCompatActivity(), OnMapReadyCallback{

    private lateinit var mMap:GoogleMap
    var points:MutableList<LatLng> = mutableListOf()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var auth: FirebaseAuth
    var userTemp:User? = User()

    /**
     * Method is triggered when activity starts. It is used to load the map and create the link to
     * the firebase and retrieve the user that is currently reporting.
     */
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        val toolbar: Toolbar = findViewById(R.id.Report_toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar

        // show the title defined in the manifest.xml file
        actionBar?.setDisplayShowTitleEnabled(true)

        // show the back button the the menu bar
        actionBar?.setDisplayHomeAsUpEnabled(true)

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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * The user is asked for location permission and the camera gets moved to the location of the
     * user. If the map is clicked, the location of the click gets recorded and a marker is
     * displayed. Once the submit button is clicked, the points are converted into a shape and sent
     * to the database.
     */
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
            mMap.addPolygon(tempPoly)
            var database = FirebaseDatabase.getInstance().getReference("Areas")
            points.clear()
            val tempArea = Area("",tempPoly, userTemp!!.id, ContextCompat.getColor(this, R.color.green_area), ContextCompat.getColor(this, R.color.green_area_transparent))
            database.push().setValue(tempArea).addOnFailureListener {
                Toast.makeText(this,"An error occurred. Try again...", Toast.LENGTH_SHORT).show()
            }
            database = FirebaseDatabase.getInstance().getReference("Users")
            database.child(userTemp!!.id).child("score").setValue(userTemp!!.score+1)
        }
    }
}