package br.com.caelum.twittelumappweb.fragment

import androidx.lifecycle.ViewModelProvider
import br.com.caelum.twittelumappweb.viewmodel.TweetViewModel
import br.com.caelum.twittelumappweb.viewmodel.ViewModelFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapaFragment : SupportMapFragment(), OnMapReadyCallback {
    private val viewModel by lazy {
        ViewModelProvider(this, ViewModelFactory)[TweetViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
         getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val lista = viewModel.tweets().value
        lista?.forEach { tweet ->
            val markerOptions = MarkerOptions()
            markerOptions.position(LatLng(tweet.latitude, tweet.longitude))
            markerOptions.title(tweet.dono.nome)
            googleMap?.addMarker(markerOptions)
        }
    }
}

