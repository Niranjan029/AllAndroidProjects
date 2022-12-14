package com.example.vollydemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public class Downloadtask extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... urls) {
            String result = " " ;
            URL url  ;
            HttpURLConnection URLConnection = null ;
            try {
                url = new URL(urls[0]);
                URLConnection = (HttpURLConnection) url.openConnection() ;
                InputStream in = URLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
               int data =  reader.read() ;
                while(data!=-1)
                {
                    char ch = (char) data ;
                    result+=ch;
                   data =  reader.read() ;
                }
                return result ;
            }
            catch (Exception e) {
                e.printStackTrace();
                return "Failed" ;
            }

            //Log.i("URL",urls[0]) ;

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        RequestQueue requestQueue ;
//        requestQueue  = Volley.newRequestQueue(this);
//
//        JsonObjectRequest jsonObjectRequest ;
//        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://jsonplaceholder.typicode.com/todos/1", null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    Log.d("myapp","The response id "+response.getString("title")) ;
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//               Log.d("myapp","Something went wrong");
//            }
//        });
//      requestQueue.add(jsonObjectRequest);
      Downloadtask task = new Downloadtask() ;
    String result = null ;
      try {
           result = task.execute("https://www.codewithharry.com").get();
      }
     catch (Exception e)
     {
         e.printStackTrace() ;
     }
        Log.i("URL",result);
    }
}