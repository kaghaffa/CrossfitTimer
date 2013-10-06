package com.example.crossfittimer;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Kayvon on 10/3/13.
 */
public class TimerFragment extends Fragment implements View.OnClickListener{


    private Handler handler = new Handler();

    private static final String KEY_POSITION = "position";
    private Button startStopButton;
    private Button resetButton;
    private TextView stopWatchTextView;
    private StopWatch stopWatch;

    static TimerFragment newInstance(int position) {
        TimerFragment fragment = new TimerFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.crossfit_timer_fragment, container, false);
        int position = getArguments().getInt(KEY_POSITION, -1);
        stopWatch = new StopWatch();

        assert result != null;
        stopWatchTextView = (TextView) result.findViewById(R.id.timer);
        startStopButton = (Button) result.findViewById(R.id.start_stop_button);
        resetButton = (Button) result.findViewById(R.id.reset_button);
        startStopButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        return result;
    }

    static String getTitle(Context context, int position) {
        return "Stopwatch";
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_stop_button:
                if (stopWatch.isRunning()) {
                    stopWatch.stop();
                    startStopButton.setText(R.string.start);
                    handler.removeCallbacks(updateTime);
                } else {
                    stopWatch.start();
                    startStopButton.setText(R.string.stop);
                    handler.postDelayed(updateTime, 0);
                }
                break;
            case R.id.reset_button:
                stopWatch.reset();
                stopWatchTextView.setText(stopWatch.getTimeAsString());
                break;
        }
    }

    private Runnable updateTime = new Runnable() {
        @Override
        public void run() {
            stopWatchTextView.setText(stopWatch.getTimeAsString());
            handler.postDelayed(this, 0);
        }
    };
}
