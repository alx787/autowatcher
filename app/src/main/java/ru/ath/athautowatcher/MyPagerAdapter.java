package ru.ath.athautowatcher;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {
    // количество вкладок
    private int tabscnt;

    public MyPagerAdapter(@NonNull FragmentManager fm, int behavior, int tabscnt) {
        super(fm, behavior);
        this.tabscnt = tabscnt;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TrackListFragment();
            case 1:
                return new TrackMapFragment();
            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return tabscnt;
    }
}
