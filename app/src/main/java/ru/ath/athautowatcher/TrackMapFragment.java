package ru.ath.athautowatcher;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import ru.ath.athautowatcher.utils.Globals;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrackMapFragment extends Fragment {

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




        double fX = 49.3;
        double fY = 58.3;


        MapView mapView = (MapView)rootView.findViewById(R.id.mapviewTrack);
        mapView.getMap().move(
                new CameraPosition(new Point(fY, fX), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
        mapView.getMap().getMapObjects().addPlacemark(new Point(fY, fX), ImageProvider.fromResource(this.getActivity(), R.drawable.map_marker_icon_32_32));





        return inflater.inflate(R.layout.fragment_track_map, container, false);
    }
}
