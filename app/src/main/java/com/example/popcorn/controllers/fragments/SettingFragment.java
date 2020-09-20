package com.example.popcorn.controllers.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;


import com.example.popcorn.R;
import com.example.popcorn.controllers.activities.AuthActivity;


import static com.example.popcorn.R.string.dic_connected;


public class SettingFragment extends PreferenceFragmentCompat {

    public static final String NEW_LINK = "news";
    public static final String DARK_MODE = "switch_theme";
    public static final String LANGUAGE = "expandable";
    public static final String DISCONNECT = "switch_account";

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        //suscribing
        Preference key = findPreference("suscribing_key");
        key.getKey();
        //Toast.makeText(getContext(), "ANNNNNNNN", Toast.LENGTH_LONG).show();
        String url = "https://www.facebook.com";
        Uri uri = Uri.parse(url);
        Intent launchWeb = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(launchWeb);

        //Disconnected
        Preference keyDisconnect = findPreference("switch_account");
        keyDisconnect.getKey();
        Intent logoutIntent  = new Intent(getContext(), AuthActivity.class);
        startActivity(logoutIntent);
        Toast.makeText(getContext(), dic_connected, Toast.LENGTH_LONG).show();

        return true;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.main_preference, rootKey);

    }

}
