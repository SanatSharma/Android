package com.example.sanat.scrabblecheater;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

public class Activity2 extends AppCompatActivity {

    ArrayList<String> values;
    ArrayAdapter<String> adapter;
    ListView listView;
    AnagramFinder_Scrabble a = new AnagramFinder_Scrabble();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        values = new ArrayList<String>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        Intent intent = getIntent();
        String input = intent.getExtras().getString("input");
        Map<String, Integer> words = a.run(input, getApplicationContext());

        ArrayList<String> addKeys = new ArrayList<String>();
        for(String key: words.keySet())
        {
            addKeys.add(key);
        }

        for(int i=0;i<addKeys.size();i++)
        {
            String sent = addKeys.get(i) + " = " + words.get(addKeys.get(i));
            values.add(sent);
        }

        adapter = new ArrayAdapter<String>(this,R.layout.list_layout, R.id.list_item,values);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
