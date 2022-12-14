package com.example.buckeyemarket.ui.item

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.buckeyemarket.R
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import java.util.*

/**
 * An activity that displays a map showing the place at the device's current location.
 */
class LocateMeActivity : AppCompatActivity(){
    lateinit var mapFragment : SupportMapFragment
    lateinit var googleMap : GoogleMap
    //var addressUser = Geocoder(this, Locale.getDefault()).getFromLocation(10.0,10.0, 1)

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            googleMap = it
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return@OnMapReadyCallback
            }
            googleMap.isMyLocationEnabled = true
            val locationResult = fusedLocationProviderClient.lastLocation
            googleMap.uiSettings.isMyLocationButtonEnabled = false
            locationResult.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Set the map's camera position to the current location of the device.
                    val lastKnownLocation = task.result
                    if (lastKnownLocation != null) {
                        googleMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    lastKnownLocation!!.latitude,
                                    lastKnownLocation!!.longitude
                                ), 10f
                            )
                        )
                    }
                    val geoCoder = Geocoder(this, Locale.getDefault())
                    val addressUser = geoCoder.getFromLocation(lastKnownLocation.latitude,lastKnownLocation.longitude, 1)
                    /*val address: String = addressUser[0].getAddressLine(0)
                    val city: String = addressUser[0].getLocality()
                    val state: String = addressUser[0].getAdminArea()
                    val country: String = addressUser[0].getCountryName()
                    val postalCode: String = addressUser[0].getPostalCode()
*/
                    val data = mapOf(
                        "Location" to addressUser.toString()
                    )
                    MyApplication.db.collection("UserLocation").add(data)

                }
            }

        })
    }

}