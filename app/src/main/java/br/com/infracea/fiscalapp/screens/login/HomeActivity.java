package br.com.infracea.fiscalapp.screens.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.infracea.fiscalapp.R;
import br.com.infracea.fiscalapp.basic.BasicActivity;
import br.com.infracea.fiscalapp.screens.container.ContainerActivity;

public class HomeActivity extends BasicActivity {

    private Button loginButton;
    private Button registerButton;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        firebaseAuth.addAuthStateListener(authStateListener);
        findViewItems();
        //progressBar.setVisibility(View.VISIBLE);


    }

    @Override
    protected void onResume() {
        super.onResume();

        firebaseAuth.addAuthStateListener(authStateListener);

    }

    @Override
    protected void onPause() {
        super.onPause();

        firebaseAuth.removeAuthStateListener(authStateListener);

    }

    private void findViewItems() {

        loginButton = findViewById(R.id.home_login_button);
        registerButton = findViewById(R.id.home_register_button);
        progressBar = findViewById(R.id.home_progress_bar);

        loginButton.setOnClickListener(loginClickListener);
        registerButton.setOnClickListener(registerClickListener);

    }

    FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();

            if (user != null) {
                //user is signed in, proceed
                //INTENT TO CONTAINER
                startActivity(new Intent(HomeActivity.this, ContainerActivity.class));
                finish();
                Toast.makeText(HomeActivity.this, "Login efetuado", Toast.LENGTH_SHORT).show();
                //onSignedInInitialize(user.getDisplayName());
            } else {
                //onSignedOutCleanup();
                //user is signed out, show login screen

            }

            progressBar.setVisibility(View.GONE);

        }
    };

    View.OnClickListener loginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    };

    View.OnClickListener registerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        }
    };

}
