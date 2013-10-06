package com.example.crossfittimer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kayvon on 10/3/13.
 */
public class TabataFragment extends Fragment {
    private static final String KEY_POSITION = "position";

    static TabataFragment newInstance(int position) {
        TabataFragment fragment = new TabataFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.crossfit_tabata_fragment, container, false);


        return result;
    }

    static String getTitle(Context context, int position) {
        return "Tabata";
    }

}
