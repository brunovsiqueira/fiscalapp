package br.com.infracea.fiscalapp.screens.container;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

import br.com.infracea.fiscalapp.R;
import br.com.infracea.fiscalapp.basic.BasicActivity;
import br.com.infracea.fiscalapp.models.User;
import br.com.infracea.fiscalapp.screens.container.chat.ChatFragment;
import br.com.infracea.fiscalapp.screens.container.map.MapFragment;
import br.com.infracea.fiscalapp.screens.container.menu.MenuFragment;
import br.com.infracea.fiscalapp.screens.login.HomeActivity;
import br.com.infracea.fiscalapp.screens.login.LoginActivity;
import br.com.infracea.fiscalapp.util.GPS.GPSLocation;
import br.com.infracea.fiscalapp.util.GPS.LocationService;

public class ContainerActivity extends BasicActivity {

    private static final int RC_SIGN_IN = 123;

    private BottomNavigationView bottomNavigationView;
    private android.support.v4.app.FragmentManager fragmentManager;

    private ChatFragment chatFragment;
    private MapFragment mapFragment;
    private MenuFragment menuFragment;

    private GPSLocation myLocManager;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference().child("users");

    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        myLocManager = new GPSLocation(ContainerActivity.this);
        myLocManager.mGoogleApiClient.connect();

        instantiateFragments();
        addFragments();

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            startService(new Intent(this, LocationService.class));
        } else {
            requestPermission();
        }


        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            getUserInfoFromFirebase();
        } else {
            startActivity(new Intent(ContainerActivity.this, HomeActivity.class));
        }

        findViewItems();
        loadFragment(menuFragment); //default fragment

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("fiscalapp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("LOGIN", false);
        editor.commit();

//        myLocManager.mGoogleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (myLocManager != null) {
            myLocManager.startLocationUpdates();
        }
        //attach firebase listener
//        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (myLocManager != null) {
            myLocManager.stopLocationUpdates();
        }
    }

    private void findViewItems() {

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        //bottomNavigationView.
    }

    public void locationUpdated(Location location) {
//        Log.d("CONTAINER", "Camera Moved New Location: " + location.toString());
//        if (mapFragment.lastUserLocation != null) {
//            mapFragment.lastUserLocation = location;
//        } else {
//            mapFragment.lastUserLocation = location;
//            mapFragment.moveCamera(location);
//        }
        mapFragment.lastUserLocation = location;
        mapFragment.moveCamera(location);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //verificar se deu ou nao a permissao, se deu, passa de tela
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                this.startService(new Intent(this, LocationService.class));
            } else {
                Toast.makeText(this, "É necessário aceitar a permissão de localização!", Toast.LENGTH_LONG).show();
                requestPermission();
            }
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    private void getUserInfoFromFirebase() {
        final String userId = firebaseAuth.getUid();
        //int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.);
        usersReference.child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        if (dataSnapshot.exists()) {
                            User.getInstance().setNewCurrentUser(dataSnapshot);


                        } else {
                            /*new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("Something went wrong!")
                                    .show();*/

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("LOGIN", "getUser:onCancelled", databaseError.toException());
                        // ...
                    }
                });
    }

    private void instantiateFragments() {

        chatFragment = new ChatFragment();
        mapFragment = new MapFragment();
        menuFragment = new MenuFragment();

    }

    private void addFragments() {
        fragmentManager = getSupportFragmentManager();
    }


    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.action_chat:
                    fragment = chatFragment;
                    break;
                    //fragmentManager.beginTransaction().replace(R.id.fragment_container, chatFragment).commit();
                    //fragmentManager.beginTransaction().hide(menuFragment).hide(mapFragment).show(chatFragment).commit();
                case R.id.action_map:
                    fragment = mapFragment;
                    break;
                    //fragmentManager.beginTransaction().replace(R.id.fragment_container, mapFragment).commit();
                    //fragmentManager.beginTransaction().hide(menuFragment).hide(chatFragment).show(mapFragment).commit();
                case R.id.action_menu:
                    fragment = menuFragment;
                    break;
                    //fragmentManager.beginTransaction().hide(chatFragment).hide(mapFragment).show(menuFragment).commit();
            }

            return  loadFragment(fragment);

        }
    };

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.fragment_container, fragment).commit();
            return true;
        }

        return false;
    }

}
