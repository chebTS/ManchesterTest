package ua.ck.manchester.activities;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import ua.ck.manchester.R;
import ua.ck.manchester.fragments.TouchableMapFragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

public class MainActivity extends LocationActivity implements OnMapReadyCallback {

    private TouchableMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mapFragment = new TouchableMapFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mapContainer, mapFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
