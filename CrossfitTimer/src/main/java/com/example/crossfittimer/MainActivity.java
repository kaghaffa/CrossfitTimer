package com.example.crossfittimer;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;


public class MainActivity extends FragmentActivity
        implements TabListener, OnPageChangeListener {

    private static final String KEY_POSITION = "position";
    private static ViewPager pager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crossfit_timer_main);

        pager = (ViewPager) findViewById(R.id.crossfit_timer_pager);
        pager.setAdapter(new MainAdapter(this, getSupportFragmentManager()));
        pager.setOnPageChangeListener(this);

        ActionBar bar = getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        bar.addTab(bar.newTab()
                .setText("Stopwatch")
                .setTabListener(this).setTag(0));
        bar.addTab(bar.newTab()
                .setText("EMOM")
                .setTabListener(this).setTag(1));
        bar.addTab(bar.newTab()
                .setText("Tabata")
                .setTabListener(this).setTag(2));
    }


    @Override
    public void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        pager.setCurrentItem(state.getInt(KEY_POSITION));
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putInt(KEY_POSITION, pager.getCurrentItem());
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        Integer position = (Integer) tab.getTag();
        pager.setCurrentItem(position);
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
        // noop
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        // noop
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // noop
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // noop
    }

    @Override
    public void onPageSelected(int position) {
        getActionBar().setSelectedNavigationItem(position);
    }
}
