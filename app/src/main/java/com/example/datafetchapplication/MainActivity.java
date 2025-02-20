package com.example.datafetchapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String URL = "https://jsonplaceholder.typicode.com/posts";
    private ListView listView;
    private MyAdapter adapter;
    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_item); // Corrected ListView ID
        itemList = new ArrayList<>(); // Initialize list outside the loop
        NetwordkUtls networdkUtls = new NetwordkUtls();

        networdkUtls.fetchData(this, URL, new NetwordkUtls.VolleyCallback() {
            public void onSuccess(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response); // Convert response to JSONArray

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject post = jsonArray.getJSONObject(i);
                        int id = post.getInt("id");
                        String title = post.getString("title");
                        String body = post.getString("body");

                        itemList.add(new Item(title, body)); // Add actual fetched data

                        Log.d("Post", "ID: " + id + " Title: " + title);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("JSONError", "Parsing error: " + e.getMessage());
                }
            }

            @Override
            public void onError(String error) {
                System.err.println("Error: " + error);
            }
        });
    }




}
