package com.example.pruebatecnica.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pruebatecnica.R
import com.example.pruebatecnica.databinding.FragmentMapsBinding
import com.example.pruebatecnica.domain.model.LocationItem
import com.example.pruebatecnica.utils.extension_functions.showAlertNoPermissions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

const val TAG = "LOCATION"

@AndroidEntryPoint
class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private lateinit var map: GoogleMap
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var db: FirebaseFirestore

    private lateinit var timer: CountDownTimer


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        db = FirebaseFirestore.getInstance()
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        checkPermissions()

        setupListeners()

        timer = object : CountDownTimer(180_000, 30_000) {
            override fun onTick(remaining: Long) {
                Log.i(TAG, "Quedan {(($remaining/60)/1000)} minutos")
            }

            override fun onFinish() {
                getLocation()
                Log.i(TAG, "Inicia timer... ${Timestamp(Date())}")
                timer.start()
            }
        }
    }

    private fun checkPermissions() {

        Dexter.withContext(requireContext())
            .withPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
            .withListener(object : PermissionListener {
                @SuppressLint("MissingPermission")
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    Log.i(TAG, "Start timer... ${Timestamp.now()}")
                    timer.start()
                    map.isMyLocationEnabled = true
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    showAlertNoPermissions()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    showAlertNoPermissions()
                }

            }).check()
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        map.isMyLocationEnabled = true

        mFusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    Log.i(TAG, "${location.latitude} - ${location.longitude}")
                    sendLocationToFirebase(LocationItem(
                        location.latitude,
                        location.longitude,
                        Timestamp.now()
                    ))
                }
            }
    }

    private fun sendLocationToFirebase(location: LocationItem) {
        db.collection("data")
            .add(location)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                getDocuments()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)

            }
    }

    private fun getDocuments() {
        db.collection("data")
            .orderBy("date", Query.Direction.DESCENDING).limit(5)
            .get()
            .addOnSuccessListener { documents ->
                map.clear()
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val latLng = LatLng(
                        document.data.get("latitude") as Double,
                        document.data.get("longitud") as Double
                    )
                    createMarker(latLng)
                }
                showMessageUpdatedLocations()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    private fun createMarker(latLng: LatLng) {
        map.addMarker(MarkerOptions().position(latLng))
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(latLng, 12f),
            1000,
            null
        )
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap!!
        map.setOnMyLocationButtonClickListener(this)
        getDocuments()
    }

    override fun onMyLocationButtonClick(): Boolean {
        return false
    }

    private fun setupListeners() {
        binding.btnRefresh.setOnClickListener {
            getDocuments()
        }
    }

    private fun showMessageUpdatedLocations() {
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.map_fragment_button_message_locations),
            Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        timer.cancel()
    }

}