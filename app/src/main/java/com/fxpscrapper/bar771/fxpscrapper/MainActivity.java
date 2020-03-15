package com.fxpscrapper.bar771.fxpscrapper;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView myListView;
    CustomAdapter adapter;
    static ArrayList<ThreadItem> threads;

    static Context con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        con = this;

        myListView = (ListView) findViewById(R.id.threadList);
        threads = new ArrayList<ThreadItem>();
        adapter = new CustomAdapter(MainActivity.this, R.layout.threadrow, threads);
        myListView.setAdapter(adapter);

        final EditText pageCount = (EditText) findViewById(R.id.pageCount);

        Scrapper scrap = new Scrapper(Integer.parseInt(pageCount.getText().toString()));
        scrap.execute();
        adapter.notifyDataSetChanged();

        Button downloadThreads = (Button) findViewById(R.id.scrapBtn);
        View.OnClickListener clicked = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Scrapper scrap = new Scrapper(Integer.parseInt(pageCount.getText().toString()));
                scrap.execute();
                adapter.notifyDataSetChanged();
            }
        };
        downloadThreads.setOnClickListener(clicked);
    }
}
