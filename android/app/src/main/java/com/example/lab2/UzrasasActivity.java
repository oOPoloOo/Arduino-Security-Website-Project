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
public class UzrasasActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "MainActivity";
    ArrayList<HashMap<String, String>> UzrasaiDataList;

    int flag = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setContentView(R.layout.uzrasai_list_row);

       gautiUzrasus();

        if (Tools.IsOnline(getApplicationContext())) {
            System.out.println("prisijungta");
        }







    }

    private void gautiUzrasus() {

        new gautiUzrasusTask().execute(Tools.HacksURL, null, null);

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
        ProgressDialog actionProgressDialog = new ProgressDialog(UzrasasActivity.this);

        @Override
        protected void onPreExecute() {
            actionProgressDialog.setMessage("Gaunami duomenys");
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
            //  Toast.makeText(getApplicationContext(), "Isilauzimu nerasta", Toast. LENGTH_SHORT).show();
             actionProgressDialog.cancel();

             Intent intent = new Intent(UzrasasActivity.this, MainActivity.class);
             startActivity(intent);

         }

            return uzrasai;

        }


        protected void onProgressUpdate(Void... progress) {
        }

        protected void onPostExecute(List<Uzrasas> result) {
            if(result.size() == 0) {
//                actionProgressDialog.cancel();
                Toast.makeText(getApplicationContext(), "Isilauzimu nerasta", Toast.LENGTH_SHORT).show();
            }
            actionProgressDialog.cancel();
            rodytiUzrasus(result, flag);


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







