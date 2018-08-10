package br.com.infracea.fiscalapp.screens.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import br.com.infracea.fiscalapp.R;
import br.com.infracea.fiscalapp.basic.BasicActivity;

public class HomeActivity extends BasicActivity {

    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewItems();

    }

    private void findViewItems() {

        loginButton = findViewById(R.id.home_login_button);
        registerButton = findViewById(R.id.home_register_button);

        loginButton.setOnClickListener(loginClickListener);
        registerButton.setOnClickListener(registerClickListener);

    }

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
