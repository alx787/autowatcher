package ru.ath.athautowatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.Polyline;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.PolylineMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.ui_view.ViewProvider;

import java.util.ArrayList;
import java.util.List;

import ru.ath.athautowatcher.utils.Globals;
import ru.ath.athautowatcher.utils.NetworkUtils;
import ru.ath.athautowatcher.utils.TrackElement;


public class TrackSingleMapActivity extends AppCompatActivity {

    private MapView trackSingleMapview;
    private MapObjectCollection mapObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapKitFactory.setApiKey(Globals.MAPKIT_API_KEY);
        MapKitFactory.initialize(this);

        setContentView(R.layout.activity_track_single_map);
        super.onCreate(savedInstanceState);

        setTitle("Пробег подробно");

        trackSingleMapview = (MapView)findViewById(R.id.trackSingleMapview);
        mapObjects = trackSingleMapview.getMap().getMapObjects().addCollection();

        Intent intent = getIntent();
        if (intent != null) {
            String invnom = null;
            String datebeg = null;
            String dateend = null;

            if (intent.hasExtra("invnom")) {
                invnom = intent.getStringExtra("invnom");
                if (invnom.isEmpty()) {
                    invnom = null;
                }
            }

            if (intent.hasExtra("datebeg")) {
                datebeg = intent.getStringExtra("datebeg");
                if (datebeg.isEmpty()) {
                    datebeg = null;
                }
            }

            if (intent.hasExtra("dateend")) {
                dateend = intent.getStringExtra("dateend");
                if (dateend.isEmpty()) {
                    dateend = null;
                }
            }

            if (invnom == null || datebeg == null || dateend == null) {
                Toast.makeText(this, "Ошибка при передаче параметров", Toast.LENGTH_SHORT).show();
                trackSingleMapview.getMap().move(new CameraPosition(new Point(Globals.ATH_LAT, Globals.ATH_LON), 15.0f, 0.0f, 0.0f));

                return;
            }

            JsonObject jsonObject = NetworkUtils.getJsonDetailTrack(this, invnom, datebeg, dateend);
            if (jsonObject != null) {
                JsonArray contentArr = jsonObject.get("content").getAsJsonArray();

                // используем вспомогательный объект
                List<TrackElement> trackElements = new ArrayList<>();

                for  (int i = 0; i < contentArr.size(); i++) {
                    JsonObject message = contentArr.get(i).getAsJsonObject();

                    TrackElement trackElement = new TrackElement();
                    trackElement.setTrackbegx(message.get("x").getAsString());
                    trackElement.setTrackbegy(message.get("y").getAsString());
                    trackElement.setMaxspeed(message.get("speed").getAsString());
                    trackElement.setTracktime(message.get("time").getAsString());

                    trackElements.add(trackElement);
                }

                if (trackElements.size() > 0) {
                    createMapObjects(trackElements);
                } else {
                    Toast.makeText(this, "Нет данных по пробегу", Toast.LENGTH_SHORT).show();
                    trackSingleMapview.getMap().move(new CameraPosition(new Point(Globals.ATH_LAT, Globals.ATH_LON), 15.0f, 0.0f, 0.0f));
                    return;
                }
            }
        }
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

    private void createMapObjects(List<TrackElement> trackElements) {

//        "y":<double>,		/* широта */  lat
//        "x":<double>,		/* долгота */ long

        ArrayList<Point> polylinePoints = new ArrayList<>();

        double dX = 0; // lon долгота
        double dY = 0; // lat широта

        int i = 0;

        for (TrackElement oneTrack : trackElements) {
            try {
                dX = Double.valueOf(oneTrack.getTrackbegx());
                dY = Double.valueOf(oneTrack.getTrackbegy());
            } catch (Exception e) {
                continue;
            }

            polylinePoints.add(new Point(dY, dX));


            // расставим временные метки
            if (i == 0) {
                setPointOnMap(oneTrack.getTracktime(), dX, dY);
            }

            i++;

            if (i > 100) {
                i = 0;
            }
        }

        // метку на последней точке
        setPointOnMap(trackElements.get(trackElements.size() - 1).getTracktime(), dX, dY);


        PolylineMapObject polyline = mapObjects.addPolyline(new Polyline(polylinePoints));
//        Log.i("myres", String.valueOf(polyline.getStrokeWidth()));
        polyline.setStrokeWidth(3.0f);
        polyline.setStrokeColor(Color.GREEN);
        polyline.setZIndex(100.0f);

        trackSingleMapview.getMap().move(new CameraPosition(new Point(dY, dX), 15.0f, 0.0f, 0.0f));

    }

    private void setPointOnMap(String period, double dX, double dY) {

//        double dX = 0; // lon долгота
//        double dY = 0; // lat широта

//        try {
//            dX = Double.valueOf(sX);
//            dY = Double.valueOf(sY);
//        } catch (Exception e) {
//            return;
//        }

//        Point(double lat, double long)

        trackSingleMapview.getMap().getMapObjects().addPlacemark(new Point(dY, dX), ImageProvider.fromResource(this, R.drawable.map_marker_icon_32_32));


        /////////////////////////////////////////////////
        /////////////////////////////////////////////////
        final TextView textView = new TextView(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);

        textView.setTextColor(Color.RED);
        textView.setText(period + "\n\n\n\n\n");

        final ViewProvider viewProvider = new ViewProvider(textView);
        final PlacemarkMapObject viewPlacemark = mapObjects.addPlacemark(new Point(dY, dX), viewProvider);
        viewPlacemark.setView(viewProvider);

    }

}
