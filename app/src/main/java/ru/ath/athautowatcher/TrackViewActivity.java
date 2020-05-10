package ru.ath.athautowatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import ru.ath.athautowatcher.utils.NetworkUtils;
import ru.ath.athautowatcher.utils.TrackElement;

public class TrackViewActivity extends AppCompatActivity {

    private TabLayout tabLayoutTrack;
    private ViewPager viewPagerTrack;
    private TabItem tabItemList, tabItemMap;
    private MyPagerAdapter pagerAdapter;

    // записи треков
    private ArrayList<TrackElement> trackArr;
    // переменные для хранения итоговых данных
    private String duration;
    private String fuelrate;
    private String motohours;
    private String probeg;
    private String period;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_view);

        tabLayoutTrack = (TabLayout) findViewById(R.id.tabLayoutTrack);
        viewPagerTrack = (ViewPager) findViewById(R.id.viewPagertrack);
        tabItemList = (TabItem) findViewById(R.id.tabItemList);
        tabItemMap = (TabItem) findViewById(R.id.tabItemMap);

        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, tabLayoutTrack.getTabCount());
        viewPagerTrack.setAdapter(pagerAdapter);

        tabLayoutTrack.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerTrack.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPagerTrack.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutTrack));


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
                finish();
            }

            //////////////////////////////////
            // запрос на сервер

            boolean wasError = false; // для проверки наличия ошибки

            JsonObject jsonObject = NetworkUtils.getJsonTracks(invnom, datebeg, dateend);
            if (jsonObject == null) {
                Toast.makeText(this, "Ошибка, не получен ответ от сервера, пробеги не загружены", Toast.LENGTH_SHORT).show();
                wasError = true;
            }

            //Log.i("myres", jsonObject.toString());

            if (!wasError && jsonObject.has("status")) {
                if (jsonObject.get("status").getAsString().equals("error")) {
                    Toast.makeText(this , "Ошибка, ошибка в ответе сервера, пробеги не загружены", Toast.LENGTH_SHORT).show();
                    wasError = true;
                }
            } else {
                Toast.makeText(this, "Ошибка, нет данных, пробеги не загружены", Toast.LENGTH_SHORT).show();
                wasError = true;
            }

            if (!wasError && !jsonObject.has("content")) {
                Toast.makeText(this, "Информация о пробегах не обнаружена", Toast.LENGTH_SHORT).show();
                wasError = true;
            }


            if (!wasError) {
                // тут разбор json ответа
                JsonObject contentJsonObj = jsonObject.get("content").getAsJsonObject();

                duration = "длительность: " + contentJsonObj.get("duration").getAsString();
                fuelrate = "расход топлива по нормам: " + contentJsonObj.get("fuelrate").getAsString();;
                motohours = "моточасы: " + contentJsonObj.get("motohours").getAsString();
                probeg = "пробег: " + contentJsonObj.get("probeg").getAsString();
                period = datebeg + " - " + dateend;

                trackArr = new ArrayList<TrackElement>();

                JsonArray detailJsonArr = contentJsonObj.get("detail").getAsJsonArray();
                int cntArr = detailJsonArr.size();
                for (int i = 0; i < cntArr; i++) {
                    JsonObject trackJsonObj = detailJsonArr.get(i).getAsJsonObject();

                    TrackElement trackElement = new TrackElement();
                    trackElement.setDatebeg(trackJsonObj.get("datebeg").getAsString());
                    trackElement.setDateend(trackJsonObj.get("dateend").getAsString());
                    trackElement.setDuration(trackJsonObj.get("duration").getAsString());
                    trackElement.setFuelavgrate(trackJsonObj.get("fuelavgrate").getAsString());
                    trackElement.setFuelrate(trackJsonObj.get("fuelrate").getAsString());
                    trackElement.setMaxspeed(trackJsonObj.get("maxspeed").getAsString());
                    trackElement.setMaxspeedx(trackJsonObj.get("maxspeedx").getAsString());
                    trackElement.setMaxspeedy(trackJsonObj.get("maxspeedy").getAsString());
                    trackElement.setMotohours(trackJsonObj.get("motohours").getAsString());
                    trackElement.setPlacebeg(trackJsonObj.get("placebeg").getAsString());
                    trackElement.setPlacebegx(trackJsonObj.get("placebegx").getAsString());
                    trackElement.setPlacebegy(trackJsonObj.get("placebegy").getAsString());
                    trackElement.setPlaceend(trackJsonObj.get("placeend").getAsString());
                    trackElement.setPlaceendx(trackJsonObj.get("placeendx").getAsString());
                    trackElement.setPlaceendy(trackJsonObj.get("placeendy").getAsString());
                    trackElement.setProbeg(trackJsonObj.get("probeg").getAsString());
                    trackElement.setTrackbegtime(trackJsonObj.get("trackbegtime").getAsString());
                    trackElement.setTrackbegx(trackJsonObj.get("trackbegx").getAsString());
                    trackElement.setTrackbegy(trackJsonObj.get("trackbegy").getAsString());
                    trackElement.setTrackendtime(trackJsonObj.get("trackendtime").getAsString());
                    trackElement.setTrackendx(trackJsonObj.get("trackendx").getAsString());
                    trackElement.setTrackendy(trackJsonObj.get("trackendy").getAsString());
                    trackElement.setTracktime(trackJsonObj.get("tracktime").getAsString());

                    trackArr.add(trackElement);
                }
            } else {
                duration = "";
                fuelrate = "";
                motohours = "";
                probeg = "";
                period = "";

                trackArr = new ArrayList<TrackElement>();

            }

        }

    }

    public ArrayList<TrackElement> getTrackArr() {
        return trackArr;
    }

    public String getDuration() {
        return duration;
    }

    public String getFuelrate() {
        return fuelrate;
    }

    public String getMotohours() {
        return motohours;
    }

    public String getProbeg() {
        return probeg;
    }

    public String getPeriod() {
        return period;
    }
}
