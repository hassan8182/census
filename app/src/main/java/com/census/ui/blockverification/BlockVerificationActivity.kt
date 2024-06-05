package com.census.ui.blockverification

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import com.census.ui.base.BaseActivity
import com.census.BR
import com.census.R
import com.census.databinding.ActivityBlockVerificationBinding
import com.census.databinding.ActivityDashboardBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import dagger.hilt.android.AndroidEntryPoint
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polygon


@AndroidEntryPoint
class BlockVerificationActivity :
    BaseActivity<BlockVerificationActivityViewModel, ActivityBlockVerificationBinding>(R.layout.activity_block_verification) {

    private lateinit var mapView: MapView
    companion object {

        const val ON_BACK_PRESS = 0
        const val ON_CLICK_CLICK = 1


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


    override fun initUi() {
        viewModel.event.observe(this) {
            when (it) {


                ON_BACK_PRESS -> {
                    finish()
                }

                ON_CLICK_CLICK -> {


                }
            }
        }


        updateStatusBar()
        setStatusBarTextColor(false)
        setupMap()

    }



    private fun setupMap() {
        // Load/initialize the osmdroid configuration
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))

        // Initialize the MapView
        mapView = findViewById(R.id.map)
        mapView.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK)
        mapView.setBuiltInZoomControls(true)
        mapView.setMultiTouchControls(true)

        val mapController = mapView.controller
        mapController.setZoom(18.0)
        val startPoint = GeoPoint(31.481444, 74.310611)
        mapController.setCenter(startPoint)

        // JSON string with the coordinates
        val jsonString = "{\"type\": \"FeatureCollection\", \"features\": [{\"type\": \"Feature\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [74.310611, 31.481444]}, \"properties\": {\"name\": \"31u00c2u00b028'53.2\\\"N 74u00c2u00b018'38.2\\\"E\"}}, {\"type\": \"Feature\", \"geometry\": {\"type\": \"Polygon\", \"coordinates\": [[[74.309934, 31.482239, 0.0], [74.31061, 31.480546, 0.0], [74.312938, 31.481288, 0.0], [74.312224, 31.482944, 0.0], [74.309934, 31.482239, 0.0]]]}, \"properties\": {\"name\": \"Polygon 2\"}}]}"

        // Parse the JSON and draw the polygon
        val jsonObject = JSONObject(jsonString)
        val features = jsonObject.getJSONArray("features")

        for (i in 0 until features.length()) {
            val feature = features.getJSONObject(i)
            val geometry = feature.getJSONObject("geometry")
            val geometryType = geometry.getString("type")

            when (geometryType) {
                "Point" -> {
                    val coordinates = geometry.getJSONArray("coordinates")
                    val lon = coordinates.getDouble(0)
                    val lat = coordinates.getDouble(1)
                    val marker = Marker(mapView)
                    marker.position = GeoPoint(lat, lon)
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    mapView.overlays.add(marker)
                }
                "Polygon" -> {
                    val coordinates = geometry.getJSONArray("coordinates").getJSONArray(0)
                    val polygon = Polygon(mapView)
                    val geoPoints = ArrayList<GeoPoint>()
                    for (j in 0 until coordinates.length()) {
                        val coord = coordinates.getJSONArray(j)
                        val lon = coord.getDouble(0)
                        val lat = coord.getDouble(1)
                        geoPoints.add(GeoPoint(lat, lon))
                    }
                    polygon.points = geoPoints
                    mapView.overlays.add(polygon)
                }
            }
        }


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