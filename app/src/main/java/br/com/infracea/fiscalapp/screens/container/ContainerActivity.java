package br.com.infracea.fiscalapp.screens.container;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
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

public class ContainerActivity extends BasicActivity {

    private static final int RC_SIGN_IN = 123;

    private BottomNavigationView bottomNavigationView;
    private android.support.v4.app.FragmentManager fragmentManager;

    private ChatFragment chatFragment;
    private MapFragment mapFragment;
    private MenuFragment menuFragment;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference().child("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        instantiateFragments();
        addFragments();

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        //attach firebase listener
//        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //detach firebase listener
//        if (authStateListener != null) {
//            firebaseAuth.removeAuthStateListener(authStateListener);
//        }
    }

    private void findViewItems() {

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        //bottomNavigationView.
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
