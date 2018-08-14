package br.com.infracea.fiscalapp.screens.container.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import br.com.infracea.fiscalapp.R;
import br.com.infracea.fiscalapp.screens.container.ContainerActivity;
import br.com.infracea.fiscalapp.screens.login.HomeActivity;

public class MenuFragment extends Fragment {

    private View view;
    private TextView toolbarTitle;
    private Button logoutButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_menu, container, false);

        findViewItems(view);

        return view;
    }

    private void findViewItems(View view) {

        toolbarTitle = view.findViewById(R.id.toolbar_container_title);
        logoutButton = view.findViewById(R.id.menu_logout);

        toolbarTitle.setText("MENU");
        logoutButton.setOnClickListener(logoutClickListener);
    }

    View.OnClickListener logoutClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                FirebaseAuth.getInstance().signOut();
                Intent intentToHome = new Intent(getContext(), HomeActivity.class);
                intentToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);;
                startActivity(intentToHome);
            }

        }
    };

}
