package com.example.crossfittimer.test;


import android.app.ActionBar;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.crossfittimer.EmomFragment;
import com.example.crossfittimer.MainActivity;
import com.example.crossfittimer.R;

import java.util.ArrayList;

/**
 * Created by Kayvon on 10/8/13.
 */
public class CrossfitTimerTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity activity;

    // Emom UI Elements
    private Fragment emomFragment;
    private GridLayout numpadLayout;
    private TextView emomTitle;
    private Button numpad0, numpad1, numpad2, numpad3, numpad4, numpad5, numpad6, numpad7, numpad8, numpad9;
    private ArrayList<Button> numpadButtons = new ArrayList<Button>();
    private Button backspace;
    private Button startStopBtn;
    private TextView timer;



    public CrossfitTimerTest() {
        super(MainActivity.class);
    }


    @Override
    protected void setUp() throws Exception {

        super.setUp();

        setActivityInitialTouchMode(false);

        activity = getActivity();
        ActionBar actionBar = activity.getActionBar();
        final ActionBar.Tab emomTab = actionBar.getTabAt(1);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.onTabSelected(emomTab, null);
            }
        });

        EmomFragment frag = new EmomFragment();
        this.emomFragment = getFragment(frag);
        setUpEmom();
    }


    // Setup for Emom Fragment to initialize all UI elements
    private void setUpEmom() {
        emomTitle = (TextView) activity.findViewById(R.id.emom_title);
        timer = (TextView) activity.findViewById(R.id.emom_timer);
        numpad0 = (Button) activity.findViewById(R.id.numpad_0);
        numpad1 = (Button) activity.findViewById(R.id.numpad_1);
        numpad2 = (Button) activity.findViewById(R.id.numpad_2);
        numpad3 = (Button) activity.findViewById(R.id.numpad_3);
        numpad4 = (Button) activity.findViewById(R.id.numpad_4);
        numpad5 = (Button) activity.findViewById(R.id.numpad_5);
        numpad6 = (Button) activity.findViewById(R.id.numpad_6);
        numpad7 = (Button) activity.findViewById(R.id.numpad_7);
        numpad8 = (Button) activity.findViewById(R.id.numpad_8);
        numpad9 = (Button) activity.findViewById(R.id.numpad_9);
        backspace = (Button) activity.findViewById(R.id.numpad_backspace);
        startStopBtn = (Button) activity.findViewById(R.id.start_stop_button);

        numpadLayout = (GridLayout) activity.findViewById(R.id.numpad_layout);

        for (int i = 0; i < numpadLayout.getChildCount(); i++) {
            View currView = numpadLayout.getChildAt(i);
            Class currViewClass = currView.getClass();
            if (currViewClass == Button.class) {
                numpadButtons.add((Button) currView);
            }
        }
    }


    private Fragment getFragment(Fragment fragment) {

        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.add(0, fragment, "tag");
        transaction.commit();

        getInstrumentation().waitForIdleSync();
        Fragment frag = activity.getSupportFragmentManager().findFragmentByTag("tag");
        return frag;

    }


    // Test to make sure all UI elements are initialized correctly
    public void testEmomPreconditions() {

        // emomTitle tests
        assertNotNull(emomTitle);
        assertNotNull(emomTitle.getText());
        assertEquals (emomTitle.getText().toString(), this.emomFragment.getString(R.string.emom_title));

        // numpad buttons tests
        assertEquals(11, numpadButtons.size());
        for (Button numpadBtn : numpadButtons) {
            assertNotNull(numpadBtn);
        }

        // start / stop button tests
        assertNotNull(startStopBtn);
        assertNotNull(startStopBtn.getText());
        assertNotNull(startStopBtn.getText().toString(), this.emomFragment.getString(R.string.start));
    }


    // Test numpad key input
    public void testEmomNumpad() {

        assertEquals (emomTitle.getText().toString(), this.emomFragment.getString(R.string.emom_title));

        // Loop through each numpad number EXCEPT backspace
        for (int i = 0; i < numpadButtons.size() - 1; i++) {

            // Input each numpad button one by one
            final Button currButton = numpadButtons.get(i);
            int currDigit = Integer.parseInt(currButton.getText().toString());
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    currButton.performClick();
                }
            });

            // Wait for UI to sync
            getInstrumentation().waitForIdleSync();

            emomTitle = (TextView) activity.findViewById(R.id.emom_title);

            if (currDigit == 0) {
                assertEquals ( "How many rounds?", emomTitle.getText().toString());
            } else if (currDigit == 1) {
                assertEquals ( "1 Round", emomTitle.getText().toString());
            } else {
                assertEquals ( currDigit + " Rounds", emomTitle.getText().toString());
            }

            // Delete the previous input
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    backspace.performClick();
                }
            });

            // Wait for UI to sync
            getInstrumentation().waitForIdleSync();
            emomTitle = (TextView) activity.findViewById(R.id.emom_title);
            assertEquals ( "How many rounds?", emomTitle.getText().toString());


        }
    }

    // Test simple start with countdown for 1 minute
    public void testSimpleOneMinEmom() {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                numpad1.performClick();
            }
        });

        getInstrumentation().waitForIdleSync();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startStopBtn.performClick();
            }
        });

        getInstrumentation().waitForIdleSync();

        emomTitle = (TextView) activity.findViewById(R.id.emom_title);
    }
}
