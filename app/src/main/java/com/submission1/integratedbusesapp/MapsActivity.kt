package com.submission1.integratedbusesapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.submission1.integratedbusesapp.databinding.ActivityMapsBinding
import com.submission1.integratedbusesapp.ui.login.LoginActivity
import kotlin.properties.Delegates

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var firebaseUser: FirebaseUser
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private var lastKnownLocation: Location? = null
    private var cameraPosition: CameraPosition? = null
    private var lat: Double = 0.0
    private var long: Double = 0.0
    private var user_role: String? = null
    private var TAG : String = MapsActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        user_role= intent?.getStringExtra(ROLE)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
        }
        binding.cariBus.setOnClickListener {
            val moveintent = Intent(this@MapsActivity,BusListActivity::class.java)
            moveintent.putExtra(BusListActivity.LAT,lat)
            moveintent.putExtra(BusListActivity.LONG,long)
            startActivity(moveintent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return if(user_role=="Kenek") {
            val inflater = menuInflater
            inflater.inflate(R.menu.menu_kenek, menu)
            true
        } else{
            val inflater = menuInflater
            inflater.inflate(R.menu.menu_user,menu)
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.Edit_Bus){

        }
        else if(item.itemId==R.id.Sign_out){
            FirebaseAuth.getInstance().signOut()
            val moveIntent = Intent(this@MapsActivity,LoginActivity::class.java)
            startActivity(moveIntent)
        }

        return true
    }
    override fun onSaveInstanceState(outState: Bundle) {

        mMap.let { map ->
            outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
            outState.putParcelable(KEY_LOCATION, lastKnownLocation)
        }
        super.onSaveInstanceState(outState)
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
        this.mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        getMyLocation()

    }
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }
    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            getDeviceLocation()
            } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {

//         * Get the best and most recent location of the device, which may be null in rare
//         * cases when a location is not available.
         val locationResult = fusedLocationProviderClient.lastLocation
         locationResult.addOnCompleteListener(this) { task ->
             if (task.isSuccessful) {
                 // Set the map's camera position to the current location of the device.
                 lastKnownLocation = task.result
                 if (lastKnownLocation != null) {
                     lat = lastKnownLocation!!.latitude
                     long = lastKnownLocation!!.longitude
                     mMap.moveCamera(
                         CameraUpdateFactory.newLatLngZoom(
                             LatLng(
                                 lastKnownLocation!!.latitude,
                                 lastKnownLocation!!.longitude
                             ), DEFAULT_ZOOM.toFloat()
                         )
                     )
                 }
             } else {
                 Log.d(TAG, "Current location is null. Using defaults.")
                 Log.e(TAG, "Exception: %s", task.exception)
                 mMap.moveCamera(
                     CameraUpdateFactory
                         .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat())
                 )
                 mMap.uiSettings.isMyLocationButtonEnabled = false
             }
         }
    }

    companion object{
        private const val DEFAULT_ZOOM = 15
        const val ROLE = "ROLE"
        const val USER_NAME = "USER_NAME"

        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"
    }
}
