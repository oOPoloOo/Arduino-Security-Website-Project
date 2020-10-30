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

public class UzrasasChartActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "MainActivity";
    ArrayList<HashMap<String, String>> UzrasaiDataList;

    int flag = -1;

    String dFrom;
    String dTo;

    ArrayList<String> isilauzimu_data=new ArrayList<>();
    ArrayList<Integer> isilauzimu_kiekis=new ArrayList<>();

    AnyChartView anyChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        dFrom = getIntent().getStringExtra("dFrom");
        dTo = getIntent().getStringExtra("dTo");

        gautiUzrasuSK();

        if (Tools.IsOnline(getApplicationContext())) {
            System.out.println("prisijungta");
        }

        anyChartView = findViewById(R.id.any_chart_view);


    }

    public void setupPieChart(){
        Pie pie = AnyChart.pie();

        List<DataEntry> dataEntries = new ArrayList<>();

        for (int i = 0; i < isilauzimu_data.size(); i++) {
            dataEntries.add(new ValueDataEntry(isilauzimu_data.get(i), isilauzimu_kiekis.get(i)));
        }
        pie.data(dataEntries);
        anyChartView.setChart(pie);
    }

    private void gautiUzrasuSK() {

        new UzrasasChartActivity.gautiUzrasusTask().execute(Tools.HacksCountURL+dFrom+"/"+dTo, null, null);

    }





    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
    }


    public class gautiUzrasusTask extends AsyncTask<String, Void, List<Uzrasas>> {
        //private static final String TAG = "gautiUzrasusTask";
        ProgressDialog actionProgressDialog = new ProgressDialog(UzrasasChartActivity.this);

        @Override
        protected void onPreExecute() {
            actionProgressDialog.setMessage("Gaunami užrašai");
            actionProgressDialog.show();
            actionProgressDialog.setCancelable(true);
            super.onPreExecute();
        }

        protected List<Uzrasas> doInBackground(String... str_param) {
            String RestURL = str_param[0];
            List<Uzrasas> uzrasai = new ArrayList<>();
            try {
                uzrasai = DataAPI.gautiUzrasus(RestURL);
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }

            if(uzrasai.size() == 0){
                actionProgressDialog.cancel();
                // Toast.makeText(getApplicationContext(), "Isilauzimu nerasta", Toast. LENGTH_SHORT).show();
                Intent intent = new Intent(UzrasasChartActivity.this, MainActivity.class);
                startActivity(intent);

            }

            return uzrasai;

        }


        protected void onProgressUpdate(Void... progress) {
        }

        protected void onPostExecute(List<Uzrasas> result) {
            actionProgressDialog.cancel();
            rodytiUzrasus(result, flag, isilauzimu_data, isilauzimu_kiekis);

            setupPieChart();

        }

    }

    private void rodytiUzrasus(List<Uzrasas> uzrasai, int flag, ArrayList<String> data, ArrayList<Integer> count) {


        UzrasaiDataList = new ArrayList<HashMap<String, String>>();




        for (int i = 0; i < uzrasai.size(); i++) {
            Uzrasas u = uzrasai.get(i);
            HashMap<String, String> UzrasasDataMap = new HashMap<>();
            UzrasasDataMap.put("id", String.valueOf(u.Kiekis));
            UzrasasDataMap.put("data", u.Data);

            UzrasaiDataList.add(UzrasasDataMap);

            data.add(u.Data);
            count.add(Integer.parseInt(u.Kiekis));

        }
        for (int i = 0; i < isilauzimu_data.size(); i++) {
            System.out.println(isilauzimu_data.get(i));
        }



//        ListView mlv = (ListView) findViewById(R.id.uzrasaiListView);
////
//        SimpleAdapter SimpleMiestaiAdapter = new SimpleAdapter(this, UzrasaiDataList, R.layout.activity_main,
//                new String[]{"id", "data"},
//                new int[]{R.id.idTextView, R.id.dataTextView});
//
//        mlv.setAdapter(SimpleMiestaiAdapter);
//        mlv.setOnItemClickListener(this);
    }

}
