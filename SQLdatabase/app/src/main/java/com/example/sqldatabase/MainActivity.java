package com.example.sqldatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        try {
//            SQLiteDatabase database = this.openOrCreateDatabase("Events", MODE_PRIVATE, null);
//            database.execSQL("CREATE TABLE IF NOT EXISTS events (name VARCHAR , age INT(3))");
//            database.execSQL("INSERT INTO events (name,age) VALUES('niranjan',21)");
//            database.execSQL("INSERT INTO events (name,age) VALUES('shikhar',19)");
//            database.execSQL("INSERT INTO events (name,age) VALUES('chetnani',20)");
//
//
//            Cursor cur = database.rawQuery("SELECT * FROM events", null);
//
//            int indexofname = cur.getColumnIndex("name");
//            int indexofage = cur.getColumnIndex("age");
//            cur.moveToFirst();
//            while (!cur.isAfterLast()) {
//                Log.i("name", cur.getString(indexofname));
//                Log.i("age", Integer.toString(cur.getInt(indexofage)));
//                cur.moveToNext();
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }


        WebView webView = findViewById(R.id.webView);

            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl("https://www.google.com");



    }
}