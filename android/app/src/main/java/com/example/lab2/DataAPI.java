package com.example.lab2;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

public class DataAPI {

    public static  final String TAG = "DataAPI";

    public static List<Uzrasas> gautiUzrasus(String RestURL) throws Exception
    {
        List<Uzrasas> uzrasai = new ArrayList<Uzrasas>();
        String response = WebAPI.gautiUzrasus(RestURL);

        if(response.length() > 1)
        {
            Gson gson;
            gson = new Gson();

            java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<Uzrasas>>() {}.getType();
            uzrasai = gson.fromJson(response, type);

        }

        return  uzrasai;
    }




    public static void trintiUzrasus(String RestURL) throws Exception
    {

       WebAPI.trintiUzrasus(RestURL);
    }

    public static String Redaguoti(String RestURL, String val) throws Exception{
        return WebAPI.RedaguotiUzrasas(RestURL, val);
    }


    public static void detiNote(String url, String pav, String kat, String tekstas) throws Exception{

        WebAPI.detiUzrasa(url,pav,kat,tekstas);

    }

    public static void siustiLaika(String url) throws Exception{

        WebAPI.siustiLaika(url);

    }
}
