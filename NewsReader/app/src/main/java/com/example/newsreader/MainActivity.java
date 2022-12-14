package com.example.newsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

 ArrayList<String> titles = new ArrayList<>();
 ArrayList<String> contents = new ArrayList<>();
 ArrayAdapter<String> arrayAdapter ;
SQLiteDatabase articlesDB ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       articlesDB = this.openOrCreateDatabase("Articles",MODE_PRIVATE,null);
        articlesDB.execSQL("CREATE TABLE  IF NOT EXISTS articles(id INTEGER PRIMARY KEY , articleId INTEGER, title VARCHAR , content VARCHAR)");
         DownloadTask task = new DownloadTask();
         try
         {
         //  task.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }

        ListView listView = findViewById(R.id.listView);
        arrayAdapter= new ArrayAdapter<> (this, android.R.layout.simple_list_item_1,titles) ;
        listView.setAdapter(arrayAdapter) ;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                intent.putExtra("contents",contents.get(i));
                startActivity(intent);
            }
        });
        updateListview();

    }
 public   void updateListview()
    {
        Cursor c = articlesDB.rawQuery("SELECT * FROM articles",null);
        int indexoftitle = c.getColumnIndex("title");
        int indexofcontent = c.getColumnIndex("content");

        if(c.moveToFirst()){
            titles.clear();
            contents.clear();

            do{
                titles.add(c.getString(indexoftitle));
                contents.add(c.getString(indexofcontent));
            }while (c.moveToNext());
            arrayAdapter.notifyDataSetChanged();
        }


    }
    public  class DownloadTask extends AsyncTask<String, Void ,String>
    {
        @Override
        protected String doInBackground(String... urls) {
            URL url ;
            String result="";
            HttpURLConnection Connection = null ;
            try{
                url = new URL(urls[0]) ;
                Connection = (HttpURLConnection) url.openConnection();
                InputStream in = Connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while(data!=-1)
                {
                    char ch = (char) data ;
                    result = result + ch ;
                    data = reader.read();
                }
                JSONArray jsonArray = new JSONArray(result) ;
                int lenghtofarr = 20 ;
                if(jsonArray.length()<20)
                {
                    lenghtofarr = jsonArray.length();
                }
                    articlesDB.execSQL("DELETE FROM articles");
                for(int i=0;i<lenghtofarr;i++)
                {
                    String articleid=jsonArray.getString(i);

                    url = new URL("https://hacker-news.firebaseio.com/v0/item/" + articleid + ".json?print=pretty") ;
                    Connection = (HttpURLConnection) url.openConnection();
                     in = Connection.getInputStream();
                     reader = new InputStreamReader(in);
                     data = reader.read();
                     String  articleinfo="";
                    while(data!=-1)
                    {
                        char ch = (char) data ;
                        articleinfo += ch ;
                        data = reader.read();
                    }
                   // Log.i("articleinfo",articleinfo);
                    JSONObject jsonObject = new JSONObject(articleinfo);
                    if(!jsonObject.isNull("title") && !jsonObject.isNull("url"))
                    {
                        String  articleurl=jsonObject.getString("url");
                        String articletitle = jsonObject.getString("title");
//                        Log.i("title + url " ,articletitle + " "+ articleurl);

                        url = new URL(articleurl) ;
                        Connection = (HttpURLConnection) url.openConnection();
                        in = Connection.getInputStream();
                        reader = new InputStreamReader(in);
                        data = reader.read();
                        String articlecontent="";
                        while(data!=-1)
                        {
                            char ch = (char) data ;
                            articlecontent += ch ;
                            data = reader.read();
                        }
                        Log.i("HTML" ,articlecontent);
                        String sql = "INSERT INTO articles(articleId , title,content) VALUES(?,?,?)";
                        SQLiteStatement statement = articlesDB.compileStatement(sql);
                        statement.bindString(1,articleid);
                        statement.bindString(2,articletitle);
                        statement.bindString(3,articlecontent);
                        statement.execute();
                    }

                }
                return  result;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return null ;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            updateListview();
        }
    }
}