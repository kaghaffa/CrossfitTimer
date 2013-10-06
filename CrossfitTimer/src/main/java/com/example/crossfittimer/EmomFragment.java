package com.example.crossfittimer;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Kayvon on 10/3/13.
 */
public class EmomFragment extends Fragment implements View.OnClickListener {


    private static final String KEY_POSITION = "position";
    private static final String STATE_TIMER = "timer";

    private static int numRounds = 0;
    private static long timeRemaining = 0;
    private static boolean isTimerStarted = false;
    private static CountDownTimer countDownTimer;

    private TextView emomTitle;
    private Button numpad0, numpad1, numpad2, numpad3, numpad4, numpad5, numpad6, numpad7, numpad8, numpad9;
    private ArrayList<Button> numpadButtons = new ArrayList<Button>();
    private Button backspace;
    private Button start;
    private TextView timer;

    static EmomFragment newInstance(int position) {
        EmomFragment fragment = new EmomFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.crossfit_emom_fragment, container, false);

        initUI(result);

        GridLayout numpadLayout = (GridLayout) result.findViewById(R.id.numpad_layout);

        for (int i = 0; i < numpadLayout.getChildCount(); i++) {
            View currView = numpadLayout.getChildAt(i);
            Class currViewClass = currView.getClass();
            if (currViewClass == Button.class) {
                currView.setOnClickListener(this);
                numpadButtons.add((Button) currView);
            }
        }

        final Button startButton = (Button) result.findViewById(R.id.start_stop_button);

        startButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numRounds == 0) {
                    return;
                }

                // TODO no magic numbers
                if (timeRemaining == 0 && !isTimerStarted) {
                    timeRemaining = numRounds * 60 * 1000;
                }

                if (isTimerStarted) {

                    // Stop button pressed
                    countDownTimer.onFinish();
                    isTimerStarted = false;
                    timeRemaining = 0;
                    timer.setText(R.string.emom_timer_label);
                    startButton.setText(R.string.start);
                } else {

                    // Start button pressed
                    isTimerStarted = true;
                    startButton.setText(R.string.stop_caps);

                    for (Button button : numpadButtons) {
                        button.setEnabled(false);
                        button.setTextColor(Color.LTGRAY);
                    }

                    countDownTimer = new CountDownTimer(timeRemaining, 1000) {
                        public void onTick(long millisUntilFinished) {
                            int minutesLeft = (int) millisUntilFinished / 60000;
                            int secondsLeft = (int) (millisUntilFinished % 60000 / 1000);

                            updateTimer(minutesLeft, secondsLeft);
                            timeRemaining = millisUntilFinished;
                        }

                        public void onFinish() {
                            for (Button button : numpadButtons) {
                                button.setEnabled(true);
                                button.setTextColor(Color.BLACK);
                            }
                            timeRemaining = 0;
                            timer.setText("Time's up!");
                            this.cancel();
                        }
                    }.start();
                }

            }
        });

        if (savedInstanceState != null) {
            timeRemaining = savedInstanceState.getLong(STATE_TIMER);
            if (timeRemaining > 0) {
                startButton.performClick();
            }
        }

        return result;
    }


    private void updateTimer(int minutesLeft, int secondsLeft) {
        String secondsLeftStr;
        if (secondsLeft < 10) {
            secondsLeftStr = "0" + secondsLeft;
        } else {
            secondsLeftStr = Integer.toString(secondsLeft);
        }

        if (minutesLeft > 0) {
            timer.setText(minutesLeft + ":" + secondsLeftStr);
        } else {
            timer.setText(Integer.toString(secondsLeft));
        }
    }


    static String getTitle(Context context, int position) {
        return "EMOM";
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong(STATE_TIMER, timeRemaining);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.numpad_0:
                numpadPressed(0);
                break;
            case R.id.numpad_1:
                numpadPressed(1);
                break;
            case R.id.numpad_2:
                numpadPressed(2);
                break;
            case R.id.numpad_3:
                numpadPressed(3);
                break;
            case R.id.numpad_4:
                numpadPressed(4);
                break;
            case R.id.numpad_5:
                numpadPressed(5);
                break;
            case R.id.numpad_6:
                numpadPressed(6);
                break;
            case R.id.numpad_7:
                numpadPressed(7);
                break;
            case R.id.numpad_8:
                numpadPressed(8);
                break;
            case R.id.numpad_9:
                numpadPressed(9);
                break;
            case R.id.numpad_backspace:
                numRounds /= 10;
                if (numRounds > 0) {
                    if (numRounds == 1) {
                        emomTitle.setText(Integer.toString(numRounds) + " Round");
                    } else {
                        emomTitle.setText(Integer.toString(numRounds) + " Rounds");
                    }
                } else {
                    emomTitle.setText("Enter number of rounds below");
                }
                break;

        }
    }


    private void numpadPressed(int digit) {
        String numRoundsStr;
        if (numRounds == 0 && digit == 0) {
            return;
        } else if (numRounds > 0) {
            numRoundsStr = Integer.toString(numRounds) + Integer.toString(digit);
        } else {
            numRoundsStr = Integer.toString(digit);
        }

        numRounds = Integer.parseInt(numRoundsStr);
        if (numRounds == 1) {
            emomTitle.setText(numRoundsStr + " Round");
        } else {
            emomTitle.setText(numRoundsStr + " Rounds");
        }
    }


    private void initUI(View layout) {
        emomTitle = (TextView) layout.findViewById(R.id.emom_title);
        if (numRounds > 0) {
            String numRoundsStr = Integer.toString(numRounds);
            if (numRounds == 1) {
                emomTitle.setText(numRoundsStr + " Round");
            } else {
                emomTitle.setText(numRoundsStr + " Rounds");
            }
        }
        timer = (TextView) layout.findViewById(R.id.emom_timer);
        numpad0 = (Button) layout.findViewById(R.id.numpad_0);
        numpad1 = (Button) layout.findViewById(R.id.numpad_1);
        numpad2 = (Button) layout.findViewById(R.id.numpad_2);
        numpad3 = (Button) layout.findViewById(R.id.numpad_3);
        numpad4 = (Button) layout.findViewById(R.id.numpad_4);
        numpad5 = (Button) layout.findViewById(R.id.numpad_5);
        numpad6 = (Button) layout.findViewById(R.id.numpad_6);
        numpad7 = (Button) layout.findViewById(R.id.numpad_7);
        numpad8 = (Button) layout.findViewById(R.id.numpad_8);
        numpad9 = (Button) layout.findViewById(R.id.numpad_9);
        backspace = (Button) layout.findViewById(R.id.numpad_backspace);
        start = (Button) layout.findViewById(R.id.start_stop_button);
    }

}
