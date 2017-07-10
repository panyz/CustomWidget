package com.panyz.customwidget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.panyz.customwidget.widget.ArcBarView;

public class MainActivity extends AppCompatActivity {

    private ArcBarView arcBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arcBar = (ArcBarView) findViewById(R.id.arc_bar);
    }
}
