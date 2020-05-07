package ru.ath.athautowatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class TrackViewActivity extends AppCompatActivity {

    private TabLayout tabLayoutTrack;
    private ViewPager viewPagerTrack;
    private TabItem tabItemList, tabItemMap;
    private MyPagerAdapter pagerAdapter;

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
    }
}
