package br.com.infracea.fiscalapp.screens.container.map;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.infracea.fiscalapp.R;
import br.com.infracea.fiscalapp.screens.login.HomeActivity;
import br.com.infracea.fiscalapp.util.GPS.GPSLocation;
import br.com.infracea.fiscalapp.util.GPS.LocationService;

public class MapFragment extends Fragment {

    View view;
    private SupportMapFragment mapFragment;
    private MapView mapView;
    private GoogleMap googleMap;

    public Location lastUserLocation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView = view.findViewById(R.id.mapView);

        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(onMapReady);

//        mapFragment.getMapAsync(onMapReady);
        moveCameraToUserLocation();


        return view;
    }

    private void moveCameraToUserLocation() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (googleMap != null && lastUserLocation != null) {
                    moveCamera(lastUserLocation);
                }
            }
        }, 3000);
    }

    OnMapReadyCallback onMapReady = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap map) {
            googleMap = map;
//            map.addMarker(new MarkerOptions()
//                    .position(new LatLng(event.getAddress().getLatitude(), event.getAddress().getLongitude()))
//                    .title(event.getAddress().getName()));
//
            final LatLng LOCAL = new LatLng( -15.7801, -47.9292);
//
////            map.setMinZoomPreference(100.0f);


//            CameraUpdateFactory.newLatLngZoom( new LatLng(event.getArea().getLatitude(), event.getArea().getLongitude()) , 14.0f );
        }
    };

    public void moveCamera(Location mLastLocation) {
        try {
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())));
            CameraUpdateFactory.newLatLngZoom( new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()) , 14.0f );
        } catch (NullPointerException e) {
            //nada-faz
        }
    }

}
