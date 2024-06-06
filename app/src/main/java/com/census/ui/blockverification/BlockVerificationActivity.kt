package com.census.ui.blockverification

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import com.census.ui.base.BaseActivity
import com.census.BR
import com.census.R
import com.census.databinding.ActivityBlockVerificationBinding
import dagger.hilt.android.AndroidEntryPoint
import android.preference.PreferenceManager
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.census.data.local.db.DatabaseRepository
import com.census.data.local.db.tables.syncdata.SyncData
import com.census.ui.blockverification.dialog.LocationPermissionDialog
import com.census.utils.showToast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.LinearRing
import org.locationtech.jts.operation.distance.DistanceOp
import org.locationtech.jts.geom.Point as JTSPoint
import org.locationtech.jts.geom.Polygon as JTSPolygon
import org.osmdroid.config.Configuration
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polygon
import org.osmdroid.views.overlay.Polyline


import javax.inject.Inject


@AndroidEntryPoint
class BlockVerificationActivity :
    BaseActivity<BlockVerificationActivityViewModel, ActivityBlockVerificationBinding>(R.layout.activity_block_verification) {
    @Inject
    lateinit var databaseRepository: DatabaseRepository
    private lateinit var mapView: MapView
    private var jtsPolygon: JTSPolygon? = null
    private lateinit var locationClient: FusedLocationProviderClient
    private var priority: Int? = null
    private var location: String = ""
    private val polygonPointsList = mutableListOf<List<GeoPoint>>()

    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
//                showToast("Access Granted")
                priority = Priority.PRIORITY_HIGH_ACCURACY

                getCurrentLocation(priority!!)
            }

            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                priority = Priority.PRIORITY_BALANCED_POWER_ACCURACY

                getCurrentLocation(priority!!)
