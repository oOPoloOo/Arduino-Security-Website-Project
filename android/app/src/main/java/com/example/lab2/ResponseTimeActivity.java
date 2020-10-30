

package com.example.lab2;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;


public class ResponseTimeActivity  extends AppCompatActivity implements AdapterView.OnItemClickListener{

    String responseTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.button_layout);
        responseTime = getIntent().getStringExtra("responseTime");

        Toast.makeText(getApplicationContext(), responseTime, Toast. LENGTH_SHORT).show();

        if (Tools.IsOnline(getApplicationContext())) {
            System.out.println("prisijungta");
        }


    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
    }


}
