package br.com.infracea.fiscalapp.screens.container.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.infracea.fiscalapp.R;

public class MapFragment extends Fragment {

    View view;
    private SupportMapFragment mapFragment;
    private MapView mapView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);

//        mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.event_map);
        mapView = view.findViewById(R.id.mapView);
//
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
//
        mapView.getMapAsync(onMapReady);

//        mapFragment.getMapAsync(onMapReady);

        return view;
    }

    OnMapReadyCallback onMapReady = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap map) {
//            map.addMarker(new MarkerOptions()
//                    .position(new LatLng(event.getAddress().getLatitude(), event.getAddress().getLongitude()))
//                    .title(event.getAddress().getName()));
//
            final LatLng LOCAL = new LatLng( -15.7801, -47.9292);
//
////            map.setMinZoomPreference(100.0f);
            map.moveCamera( CameraUpdateFactory.newLatLngZoom(LOCAL, 15) );

//            CameraUpdateFactory.newLatLngZoom( new LatLng(event.getArea().getLatitude(), event.getArea().getLongitude()) , 14.0f );
        }
    };

}
