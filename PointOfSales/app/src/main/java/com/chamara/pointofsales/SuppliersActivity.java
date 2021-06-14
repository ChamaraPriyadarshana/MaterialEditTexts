package com.chamara.pointofsales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

public class SuppliersActivity extends AppCompatActivity {
    String url = "https://pointofsaleschamara.000webhostapp.com/suppliers.php";

    LinearLayout mainLinear;
    FloatingActionButton btnRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppliers);

        mainLinear = findViewById(R.id.mainLInear);
        btnRefresh = findViewById(R.id.floatingActionButton);

        retrieveData();

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveData();
            }
        });
    }
    private void retrieveData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("suppliers");

                    for (int i=0;i<jsonArray.length();i++){ //jsonArray.length()

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String address = jsonObject.getString("address");
                        String contact = jsonObject.getString("contact");
                        String email = jsonObject.getString("email");

                        setTextViews(id,name,address,contact,email);

                    }
                }catch (Exception e){}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SuppliersActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void setTextViews(String id, String name, String address, String contact, String email) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(30, 20, 30, 0);

        CardView cardView = new CardView(this);
        cardView.setRadius(24);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(25,25,25,25);

        TextView txtId = new TextView(this);
        txtId.setText("PId          : "+id);

        TextView txtName = new TextView(this);
        txtName.setText("Item        : "+name);

        TextView txtAddress = new TextView(this);
        txtAddress.setText("Address  : "+address);

        TextView txtContact = new TextView(this);
        txtContact.setText("Amount   : "+contact);

        TextView txtEmail = new TextView(this);
        txtEmail.setText("Email    : "+email);

        linearLayout.addView(txtId);
        linearLayout.addView(txtName);
        linearLayout.addView(txtAddress);
        linearLayout.addView(txtContact);
        linearLayout.addView(txtEmail);

        cardView.addView(linearLayout);

        mainLinear.addView(cardView,layoutParams);
    }
}