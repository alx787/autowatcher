package ru.ath.athautowatcher;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.ui_view.ViewProvider;

import java.util.List;
import java.util.Random;

import ru.ath.athautowatcher.utils.Globals;
import ru.ath.athautowatcher.utils.TrackElement;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrackMapFragment extends Fragment {

    private MapView mapView;
    private MapObjectCollection mapObjects;

    public TrackMapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        MapKitFactory.setApiKey(Globals.MAPKIT_API_KEY);
        MapKitFactory.initialize(this.getActivity());

        View rootView = inflater.inflate(R.layout.fragment_track_map, container, false);

        mapView = (MapView)rootView.findViewById(R.id.mapviewTrack);
        mapObjects = mapView.getMap().getMapObjects().addCollection();


        List<TrackElement> trackArr = ((TrackViewActivity) getActivity()).getTrackArr();

        double dLastX = 0; // lon долгота
        double dLastY = 0; // lat широта

        if (trackArr.size() > 0) {
            try {
                dLastX = Double.valueOf(trackArr.get(trackArr.size() - 1).getTrackbegx());
                dLastY = Double.valueOf(trackArr.get(trackArr.size() - 1).getTrackbegy());
            } catch (Exception e) {

            }
        }

        int cnt = 0;
        for (TrackElement oneTrack : trackArr) {
            cnt++;
            setPointOnMap(String.valueOf(cnt) + ". "+ oneTrack.getDatebeg() + "\n\n\n\n\n", oneTrack.getTrackbegx(), oneTrack.getTrackbegy());
        }


        mapView.getMap().move(
                new CameraPosition(new Point(dLastY, dLastX), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);

        return rootView;
    }


    private void setPointOnMap(String period, String sX, String sY) {

        double dX = 0; // lon долгота
        double dY = 0; // lat широта

        try {
            dX = Double.valueOf(sX);
            dY = Double.valueOf(sY);
        } catch (Exception e) {
            return;
        }

//        Point(double lat, double long)

        mapView.getMap().getMapObjects().addPlacemark(new Point(dY, dX), ImageProvider.fromResource(this.getActivity(), R.drawable.map_marker_icon_32_32));


        /////////////////////////////////////////////////
        /////////////////////////////////////////////////
        final TextView textView = new TextView(this.getActivity());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);

        textView.setTextColor(Color.RED);
        textView.setText(period);

        final ViewProvider viewProvider = new ViewProvider(textView);
        final PlacemarkMapObject viewPlacemark = mapObjects.addPlacemark(new Point(dY, dX), viewProvider);
        viewPlacemark.setView(viewProvider);

    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
        MapKitFactory.getInstance().onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
    }
}
