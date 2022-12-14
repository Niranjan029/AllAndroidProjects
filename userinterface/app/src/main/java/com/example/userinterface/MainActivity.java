package com.example.userinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
  public static String text ;
  TextView textView;
    int max_width = 15 ;
    int max_height = 15 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.usertext);
        RequestQueue requestQueue ;
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://randomuser.me/api/", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Log.d("userapp","success");
                            text = response.getString("results");
                            textView.setText(text);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("userapp","failed");
            }
        });
        String URL = "https://randomuser.me/api/portraits/men/75.jpg";
        requestQueue.add(jsonObjectRequest);
        ImageRequest imageRequest = new ImageRequest(URL, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                ImageView imageView;
                imageView = (ImageView) findViewById(R.id.userImage);

                imageView.setImageBitmap(response);
            }
        }, max_width, max_height, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                 Log.d("userapp","failed to load");
            }
        });
      requestQueue.add(imageRequest);
    }
}