package com.example.crossfittimer;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Kayvon on 10/3/13.
 */
public class MainAdapter extends FragmentPagerAdapter {

    Context context = null;

    public MainAdapter(Context context, FragmentManager manager) {
        super(manager);
        this.context = context;
    }

    @Override
    public int getCount() {
        return(3);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TimerFragment.newInstance(position);
            case 1:
                return EmomFragment.newInstance(position);
            case 2:
                return TabataFragment.newInstance(position);
        }
        return null;
    }

    @Override
    public String getPageTitle(int position) {
        return(TimerFragment.getTitle(context, position));
    }
}
