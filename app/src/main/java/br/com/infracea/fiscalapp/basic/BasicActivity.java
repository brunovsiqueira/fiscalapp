package br.com.infracea.fiscalapp.basic;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class BasicActivity extends AppCompatActivity {

    private String TAG = getClass().getName().toString();

    @Override
    protected void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("foreground", true).commit();
        Log.d(TAG, "foreground = " + PreferenceManager.getDefaultSharedPreferences(this).getBoolean("foreground", false));
    }

    @Override
    protected void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("foreground", false).commit();
        Log.d(TAG, "foreground = " + PreferenceManager.getDefaultSharedPreferences(this).getBoolean("foreground", false));
    }

}
