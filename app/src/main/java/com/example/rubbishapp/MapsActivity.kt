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
import java.util.concurrent.TimeUnit
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

/**
 * MapsActivity class that displays your position on a map and lets you see reported areas and
 * either clean them or report them as dirtier.
 *
 * @author Kristiyan Iliev
 * @property mMap links to the displayed map
 * @see activity_maps.xml linked view to this activity
 */


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

    /**
     * This method is used to open or hide the navigation bar to the left.
     */
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

    /**
     * Method is triggered when activity starts. It is used to link the navigation bar's buttons to
     * different activities in the application, to load the map and create the link to the firebase.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        auth = FirebaseAuth.getInstance()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


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
                R.id.nav_rate_review -> {
                    startActivity(Intent(this, Rating::class.java))
                }
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

    /**
     * Inflate the menu; this adds items to the action bar if it is present.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.map_toolbar, menu)
        return true
    }

    /**
     * Handle action bar item clicks here. The action bar will automatically handle clicks on the
     * Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            else -> super.onOptionsItemSelected(item)
        }
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * The user is asked for location permission, the areas are loaded on the map, and the camera
     * gets moved to the location of the user. If an area is clicked, it gives you the option to
     * either report it as dirtier or clean it.
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
