package com.example.languageselector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
TextView textView ;
    SharedPreferences sharedPreferences ;

    void setLanguage(String language)
    {
        sharedPreferences.edit().putString("language", language).apply();
        textView.setText(language);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.english:setLanguage("English");
                return  true ;
            case R.id.spanish:setLanguage("Spanish");
                return  true ;
            default:
                return  false ;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textshow);
       sharedPreferences = this.getSharedPreferences("com.example.languageselector", Context.MODE_PRIVATE);
       String str = sharedPreferences.getString("language","error");
       if(str.equals("error")) {
           new AlertDialog.Builder(this)
                   .setIcon(android.R.drawable.ic_dialog_alert)
                   .setTitle("Language Selector")
                   .setMessage("please select a language")
                   .setPositiveButton("English", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                          setLanguage("English");
                       }
                   })
                   .setNegativeButton("Spanish", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                            setLanguage("Spanish");
                       }
                   }).show();
       }
         textView.setText(str);

    }
}