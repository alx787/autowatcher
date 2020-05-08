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

import java.util.Random;


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





        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout linearLayoutWrapper = (LinearLayout) rootView.findViewById((R.id.linearLayoutWrapper));


        for (int i = 1; i <= 10; i++) {
            linearLayoutWrapper.addView(getCardViewTrack(), lParams);
        }



        return rootView;
    }


    ///////////////////////////////////////
    // создаем и наполняем элемент cardView
    ///////////////////////////////////////
    private CardView getCardViewTrack() {
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        CardView cardViewTrack = new CardView(getActivity());

        LinearLayout linearLayoutTrack = (LinearLayout) new LinearLayout(getActivity());
        linearLayoutTrack.setOrientation(LinearLayout.VERTICAL);


        TextView textViewPeriod = new TextView(getActivity());
        textViewPeriod.setText("Период пробега ___");

        TextView textViewDuration = new TextView(getActivity());
        textViewDuration.setText("Длительность ___");

        TextView textViewMotohours = new TextView(getActivity());
        textViewMotohours.setText("Моточасы ___");

        TextView textViewProbeg = new TextView(getActivity());
        textViewProbeg.setText("Пробег ___");

        TextView textViewRashod = new TextView(getActivity());
        textViewRashod.setText("Расход топлива по нормам ___");

        cardViewTrack.addView(linearLayoutTrack, lParams);
        linearLayoutTrack.addView(textViewPeriod, lParams);
        linearLayoutTrack.addView(textViewDuration, lParams);
        linearLayoutTrack.addView(textViewMotohours, lParams);
        linearLayoutTrack.addView(textViewProbeg, lParams);
        linearLayoutTrack.addView(textViewRashod, lParams);

        return cardViewTrack;

    }
}
