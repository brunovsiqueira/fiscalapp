package br.com.infracea.fiscalapp.screens.container;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import br.com.infracea.fiscalapp.R;
import br.com.infracea.fiscalapp.basic.BasicActivity;
import br.com.infracea.fiscalapp.screens.container.chat.ChatFragment;
import br.com.infracea.fiscalapp.screens.container.map.MapFragment;
import br.com.infracea.fiscalapp.screens.container.menu.MenuFragment;

public class ContainerActivity extends BasicActivity {

    private BottomNavigationView bottomNavigationView;
    private android.support.v4.app.FragmentManager fragmentManager;

    private ChatFragment chatFragment;
    private MapFragment mapFragment;
    private MenuFragment menuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        instantiateFragments();
        addFragments();

        findViewItems();
        loadFragment(chatFragment); //default fragment
    }

    private void findViewItems() {

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        //bottomNavigationView.
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
