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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;


//public class MainActivity extends Activity implements AdapterView.OnItemClickListener {
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "MainActivity";
    ArrayList<HashMap<String, String>> UzrasaiDataList;

    int flag = -1;


    EditText dataNuo;
    EditText dataIki;
    EditText resp_time;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_layout);
//        setContentView(R.layout.uzrasai_list_row);
        dataNuo = (EditText) findViewById(R.id.dataNuo);
        dataIki = (EditText) findViewById(R.id.dataIki);
        resp_time = (EditText) findViewById(R.id.resp_time);



        if (Tools.IsOnline(getApplicationContext())) {
            System.out.println("prisijungta");
        }

        Button shAll = findViewById(R.id.showAll);
        shAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, UzrasasActivity.class);


                startActivity(intent);

//

            }
        });
        Button response = findViewById(R.id.send_time);
        response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, MainActivity.class);

                String respTimeVal = resp_time.getText().toString();

             //   intent.putExtra("responseTime",  respTimeVal);
                Toast.makeText(getApplicationContext(), respTimeVal, Toast. LENGTH_SHORT).show();
                new siustiLaika().execute(Tools.responseURL+respTimeVal, null, null);

                startActivity(intent);

            }
        });
        Button seeCount = findViewById(R.id.chart);
        seeCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, UzrasasChartActivity.class);

                String dataNuoVal = dataNuo.getText().toString();
                String dataIkiVal = dataIki.getText().toString();


                intent.putExtra("dFrom",  dataNuoVal);
                intent.putExtra("dTo",  dataIkiVal);

                startActivity(intent);

//

            }
        });


        Button rmAll = findViewById(R.id.removeAll);
        rmAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(MainActivity.this, sortActivity.class);
//
//                intent.putExtra("sortType", "DESC");
//                startActivity(intent);

                new gautiUzrasusTask().execute(Tools.DelAllURL, null, null);
                Intent intent = new Intent(MainActivity.this, MainActivity.class);

                Toast.makeText(getApplicationContext(), "Ištrinta sėkmingai", Toast. LENGTH_SHORT).show();

                startActivity(intent);


            }
        });
//
//
//        final Spinner spinner = (Spinner) findViewById(R.id.katSpinner);
//
//        List<String> list = new ArrayList<String>();
//        list.add("Darbas");
//        list.add("Kita");
//        list.add("Svarbu");
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(dataAdapter);
//
//
//        Button issaugoti = findViewById(R.id.btnSpinner);
//        issaugoti.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                String kategorija;
//                System.out.println(spinner.getSelectedItem().toString());
//                if(spinner.getSelectedItem().toString().equals("Darbas"))
//                    kategorija = "2";
//                else if(spinner.getSelectedItem().toString().equals("Svarbu"))
//                    kategorija = "1";
//                else
//                    kategorija = "3";
//
//                Intent intent = new Intent(MainActivity.this, sortActivity.class);
//
//                intent.putExtra("katID", kategorija);
//                startActivity(intent);
//
//
//            }
//        });
//
//
//
//

//        Button InsertUzrasai = findViewById(R.id.btnInsert);
//        InsertUzrasai.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(MainActivity.this, InsertActivity.class);
//
//
//                startActivity(intent);
//
//
//            }
//        });

//        Button BtnDelete = findViewById(R.id.delete);
//
//        BtnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                Intent intent = new Intent(MainActivity.this, sortActivity.class);
//                String delID = null;
//                EditText delValue = findViewById(R.id.delID);
//                delID = delValue.getText().toString();
//
//                if (delID.compareTo("") != 0) {
//                    Intent intent = new Intent(MainActivity.this, TrintiActivity.class);
//
//                    intent.putExtra("delID", delID);
//                    startActivity(intent);
//                } else
//                {
//                    Toast.makeText(MainActivity.this, "Neivestas ID: ", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });

    }

    private void gautiUzrasus() {

        new gautiUzrasusTask().execute(Tools.HacksURL, null, null);

    }

    private void gautiUzrasusASC() {

        new gautiUzrasusTask().execute(Tools.AscURL, null, null);

    }

    private void gautiUzrasusDESC() {

        new gautiUzrasusTask().execute(Tools.DescURL, null, null);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

//
//        String UzrasoID = UzrasaiDataList.get(pos).get("id");
//        String Pavadinimas = UzrasaiDataList.get(pos).get("pavadinimas");
//
//
//        Intent myIntent = new Intent(this, UzrasasActivity.class);
//        myIntent.putExtra("id", UzrasoID);
//        myIntent.putExtra("pavadinimas", Pavadinimas);
//
//        startActivity(myIntent);

    }


    public class gautiUzrasusTask extends AsyncTask<String, Void, List<Uzrasas>> {
        //private static final String TAG = "gautiUzrasusTask";
        ProgressDialog actionProgressDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            actionProgressDialog.setMessage("Gaunami užrašai");
            actionProgressDialog.show();
            actionProgressDialog.setCancelable(false);
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

            System.out.println("-----AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA-------");
            System.out.println(uzrasai);

            return uzrasai;

        }


        protected void onProgressUpdate(Void... progress) {
        }

        protected void onPostExecute(List<Uzrasas> result) {
            actionProgressDialog.cancel();
            rodytiUzrasus(result, flag);
        }

    }
    public class siustiLaika extends AsyncTask<String, Void, List<Uzrasas>> {
        //private static final String TAG = "gautiUzrasusTask";
        ProgressDialog actionProgressDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            actionProgressDialog.setMessage("Gaunami užrašai");
            actionProgressDialog.show();
            actionProgressDialog.setCancelable(false);
            super.onPreExecute();
        }

        protected List<Uzrasas> doInBackground(String... str_param) {
            String RestURL = str_param[0];
            List<Uzrasas> uzrasai = new ArrayList<>();
            try {
                 DataAPI.siustiLaika(RestURL);
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }

            System.out.println("-----AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA-------");
            System.out.println(uzrasai);

            return null;

        }


        protected void onProgressUpdate(Void... progress) {
        }

        protected void onPostExecute(List<Uzrasas> result) {
            actionProgressDialog.cancel();
           // rodytiUzrasus(result, flag);
        }

    }

    private void rodytiUzrasus(List<Uzrasas> uzrasai, int flag) {


        UzrasaiDataList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < uzrasai.size(); i++) {
            Uzrasas u = uzrasai.get(i);
            HashMap<String, String> UzrasasDataMap = new HashMap<>();
            UzrasasDataMap.put("id", String.valueOf(u.ID));
            UzrasasDataMap.put("data", u.DataIrLaikas);



            UzrasaiDataList.add(UzrasasDataMap);
        }

        ListView mlv = (ListView) findViewById(R.id.uzrasaiListView);
//
        SimpleAdapter SimpleMiestaiAdapter = new SimpleAdapter(this, UzrasaiDataList, R.layout.activity_main,
                new String[]{"id", "data"},
                new int[]{R.id.idTextView, R.id.dataTextView});

        mlv.setAdapter(SimpleMiestaiAdapter);
        mlv.setOnItemClickListener(this);
    }

}







