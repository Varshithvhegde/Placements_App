package com.varshith.placements;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Model> list;
    private JAdapter adapter;
    EditText search;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        RecyclerView recyclerView = findViewById(R.id.IdRecyclerView);
        search = findViewById(R.id.searching);
        list = new ArrayList<>();

//        Set a sample data to the list
//        list.add(new Model("Varshith", 100000, "Google"));/**/
//        show the data in the adapter

        adapter = new JAdapter(list, this);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        getStateData();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterState(editable.toString());
            }
        });
    }

    private void getStateData () {
        //below is api link provided  By govt of india to get all details about covid 19 cases in india
        String url = "https://script.google.com/macros/s/AKfycbxvZk6Wxi0TAUH8x6oBmTxO6vXhNoIbSpVoL2LdsanF6PjSiN-czA6j712Yzs50fVfgig/exec";
//        using above link we are getting data in json format
//        json format is {data:[{Name,Salary,Company}]}
//        so we are using volley library to get data from api
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONObject object = new JSONObject(response.toString());
//                here we are getting data from json object
                JSONArray data = object.getJSONArray("data");
//                here we are getting data from data object
//                Toast.makeText(this, data.length(), Toast.LENGTH_SHORT).show();
//                jsonarray to object
//                using while loop

                for (int i = 1; i < data.length(); i++) {
                    JSONObject jsonObject = data.getJSONObject(i);
                    String name = jsonObject.getString("Name");
//                    Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
                    double salary = jsonObject.getDouble("Salary");
                    String company = jsonObject.getString("Company");
                    list.add(new Model(name, salary, company));
                    adapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(ProgressBar.GONE);




            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
        });
        queue.add(request);
    }

    private void filterState(String text) {
        ArrayList<Model> filteredList = new ArrayList<>();
        for (Model item : list) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }
}

//                Iterator<String> keys = data.keys();
////                here we are getting keys from data object
//                for(int i=1;i<2;i++){
//                    String key = keys.next();
//                    JSONObject value = data.getJSONObject(key);
////                    here we are getting values from data object
//                    String Name = value.getString("Name");
//                    int Salary = value.getInt("Salary");
////                    Convert salary to integer
////                    int salary = Integer.parseInt(Salary);
//                    String Company = value.getString("Company");
//                    list.add(new Model(Name, Salary, Company));
//