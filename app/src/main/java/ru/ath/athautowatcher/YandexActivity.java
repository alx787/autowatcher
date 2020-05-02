package ru.ath.athautowatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

public class YandexActivity extends AppCompatActivity {

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapKitFactory.setApiKey("");
        MapKitFactory.initialize(this);

        setContentView(R.layout.activity_yandex);


        Intent intent = getIntent();
        if (intent != null) {
//        if ((intent != null) && (intent.hasExtra("id"))) {
            String regnom = intent.getStringExtra("regnom");

            String sX = intent.getStringExtra("x");
            String sY = intent.getStringExtra("y");

            try {
                float fX = Float.valueOf(sX);
                float fY = Float.valueOf(sY);

                mapView = (MapView)findViewById(R.id.mapview);
                mapView.getMap().move(
                        new CameraPosition(new Point(fY, fX), 11.0f, 0.0f, 0.0f),
                        new Animation(Animation.Type.SMOOTH, 0),
                        null);
                mapView.getMap().getMapObjects().addPlacemark(new Point(fY, fX), ImageProvider.fromResource(this, R.drawable.map_marker_icon_32_32));

            } catch (Exception e) {
                finish();
            };

        } else {
            finish();
        }

    }

    @Override
    protected void onStop() {
        // Вызов onStop нужно передавать инстансам MapView и MapKit.
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        // Вызов onStart нужно передавать инстансам MapView и MapKit.
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }
}
