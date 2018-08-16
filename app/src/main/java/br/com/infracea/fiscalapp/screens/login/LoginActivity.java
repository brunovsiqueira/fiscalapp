package br.com.infracea.fiscalapp.screens.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.infracea.fiscalapp.R;
import br.com.infracea.fiscalapp.basic.BasicActivity;
import br.com.infracea.fiscalapp.models.User;
import br.com.infracea.fiscalapp.screens.container.ContainerActivity;

public class LoginActivity extends BasicActivity {

    private EditText userEmail;
    private EditText userPassword;
    private Button loginButton;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference().child("users");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewItems();

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("fiscalapp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("LOGIN", true);
        editor.commit();

    }

    private void findViewItems() {

        userEmail = findViewById(R.id.login_user_mail);
        userPassword = findViewById(R.id.login_user_password);
        loginButton = findViewById(R.id.login_enter);

        loginButton.setOnClickListener(loginClickListener);

    }


    View.OnClickListener loginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //show progress bar
            firebaseAuth.signInWithEmailAndPassword(userEmail.getText().toString(), userPassword.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //getUserInfoFromFirebase();
                                Intent intentToContainer = new Intent(LoginActivity.this, ContainerActivity.class);
                                intentToContainer.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intentToContainer);
                                Toast.makeText(LoginActivity.this, "Login efetuado com sucesso", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(LoginActivity.this, "Email ou senha incorretos", Toast.LENGTH_SHORT).show();
                                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                                    FirebaseAuth.getInstance().signOut();
                                    loginButton.setVisibility(View.VISIBLE);

                                }
                            }

                            //dismiss progress bar
                        }
                    });


        }
    };

}
