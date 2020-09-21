package com.example.popcorn.controllers.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;


import com.example.popcorn.R;
import com.example.popcorn.controllers.activities.AuthActivity;
import com.example.popcorn.controllers.fragments.LoginFragment;

import static com.example.popcorn.R.string.dic_connected;


public class SettingFragment extends PreferenceFragmentCompat {

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public boolean onPreferenceTreeClick(Preference preference) {

        Preference keySurbcribing = findPreference("suscribing_key");
        keySurbcribing.getKey();

        Preference keyDisconnect = findPreference("switch_account");
        keyDisconnect.getKey();
       if(preference == keySurbcribing ){
           //suscribing
           String url = "https://www.pinterest.fr/login/";
           Uri uri = Uri.parse(url);
           Intent launchWeb = new Intent(Intent.ACTION_VIEW,uri);
           startActivity(launchWeb);

       }if(preference == keyDisconnect ){
           //Disconnected
            LoginFragment.logout();
            Intent logoutIntent  = new Intent(getContext(), AuthActivity.class);
            startActivity(logoutIntent);
            Toast.makeText(getContext(), dic_connected, Toast.LENGTH_LONG).show();

       }
        return true;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.main_preference, rootKey);

    }

}
