package br.com.infracea.fiscalapp.screens.container.map;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.infracea.fiscalapp.R;
import br.com.infracea.fiscalapp.util.GPS.GPSLocation;

public class MapFragment extends Fragment {



    View view;

    private SupportMapFragment mapFragment;
    private MapView mapView;
    private FloatingActionButton floatingActionButton;
    private GoogleMap googleMap;
    private MarkerOptions mp = new MarkerOptions();
    private boolean markerAdded = false;
    private Marker m;

    public Location lastUserLocation;
    private GeoQuery geoQuery;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);

        floatingActionButton = view.findViewById(R.id.frag_map_userlocation_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (googleMap != null && lastUserLocation != null) {
                    moveCamera();
                    //moveMarker(lastUserLocation);
                }
            }
        });

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

    @Override
    public void onResume() {
        super.onResume();
        if (lastUserLocation != null) {
            loadUsersMarkers(lastUserLocation);
        }
    }

    private void moveCameraToUserLocation() {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Atualizando sua localização");
        pd.show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (googleMap != null && lastUserLocation != null) {
                    moveCamera();
                    //moveMarker(lastUserLocation);
                    pd.dismiss();
                }
            }
        }, 2000);
    }

    private void moveCamera() {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastUserLocation.getLatitude(), lastUserLocation.getLongitude()), 17.0f));
    }

    OnMapReadyCallback onMapReady = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap map) {
            googleMap = map;
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
//            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                googleMap.setMyLocationEnabled(true);
//                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
//            } else {
//
//            }

        }
    };

    public void loadUsersMarkers(Location userLocation) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user_locations");
        GeoFire geoFire = new GeoFire(ref);

        geoQuery = geoFire.queryAtLocation(new GeoLocation(userLocation.getLatitude(),userLocation.getLongitude()), 10.0);

        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {


            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });

        //make te query
    }

}
