package ru.ath.athautowatcher;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.service.autofill.TextValueSanitizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import ru.ath.athautowatcher.utils.TrackElement;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrackListFragment extends Fragment {

    public TrackListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_track_list, container, false);

        TextView textViewElem = (TextView) rootView.findViewById(R.id.textViewPeriodTotal);
        textViewElem.setText(((TrackViewActivity) getActivity()).getPeriod());

        textViewElem = (TextView) rootView.findViewById(R.id.textViewDurationTotal);
        textViewElem.setText(((TrackViewActivity) getActivity()).getDuration());

        textViewElem = (TextView) rootView.findViewById(R.id.textViewMotohoursTotal);
        textViewElem.setText(((TrackViewActivity) getActivity()).getMotohours());

        textViewElem = (TextView) rootView.findViewById(R.id.textViewProbegTotal);
        textViewElem.setText(((TrackViewActivity) getActivity()).getProbeg());

        textViewElem = (TextView) rootView.findViewById(R.id.textViewFuelRateTotal);
        textViewElem.setText(((TrackViewActivity) getActivity()).getFuelrate());


        // параметры выравнивания для LinearLayout
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // добавляем элементы в макет в дочерние этого
        LinearLayout linearLayoutWrapper = (LinearLayout) rootView.findViewById((R.id.linearLayoutWrapper));


        List<TrackElement> trackArr = ((TrackViewActivity) getActivity()).getTrackArr();

        for (TrackElement oneTrack : trackArr) {
            linearLayoutWrapper.addView(getCardViewTrack(oneTrack.getDatebeg(), oneTrack.getDateend(), oneTrack.getDuration(), oneTrack.getMotohours(), oneTrack.getProbeg(), oneTrack.getFuelrate()), lParams);
        }

        return rootView;
    }


    ///////////////////////////////////////
    // создаем и наполняем элемент cardView
    ///////////////////////////////////////
    private CardView getCardViewTrack(String datebeg, String dateend, String duration, String motohours,String probeg, String fuelRate) {
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        CardView cardViewTrack = new CardView(getActivity());

        LinearLayout linearLayoutTrack = (LinearLayout) new LinearLayout(getActivity());
        linearLayoutTrack.setOrientation(LinearLayout.VERTICAL);


        TextView textViewPeriod = new TextView(getActivity());
        textViewPeriod.setText("с " + datebeg + " по " + dateend);

        TextView textViewDuration = new TextView(getActivity());
        textViewDuration.setText("длительность: " + duration);

        TextView textViewMotohours = new TextView(getActivity());
        textViewMotohours.setText("моточасы: " + motohours);

        TextView textViewProbeg = new TextView(getActivity());
        textViewProbeg.setText("пробег: " + probeg);

        TextView textViewRashod = new TextView(getActivity());
        textViewRashod.setText("расход топлива по нормам: " + fuelRate);

        cardViewTrack.addView(linearLayoutTrack, lParams);
        linearLayoutTrack.addView(textViewPeriod, lParams);
        linearLayoutTrack.addView(textViewDuration, lParams);
        linearLayoutTrack.addView(textViewMotohours, lParams);
        linearLayoutTrack.addView(textViewProbeg, lParams);
        linearLayoutTrack.addView(textViewRashod, lParams);

        return cardViewTrack;

    }
}
