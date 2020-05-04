package ru.ath.athautowatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

// import ru.ath.athautowatcher.utils.NetworkUtils;

public class GoogleActivity extends AppCompatActivity implements OnMapReadyCallback{

    private Float   fX;
    private Float fY;
    private String regnom;

//    private GoogleMap map;

    private MapView mapViewPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);


        mapViewPlace = (MapView) findViewById(R.id.mapViewPlace);
        mapViewPlace.onCreate(savedInstanceState);

        mapViewPlace.getMapAsync(this);

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap googleMap) {
//                map = googleMap;
//
//                map.getUiSettings().setZoomControlsEnabled(true);
//
//
//                LatLng latLng = new LatLng(58.60, 49.66);
//                map.addMarker(new MarkerOptions().position(latLng).title("Kirov"));
//                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));
//            }
//        });


        Intent intent = getIntent();
        if (intent != null) {
//        if ((intent != null) && (intent.hasExtra("id"))) {
            regnom = intent.getStringExtra("regnom");

            String sX = intent.getStringExtra("x");
            String sY = intent.getStringExtra("y");

            try {
                fX = Float.valueOf(sX);
                fY = Float.valueOf(sY);
            } catch (Exception e) {
                finish();
            };

        } else {
            finish();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //        "y":<double>,         /* широта */ lat
        //        "x":<double>,         /* долгота */ long
        LatLng latLng = new LatLng(fY, fX);

        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setMapToolbarEnabled(true);


        googleMap.addMarker(new MarkerOptions().position(latLng).title(regnom));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapViewPlace.onResume();
    }

    @Override
    protected void onPause() {
        mapViewPlace.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapViewPlace.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapViewPlace.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapViewPlace.onSaveInstanceState(outState);
    }
}