//                showToast("Access Granted")
            }

            else -> {
                showToast("Access Denied")
            }
        }
    }

    companion object {

        const val ON_BACK_PRESS = 0
        const val ON_CAPTURE_LOCATION_CLICK = 1
        const val ON_PROCEED_CLICK=2


        fun newIntent(
            context: Context
        ): Intent {
            val intent = Intent(context, BlockVerificationActivity::class.java)
            return intent
        }
    }


    override val viewModel: BlockVerificationActivityViewModel by viewModels()

    override fun getBindingVariable() = BR.viewModel


    override fun onNetworkConnected() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationClient = LocationServices.getFusedLocationProviderClient(this)
        // Load/initialize the osmdroid configuration
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))

        // Initialize the MapView
        mapView = findViewById(R.id.map)
    }


    override fun initUi() {
        viewModel.event.observe(this) {
            when (it) {


                ON_BACK_PRESS -> {
                    finish()
                }

                ON_CAPTURE_LOCATION_CLICK -> {
                    locationPermission()

                }
                ON_PROCEED_CLICK->{

                }
            }
        }

        updateStatusBar()
        setStatusBarTextColor(false)

        getActiveData()

    }

    ////////////// Get Active Data //////////////

    private fun getActiveData() {

        var syncData: SyncData
        lifecycleScope.launch {


            syncData = databaseRepository.readActiveData()
            runOnUiThread {
                bindings.tvProvinceValue.text = syncData.provinceName
                bindings.tvDistrictValue.text = syncData.districtName
                bindings.tvCityValue.text = syncData.city
                bindings.tvTehsilValue.text = syncData.tehsilName
                bindings.tvFileNameValue.text = syncData.fileName

            }

            if (syncData == null) {
                showMapForPakistan()
                showToast("No block is active")
            } else {
                syncData.json?.let {
                    setupMap(it)
                }
            }

        }

    }


    ////////////////////////////MAP///////////////////////

    private fun setupMap(jsonString: String) {
        mapView.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK)
        mapView.setBuiltInZoomControls(true)
        mapView.setMultiTouchControls(true)

        // Parse the JSON and draw the polygon
        val jsonObject = JSONObject(jsonString)
        val features = jsonObject.getJSONArray("features")

        var polygonBoundingBox: BoundingBox? = null
        val geometryFactory = GeometryFactory()
        polygonPointsList.clear() // Clear the list before adding new points



        for (i in 0 until features.length()) {
            val feature = features.getJSONObject(i)
            val geometry = feature.getJSONObject("geometry")
            val geometryType = geometry.getString("type")

            when (geometryType) {
                "Polygon" -> {
                    val coordinates = geometry.getJSONArray("coordinates").getJSONArray(0)
                    val osmdroidPolygon = Polygon(mapView)
                    val geoPoints = ArrayList<GeoPoint>()
                    val jtsCoordinates = mutableListOf<Coordinate>()

                    for (j in 0 until coordinates.length()) {
                        val coord = coordinates.getJSONArray(j)
                        val lon = coord.getDouble(0)
                        val lat = coord.getDouble(1)
                        geoPoints.add(GeoPoint(lat, lon))
                        jtsCoordinates.add(Coordinate(lon, lat))
                    }

                    osmdroidPolygon.points = geoPoints
                    mapView.overlays.add(osmdroidPolygon)
                    polygonPointsList.add(geoPoints)

                    // Create JTS polygon
                    val linearRing: LinearRing =
                        geometryFactory.createLinearRing(jtsCoordinates.toTypedArray())
                    jtsPolygon = geometryFactory.createPolygon(linearRing, null)

                    // Calculate the bounding box for the polygon
                    polygonBoundingBox = BoundingBox.fromGeoPointsSafe(geoPoints)
                }
            }
        }


        // If a bounding box was calculated for the polygon, zoom to it
        polygonBoundingBox?.let {
            val padding: Float = 5.0F // adjust this value according to your preference
            val paddedBoundingBox = it.increaseByScale(padding)
            mapView.zoomToBoundingBox(paddedBoundingBox, true)
        }
    }

    /////////////////// map validations /////////////////
    private fun closestPointOnSegment(p: GeoPoint, a: GeoPoint, b: GeoPoint): GeoPoint {
        val ap = doubleArrayOf(p.latitude - a.latitude, p.longitude - a.longitude)
        val ab = doubleArrayOf(b.latitude - a.latitude, b.longitude - a.longitude)
        val ab2 = ab[0] * ab[0] + ab[1] * ab[1]
        val ap_ab = ap[0] * ab[0] + ap[1] * ab[1]
        val t = ap_ab / ab2

        return when {
            t < 0.0 -> a
            t > 1.0 -> b
            else -> GeoPoint(a.latitude + ab[0] * t, a.longitude + ab[1] * t)
        }
    }

    private fun findClosestPointOnPolygon( location: GeoPoint):Double? {
        var closestPoint: GeoPoint? = null
        var minDistance = Double.MAX_VALUE

        for (polygonPoints in polygonPointsList) {
            for (i in 0 until polygonPoints.size) {
                val startPoint = polygonPoints[i]
                val endPoint = polygonPoints[(i + 1) % polygonPoints.size]
                val candidatePoint = closestPointOnSegment(location, startPoint, endPoint)
                val distance = location.distanceToAsDouble(candidatePoint)

                if (distance < minDistance) {
                    minDistance = distance
                    closestPoint = candidatePoint
                }
            }
        }
        var distance:Double?=null

        closestPoint?.let {
           distance= drawLine(location, it)
        }
        return distance
    }

    private fun drawLine( start: GeoPoint, end: GeoPoint):Double {
        val line = Polyline(mapView)
        line.color=ContextCompat.getColor(this,R.color.red_color_cancel)
        line.addPoint(start)
        line.addPoint(end)
        mapView.overlays.add(line)
        mapView.invalidate()
        val distance = start.distanceToAsDouble(end)
        return distance
    }



    private fun isPointInsidePolygon(point: GeoPoint, polygon: JTSPolygon): Boolean {
        val geometryFactory = GeometryFactory()
        val jtsPoint: JTSPoint =
            geometryFactory.createPoint(Coordinate(point.longitude, point.latitude))
        return polygon.contains(jtsPoint)
    }




    private fun showMapForPakistan() {
        val pakistanCenter =
            GeoPoint(30.3753, 69.3451) // Latitude and Longitude for Pakistan's center
        val zoomLevel = 6.0 // Adjust the zoom level as per your preference

        mapView.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK)
        mapView.setBuiltInZoomControls(true)
        mapView.setMultiTouchControls(true)

        mapView.controller.setZoom(zoomLevel)
        mapView.controller.setCenter(pakistanCenter)
    }


    //////////////////////GET CURRENT LOCATION////////////////////////////////
    @SuppressLint("MissingPermission")
    private fun getCurrentLocation(priority: Int) {
        showLoading()
        locationClient.getCurrentLocation(priority, CancellationTokenSource().token)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentLocation: Location? = task.result
                    currentLocation?.let {
//                        focusOnStartPosition(it.latitude, it.longitude)
//                        animateToLocation(GeoPoint(it.latitude,it.longitude))
//                        val geoPoint=GeoPoint(it.latitude,it.longitude)
                        val geoPoint=GeoPoint(31.483267463188803,74.31566681269855)//outside 20 meters

//                        val geoPoint=GeoPoint(31.48160866506337,74.3129336954781)


                        addCurrentLocationMarker(geoPoint)
//                        animateToLocation(GeoPoint(it.latitude, it.longitude))
                        confirmIfInsidePolygon(geoPoint)
//                        findClosestPointOnPolygon(GeoPoint(GeoPoint(it.latitude,it.longitude)))
                        animateToLocation(geoPoint)
//                        val isInsidePolygon:Boolean?=isPointInsidePolygon(GeoPoint(GeoPoint(it.latitude,it.longitude)),jtsPolygon!!)


                    }
                    Log.d(
                        "fetched_location",
                        "latitude: ${currentLocation?.latitude} longitude: ${currentLocation?.longitude}"
                    )
                }
            }
    }

    /////////////////////Confirmation////////////////////////////////////////
    private fun confirmIfInsidePolygon(geoPoint: GeoPoint) {
        val isInsidePolygon: Boolean? = jtsPolygon?.let { it1 ->
            isPointInsidePolygon(
                geoPoint,
                it1
            )
        }
        Log.d("resultT", "$isInsidePolygon")
        if (isInsidePolygon == true) {
            bindings.tvErrorText.text = "you are inside map"
            bindings.tvErrorText.setTextColor(ContextCompat.getColor(this,R.color.completed))
            viewModel.isInsideMap.value=true

        } else {

            var distance:Double?=null
               distance= findClosestPointOnPolygon(geoPoint)
            if (distance!=null && distance<=20.0){
                bindings.tvErrorText.text = "you are barely inside map"
                bindings.tvErrorText.setTextColor(ContextCompat.getColor(this,R.color.completed))
                viewModel.isInsideMap.value=true
                showToast(distance.toString())
            }
            else if (distance!=null && distance>20.0){
                viewModel.isInsideMap.value=false
                bindings.tvErrorText.text = "You are outside the map"
                bindings.tvErrorText.setTextColor(ContextCompat.getColor(this,R.color.red_color_cancel))
            }
            else{
                showToast("Please try again!")
            }


        }

        Log.d("resultp", "${isInsidePolygon.toString()}")
    }


    private fun addCurrentLocationMarker(location: GeoPoint) {
        mapView.overlays.run {
            val marker = Marker(mapView).apply {
                position = location
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                icon =
                    ContextCompat.getDrawable(this@BlockVerificationActivity, R.drawable.ic_marker)
            }
            add(marker)
        }
        mapView.invalidate()
    }

    private fun animateToLocation(location: GeoPoint) {
        val mapController = mapView.controller
        val currentZoomLevel = mapView.zoomLevelDouble
        mapController.animateTo(location, 20.0 , 800L)
        hideLoading()
    }

    /////////////// Location Permission //////////////
    private fun locationPermission() {

        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                priority = Priority.PRIORITY_HIGH_ACCURACY
                getCurrentLocation(priority!!)

            }

            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                priority = Priority.PRIORITY_BALANCED_POWER_ACCURACY
                getCurrentLocation(priority!!)

            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                showLocationPermissionDialog()
            }

            else -> {
                locationPermissionRequest.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }


    private fun openAppSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.data = Uri.fromParts("package", context.packageName, null)
        this.startActivity(intent)
    }

    private fun showLocationPermissionDialog() {
        val basicDialog = LocationPermissionDialog.newInstance()

        basicDialog.setHideCloseIcon(true)
        basicDialog.setIsCancelable(false)
        basicDialog.setDialogListener(object : LocationPermissionDialog.BasicDialogInterface {
            override fun onOpenSettingButtonClicked() {
                openAppSettings()
            }


        })

        basicDialog.show(supportFragmentManager)
        supportFragmentManager.executePendingTransactions()

    }


    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }


}