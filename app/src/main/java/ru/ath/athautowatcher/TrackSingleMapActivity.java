package ru.ath.athautowatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.LinearRing;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.Polygon;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PolygonMapObject;
import com.yandex.mapkit.map.SublayerManager;
import com.yandex.mapkit.mapview.MapView;

import java.util.ArrayList;

import ru.ath.athautowatcher.utils.Globals;

public class TrackSingleMapActivity extends AppCompatActivity {

    private MapView trackSingleMapview;
    private SublayerManager sublayerManager;
    private MapObjectCollection mapObjects;

    private final String MAPKIT_API_KEY = Globals.MAPKIT_API_KEY;
    private final Point CAMERA_TARGET = new Point(59.951029, 30.317181);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_single_map);

        trackSingleMapview = (MapView) findViewById(R.id.trackSingleMapview);

        trackSingleMapview.getMap().move(
                new CameraPosition(CAMERA_TARGET, 16.0f, 0.0f, 45.0f));

        sublayerManager = trackSingleMapview.getMap().getSublayerManager();
        mapObjects = trackSingleMapview.getMap().getMapObjects();


        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(59.949911, 30.316560));
        points.add(new Point(59.949121, 30.316008));
        points.add(new Point(59.949441, 30.318132));
        points.add(new Point(59.950075, 30.316915));
        points.add(new Point(59.949911, 30.316560));
        Polygon polygon = new Polygon(new LinearRing(points), new ArrayList<LinearRing>());
        final PolygonMapObject polygonMapObject = mapObjects.addPolygon(polygon);
        polygonMapObject.setFillColor(0x3300FF00);
        polygonMapObject.setStrokeWidth(3.0f);
        polygonMapObject.setStrokeColor(Color.GREEN);

    }

    @Override
    protected void onStop() {
        trackSingleMapview.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        trackSingleMapview.onStart();
    }
}
