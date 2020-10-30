package com.example.lab2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class sortActivity extends Activity implements AdapterView.OnItemClickListener {
    private static final String TAG = "sortActivity";
    ArrayList<HashMap<String, String>> sortDataList;

    String sortType = null;
    String delID = null;
    String katID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sortType = getIntent().getStringExtra("sortType");
        delID = getIntent().getStringExtra("delID");
        katID = getIntent().getStringExtra("katID");
        setContentView(R.layout.uzrasai_list_row);

        //Toast.makeText(DuomenysActivity.this, "ParasytaData turinys " + parasytaData.length(), Toast.LENGTH_LONG).show();


        onActivityResult();

    }

    //----------------------------------------------------------------------------------------------


    protected void onActivityResult() {

        if (sortType != null) {
            if (sortType.compareTo("ASC") == 0) {

                new sortActivity.gautiUzrasusTask().execute(Tools.AscURL, null, null);
            } else if (sortType.compareTo("DESC") == 0) {
                new sortActivity.gautiUzrasusTask().execute(Tools.DescURL, null, null);
            }

        }
        else if (katID != null) {
            new sortActivity.gautiUzrasusTask().execute(Tools.CatsURL + katID, null, null);
        }
//        else if (delID != null) {
//            new sortActivity.trintiUzrasusTask().execute(Tools.DelURL + delID, null, null);
//            //new sortActivity.gautiUzrasusTask().execute(Tools.RestURL, null, null);
//        }


    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent The AdapterView where the click happened.
     * @param view   The view within the AdapterView that was clicked (this
     *               will be a view provided by the adapter)
     * @param pos    The position of the view in the adapter.
     * @param id     The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {


        String Pavadinimas = sortDataList.get(pos).get("pavadinimas");
        String Data = sortDataList.get(pos).get("data");
        String UzrasoID = sortDataList.get(pos).get("id");
        String Spalva = sortDataList.get(pos).get("spalva");
        String Kategorija = sortDataList.get(pos).get("kategorija");
        String Tekstas = sortDataList.get(pos).get("tekstas");


        Intent myIntent = new Intent(this, UzrasasActivity.class);
        myIntent.putExtra("pavadinimas", Pavadinimas);
        myIntent.putExtra("data", Data);
        myIntent.putExtra("id", UzrasoID);
        myIntent.putExtra("spalva", Spalva);
        myIntent.putExtra("kategorija", Kategorija);
        myIntent.putExtra("tekstas", Tekstas);

        startActivity(myIntent);
        System.out.println("Response Code **********************************2 ");
    }

    public class gautiUzrasusTask extends AsyncTask<String, String, List<Uzrasas>> {
        ProgressDialog actionProgressDialog = new ProgressDialog(sortActivity.this);

        @Override
        protected void onPreExecute() {

            actionProgressDialog.setMessage("Gaunami uzrasai");
            actionProgressDialog.show();
            actionProgressDialog.setCancelable(false);
            super.onPreExecute();

        }

        protected List<Uzrasas> doInBackground(String... str_param) {

            String sortURL = str_param[0];


            List<Uzrasas> uzrasai = new ArrayList<>();
            try {
                uzrasai = DataAPI.gautiUzrasus(sortURL);
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }
            return uzrasai;

        }

        protected void onProgressUpdate(Void... progress) {

        }

        protected void onPostExecute(List<Uzrasas> result) {
            Toast.makeText(sortActivity.this, "OnPostEx  ", Toast.LENGTH_LONG).show();
            actionProgressDialog.cancel();
            if (result.size() != 0) {
                Toast.makeText(sortActivity.this, "Rastas uzrasu skaičius: " + result.size(), Toast.LENGTH_SHORT).show();
                // rodytiUzrasus(result, dataNuo);// Niekur nenaudoja
                rodytiUzrasus(result);
            } else {

                Toast.makeText(sortActivity.this, "Buvo nerasta jokių uzrasu", Toast.LENGTH_SHORT).show();
                //Toast.makeText(DuomenysActivity.this, "Rodomi visi užrašai", Toast.LENGTH_SHORT).show();
                // gautiUzrasus();
            }
        }

    }
    //----------------------------------------------------------------------------------------------


    public class trintiUzrasusTask extends AsyncTask<String, String, List<Uzrasas>> {
        ProgressDialog actionProgressDialog = new ProgressDialog(sortActivity.this);

        @Override
        protected void onPreExecute() {

            actionProgressDialog.setMessage("Gaunami uzrasai");
            actionProgressDialog.show();
            actionProgressDialog.setCancelable(false);
            super.onPreExecute();

        }

        protected List<Uzrasas> doInBackground(String... str_param) {

            String sortURL = str_param[0];



            try {
              DataAPI.trintiUzrasus(sortURL);
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }
            return null;

        }

        protected void onProgressUpdate(Void... progress) {

        }

        protected void onPostExecute(List<Uzrasas> result) {
            Toast.makeText(sortActivity.this, "OnPostEx  ", Toast.LENGTH_LONG).show();
            actionProgressDialog.cancel();


                Toast.makeText(sortActivity.this, "uzrasas istrintas", Toast.LENGTH_SHORT).show();


        }

    }


    private void rodytiUzrasus(List<Uzrasas> uzrasai) {


        sortDataList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < uzrasai.size(); i++) {
            Uzrasas u = uzrasai.get(i);
            HashMap<String, String> UzrasasDataMap = new HashMap<>();
            UzrasasDataMap.put("id", String.valueOf(u.ID));
            UzrasasDataMap.put("kategorija", u.Kategorija);
            UzrasasDataMap.put("pavadinimas", u.Pavadinimas);
            UzrasasDataMap.put("data", u.DataIrLaikas);
            UzrasasDataMap.put("tekstas", u.Tekstas);


//
//            Log.v(TAG, "#" + u.Spalva);
//            TextView kat = findViewById(R.id.kategorijaTextView);
//            kat.setTextColor(Color.parseColor("#" + u.Spalva));


            sortDataList.add(UzrasasDataMap);
        }

        ListView mlv = (ListView) findViewById(R.id.uzrasaiListView);

//        View v = mlv.getChildAt(1);
//        TextView kat = v.findViewById(R.id.kategorijaTextView);
//        kat.setTextColor(Color.parseColor("#" + "FFFFFF"));

        SimpleAdapter SimpleMiestaiAdapter = new SimpleAdapter(this, sortDataList, R.layout.uzrasai_list_row,
                new String[]{"kategorija", "pavadinimas", "tekstas", "data"},
                new int[]{R.id.kategorijaTextView, R.id.pavadinimasTextView, R.id.tekstasTextView, R.id.dataTextView});

        mlv.setAdapter(SimpleMiestaiAdapter);
        mlv.setOnItemClickListener(this);
    }
}
