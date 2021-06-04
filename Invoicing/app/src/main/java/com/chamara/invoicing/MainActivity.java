package com.chamara.invoicing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String url="https://invoicingappchamara.000webhostapp.com/products.php";
    ArrayList<String> productNames;
    Spinner spinner;
    EditText invno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        invno = findViewById(R.id.txtInvoiceNumber);
        spinner = findViewById(R.id.spinner);
        getProducts();
    }

    public void getProducts(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("products");
                    for (int x=0;x<jsonArray.length();x++){
                        //productNames.add(jsonArray.getJSONObject(x).getString("name"));
                        //invno.append(jsonArray.getJSONObject(x).getString("name"));
                        Toast.makeText(MainActivity.this,jsonArray.getJSONObject(x).getString("name"), Toast.LENGTH_SHORT).show();
                    }
                    /*ArrayAdapter adapter =
                            new ArrayAdapter(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, productNames);
                    adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);*/

                    //spinner.setAdapter(adapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
               //requestQueue.add(stringRequest);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
}