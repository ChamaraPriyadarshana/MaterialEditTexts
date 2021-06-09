package com.chamara.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton btnrefresh;
    TextView txtid,txtname,txtaddress,txtcontact,txtcourse;
    String url = "https://asdinstitute.000webhostapp.com/retrieve.php";
    LinearLayout mainLinear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defineComponents();

        btnrefresh.setOnClickListener(new View.OnClickListener() {
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

                    JSONArray jsonArray = response.getJSONArray("students");

                    for (int i=0;i<jsonArray.length();i++){ //jsonArray.length()

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String address = jsonObject.getString("address");
                        String contact = jsonObject.getString("contact");
                        String course = jsonObject.getString("course");

                        setTextViews(id,name,address,contact,course);

                    }
                }catch (Exception e){}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void setTextViews(String id, String name, String address, String contact, String course) {

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(30, 20, 30, 0);

        CardView cardView = new CardView(this);
        cardView.setRadius(24);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(15,15,15,15);

        TextView txtId = new TextView(this);
        txtId.setText(id);

        TextView txtName = new TextView(this);
        txtName.setText(name);

        TextView txtAddress = new TextView(this);
        txtAddress.setText(address);

        TextView txtContact = new TextView(this);
        txtContact.setText(address);

        TextView txtCourse = new TextView(this);
        txtCourse.setText(course);

        linearLayout.addView(txtId);
        linearLayout.addView(txtName);
        linearLayout.addView(txtAddress);
        linearLayout.addView(txtContact);
        linearLayout.addView(txtCourse);

        cardView.addView(linearLayout);

        mainLinear.addView(cardView,layoutParams);
        /*txtid.setText(id);
        txtname.setText(name);
        txtaddress.setText(address);
        txtcontact.setText(contact);
        txtcourse.setText(course);*/
    }

    private void defineComponents() {
        btnrefresh = findViewById(R.id.btnRefresh);
        /*txtid = findViewById(R.id.txtId);
        txtname = findViewById(R.id.txtName);
        txtaddress = findViewById(R.id.txtAddress);
        txtcontact = findViewById(R.id.txtContact);
        txtcourse = findViewById(R.id.txtContact);*/
        mainLinear = findViewById(R.id.mainLinear);
    }
}