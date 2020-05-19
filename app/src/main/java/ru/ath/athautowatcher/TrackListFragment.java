package ru.ath.athautowatcher;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.service.autofill.TextValueSanitizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

        int cnt = 0;
        for (TrackElement oneTrack : trackArr) {
            cnt++;
            linearLayoutWrapper.addView(getCardViewTrack(cnt, oneTrack.getDatebeg(), oneTrack.getDateend(), oneTrack.getDuration(), oneTrack.getMotohours(), oneTrack.getProbeg(), oneTrack.getFuelrate()), lParams);
        }

        return rootView;
    }


    ///////////////////////////////////////
    // создаем и наполняем элемент cardView
    ///////////////////////////////////////
    private CardView getCardViewTrack(int cnt, final String datebeg, final String dateend, String duration, String motohours, String probeg, String fuelRate) {
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        CardView cardViewTrack = new CardView(getActivity());
        cardViewTrack.setCardBackgroundColor(ContextCompat.getColor(this.getActivity(), R.color.colorCardBackground));

        LinearLayout linearLayoutTrack = (LinearLayout) new LinearLayout(getActivity());
        linearLayoutTrack.setOrientation(LinearLayout.VERTICAL);


        TextView textViewPeriod = new TextView(getActivity());
        textViewPeriod.setText(String.valueOf(cnt) + ". с " + datebeg + " по " + dateend);
        textViewPeriod.setTextColor(ContextCompat.getColor(this.getActivity(), R.color.colorTextPrimary));
        textViewPeriod.setBackgroundColor(ContextCompat.getColor(this.getActivity(), R.color.colorCardTitle));
        textViewPeriod.setPadding(10, 5, 0, 5);



        TextView textViewDuration = new TextView(getActivity());
        textViewDuration.setText("длительность: " + duration);
        textViewDuration.setTextColor(ContextCompat.getColor(this.getActivity(), R.color.colorTextPrimary));
        textViewDuration.setPadding(10, 0, 0, 0);

        TextView textViewMotohours = new TextView(getActivity());
        textViewMotohours.setText("моточасы: " + motohours);
        textViewMotohours.setTextColor(ContextCompat.getColor(this.getActivity(), R.color.colorTextPrimary));
        textViewMotohours.setPadding(10, 0, 0, 0);

        TextView textViewProbeg = new TextView(getActivity());
        textViewProbeg.setText("пробег: " + probeg);
        textViewProbeg.setTextColor(ContextCompat.getColor(this.getActivity(), R.color.colorTextPrimary));
        textViewProbeg.setPadding(10, 0, 0, 0);

        TextView textViewRashod = new TextView(getActivity());
        textViewRashod.setText("расход топлива по нормам: " + fuelRate);
        textViewRashod.setTextColor(ContextCompat.getColor(this.getActivity(), R.color.colorTextPrimary));
        textViewRashod.setPadding(10, 0, 0, 0);

        cardViewTrack.addView(linearLayoutTrack, lParams);
        linearLayoutTrack.addView(textViewPeriod, lParams);
        linearLayoutTrack.addView(textViewDuration, lParams);
        linearLayoutTrack.addView(textViewMotohours, lParams);
        linearLayoutTrack.addView(textViewProbeg, lParams);
        linearLayoutTrack.addView(textViewRashod, lParams);

        cardViewTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "c " + datebeg + " по " + dateend, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), TrackSingleMapActivity.class);
                startActivity(intent);
            }
        });

        return cardViewTrack;

    }
}
