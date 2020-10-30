package com.example.lab2;


//import android.telecom.Call;
import android.util.Log;
import android.view.textclassifier.ConversationActions;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//import javax.security.auth.callback.Callback;



import javax.net.ssl.HttpsURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WebAPI extends AppCompatActivity {

    private static final String TAG = "WebAPI";
    public static String gautiUzrasus(String url) throws Exception {


        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("Responsse code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Responsse code :: " + HttpURLConnection.HTTP_OK);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            System.out.println("Žingsnis *********");
            String inputLine;
            System.out.println("Žingsnis *********");
            StringBuffer response = new StringBuffer();

            System.out.println("Žingsnis *********");
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                System.out.println("Žingsnis *****InputLine+****");
            }
            in.close();
            System.out.println("Žingsnis *****WEBAPI+****::" + response.toString());
            return response.toString();
        }

        return null;
    }


    public static void trintiUzrasus(String url) throws Exception {


        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setConnectTimeout(100);
        con.setReadTimeout(100);
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();


        con.disconnect();


    }
    public static void siustiLaika(String url) throws Exception {


        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setConnectTimeout(100);
        con.setReadTimeout(100);
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();


        con.disconnect();


    }
    public  static String RedaguotiUzrasas(String url, String val) throws Exception{
        String bendras = url + "" + val;
        URL obj = new URL(bendras);
        HttpURLConnection con = (HttpURLConnection)obj.openConnection();
        con.setRequestMethod("POST");
        int responseCode = con.getResponseCode();
        StringBuffer response = new StringBuffer();
        System.out.println("Response Code ::: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { //connection ok
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            //StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }
        con.disconnect();
        return response.toString();
    }

       public static void detiUzrasa(String url, String pav, String kat, String tekstas) throws Exception {

        String data = "{\"pav\":\""+pav+"\", \"kat1\":\""+kat+"\", \"tekstas\":\""+tekstas+"\"}"; //data to post

        Log.d("JSON", data);

        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MEDIA_TYPE, data);

           Request request = new Request.Builder()
                   .url(url)
                   .post(body)
                   .header("Content-Type", "application/json")
                   .build();



        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                //call.cancel();


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String mMessage = response.body().string();
                Log.e(TAG, mMessage);
            }
        });

  }

}
