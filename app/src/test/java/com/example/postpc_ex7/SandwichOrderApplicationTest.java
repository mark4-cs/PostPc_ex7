package com.example.postpc_ex7;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.junit.Before;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {28}, application = SandwichOrderApplication.class)
public class SandwichOrderApplicationTest {
    private ActivityController<newOrderActivity> activityController_newOrder;
    private newOrderActivity activityUnderTest_newOrder;

    @Before
    public void setup(){
        activityController_newOrder = Robolectric.buildActivity(newOrderActivity.class);
        activityUnderTest_newOrder = activityController_newOrder.get();
        activityController_newOrder.create().start().resume();
    }

    @Test
    public void flowTest1(){
        // On startup number of pickles is 5
        TextView amountOfPicklesTitle = activityUnderTest_newOrder.findViewById(R.id.amountOfPicklesTitle);
        int startup_pickles = Integer.parseInt(amountOfPicklesTitle.getText().toString().split(" ")[2]);
        assertEquals(startup_pickles, 5);
    }

    @Test
    public void flowTest2(){
        // check pickles text after changing the bar position
        SeekBar picklesBar = activityUnderTest_newOrder.findViewById(R.id.picklesBar);
        picklesBar.setProgress(0);
        TextView amountOfPicklesTitle = activityUnderTest_newOrder.findViewById(R.id.amountOfPicklesTitle);
        int cur_pickles_txt = Integer.parseInt(amountOfPicklesTitle.getText().toString().split(" ")[2]);
        assertEquals(cur_pickles_txt, 0);
    }

    @Test
    public void flowTest3(){
        // Check on startup tahini and hummus are unChecked
        CheckBox checkBoxHummus = activityUnderTest_newOrder.findViewById(R.id.checkBoxHummus);
        CheckBox checkBoxTahini = activityUnderTest_newOrder.findViewById(R.id.checkBoxTahini);
        assertFalse(checkBoxHummus.isChecked());
        assertFalse(checkBoxTahini.isChecked());
    }
}
