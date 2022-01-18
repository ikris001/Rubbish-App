package com.example.rubbishapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_maps.*
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.*
import com.google.firebase.database.*
import java.util.*
import java.util.concurrent.TimeUnit
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var auth: FirebaseAuth
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var currentLocation: Location? = null
    lateinit var markerLocation:Marker
    var userTemp:User? = User()
    var displayedPolygons:MutableMap<Polygon,String> = mutableMapOf()

    fun openCloseNavigationDrawer1(view: View) {
        val nv_email = findViewById<View>(R.id.nav_header_user_email) as TextView
        nv_email.text = FirebaseAuth.getInstance()
            .currentUser!!.email


        val nv_name = findViewById<View>(R.id.nav_header_user_name) as TextView
        nv_name.text = FirebaseAuth.getInstance()
            .currentUser!!.displayName

        if (drawerLayout1.isDrawerOpen(GravityCompat.START)) {
            drawerLayout1.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout1.openDrawer(GravityCompat.START)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        auth = FirebaseAuth.getInstance()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

//        // overlays the toolbar on the screen
//        val toolbar: Toolbar = findViewById(R.id.Map_toolbar)
//        setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout1)
        val navView: NavigationView = findViewById(R.id.nav_view1)


        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        navView.setNavigationItemSelectedListener {

            when (it.itemId) {


                R.id.nav_home -> {
                    startActivity(Intent(this, MapsActivity::class.java))
                }
                R.id.nav_logout -> {
                    auth.signOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                R.id.nav_report -> {
                    startActivity(Intent(this, ReportActivity::class.java))
                }
                R.id.nav_rate_review -> Toast.makeText(
                    applicationContext, "Clicked Rate review",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_feedback -> startActivity(Intent(this, feedback::class.java))


                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))

                }



                R.id.nav_account -> {
                    startActivity(Intent(this, ProfilePage::class.java))
                }
                R.id.nav_contact_support -> startActivity(Intent(this, ContactSupport::class.java))
                R.id.nav_Term -> {
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/terms-and-conditions-rubbish-/home"))
                    startActivity(browserIntent)
                }


            }
            true
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.map_toolbar, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            else -> super.onOptionsItemSelected(item)
        }
    }



    /**

     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.

     */



    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        markerLocation = mMap.addMarker(MarkerOptions().position(LatLng(-89.0, 0.0)).icon(BitmapDescriptorFactory.fromResource(R.drawable.here)))


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

        var polly:Polygon
        var latLng:LatLng
        var areaPoints:MutableList<LatLng> = mutableListOf()
        val database = FirebaseDatabase.getInstance()
        val ref: DatabaseReference = database.getReference("Areas")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(a in displayedPolygons.keys){
                    a.remove()
                }
                displayedPolygons.clear()
                if (snapshot.exists()) {
                    for(i in snapshot.children) {
                        for(k in i.child("shape").child("points").children){
                            latLng = LatLng(k.child("latitude").getValue(Double::class.java)!!, k.child("longitude").getValue(Double::class.java)!!)
                            areaPoints.add(latLng)
                        }
                        polly = mMap.addPolygon(PolygonOptions().addAll(areaPoints).strokeColor(i.getValue(Area::class.java)?.colourStroke!!).fillColor(i.getValue(Area::class.java)?.colourFill!!).clickable(true))
                        displayedPolygons[polly] = i.key!!
                        areaPoints.clear()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        mMap.setOnPolygonClickListener {
            val dialog =  AlertDialog.Builder(this)
            dialog.setMessage("Please select if you're cleaning this area or reporting it as dirtier")
            dialog.setTitle("Dialog Box")
            dialog.setPositiveButton("Cleaning"
            ) { dialog, which ->
                if(it.strokeColor == ContextCompat.getColor(this, R.color.red_area)){
                    it.strokeColor = ContextCompat.getColor(this, R.color.yellow_area)
                    it.fillColor = ContextCompat.getColor(this, R.color.yellow_area_transparent)
                    database.getReference("Areas").child(displayedPolygons[it]!!).child("colourStroke").setValue(ContextCompat.getColor(this, R.color.yellow_area))
                    database.getReference("Areas").child(displayedPolygons[it]!!).child("colourFill").setValue(ContextCompat.getColor(this, R.color.yellow_area_transparent))
                }
                else if(it.strokeColor == ContextCompat.getColor(this, R.color.yellow_area)){
                    it.strokeColor = ContextCompat.getColor(this, R.color.green_area)
                    it.fillColor = ContextCompat.getColor(this, R.color.green_area_transparent)
                    database.getReference("Areas").child(displayedPolygons[it]!!).child("colourStroke").setValue(ContextCompat.getColor(this, R.color.green_area))
                    database.getReference("Areas").child(displayedPolygons[it]!!).child("colourFill").setValue(ContextCompat.getColor(this, R.color.green_area_transparent))
                }
                database.getReference("Users").child(userTemp!!.id).child("score").setValue(userTemp!!.score+1)
            }
            dialog.setNegativeButton("Reporting"
            ) { dialog, which ->
                if(it.strokeColor == ContextCompat.getColor(this, R.color.green_area)){
                    it.strokeColor = ContextCompat.getColor(this, R.color.yellow_area)
                    it.fillColor = ContextCompat.getColor(this, R.color.yellow_area_transparent)
                    database.getReference("Areas").child(displayedPolygons[it]!!).child("colourStroke").setValue(ContextCompat.getColor(this, R.color.yellow_area))
                    database.getReference("Areas").child(displayedPolygons[it]!!).child("colourFill").setValue(ContextCompat.getColor(this, R.color.yellow_area_transparent))
                }
                else if(it.strokeColor == ContextCompat.getColor(this, R.color.yellow_area)){
                    it.strokeColor = ContextCompat.getColor(this, R.color.red_area)
                    it.fillColor = ContextCompat.getColor(this, R.color.red_area_transparent)
                    database.getReference("Areas").child(displayedPolygons[it]!!).child("colourStroke").setValue(ContextCompat.getColor(this, R.color.red_area))
                    database.getReference("Areas").child(displayedPolygons[it]!!).child("colourFill").setValue(ContextCompat.getColor(this, R.color.red_area_transparent))
                }
                database.getReference("Users").child(userTemp!!.id).child("score").setValue(userTemp!!.score+1)
            }
            dialog.setNeutralButton("Cancel"
            ) { dialog, which ->

            }
            val alertDialog: AlertDialog = dialog.create()
            alertDialog.show()
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.create().apply {
            interval = TimeUnit.SECONDS.toMillis(10)
            fastestInterval = TimeUnit.SECONDS.toMillis(5)
            maxWaitTime = TimeUnit.SECONDS.toMillis(20)

            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                currentLocation = locationResult.lastLocation
                val marker:LatLng = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
                markerLocation.position = marker
                if(currentLocation != null) {
                    val cameraMove: LatLng = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition(cameraMove, 17F, 0F, 0F)))
                }
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())

    }


}
