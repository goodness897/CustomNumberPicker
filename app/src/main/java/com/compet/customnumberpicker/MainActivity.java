package com.compet.customnumberpicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ScalePicker singleDateAndTimePicker = (ScalePicker) findViewById(R.id.picker_scale);
        singleDateAndTimePicker.setListener(new ScalePicker.Listener() {
            @Override
            public void onNumberChanged(String displayed, int number) {
                displayed(displayed);

            }

            @Override
            public void onClickChanged() {
            }
        });

    }

    private void displayed(String toDisplay) {
        Toast.makeText(this, toDisplay, Toast.LENGTH_SHORT).show();
    }
}
