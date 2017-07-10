package com.panyz.customwidget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.panyz.customwidget.widget.ArcBarView;

public class MainActivity extends AppCompatActivity {

    private ArcBarView arcBar;

    private float result = 0;//剩余流量
    private String totalData = "2371.00";//可用流量
    private String usedData = "276.99";//已用流量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arcBar = (ArcBarView) findViewById(R.id.arc_bar);

        result = Float.parseFloat(totalData) - Float.parseFloat(usedData);

        arcBar.setTotalData(totalData+"M");
        arcBar.setUsedData(usedData+"M");
        arcBar.setResult(result);
    }
}
