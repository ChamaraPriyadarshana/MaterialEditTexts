package com.chamara.pointofsales;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity {
    String amount;
    Button btnAddProduct;
    String name;
    String price;
    String supplier;
    EditText txtAmount;
    EditText txtName;
    EditText txtPrice;
    EditText txtSupplier;
    final String url = "https://pointofsaleschamara.000webhostapp.com/addstock.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        defineComponents();
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTextes();
                if (name.equals("") && price.equals("") && amount.equals("") && supplier.equals("")){
                    Toast.makeText(AddProductActivity.this, "Required Fields!", Toast.LENGTH_SHORT).show();
                }else{
                    insertRequest();
                }

                clearTextBoxes();
            }
        });
    }

    private void getTextes() {
        name = txtName.getText().toString();
        price = txtPrice.getText().toString();
        amount = txtAmount.getText().toString();
        supplier = txtSupplier.getText().toString();
    }

    private void defineComponents() {
        txtName = (EditText) findViewById(R.id.txtName);
        txtPrice = (EditText) findViewById(R.id.txtPrice);
        txtAmount = (EditText) findViewById(R.id.txtAmount);
        txtSupplier = (EditText) findViewById(R.id.txtSupplier);
        btnAddProduct = findViewById(R.id.btnAddProduct);
    }

    private void insertRequest() {
        Volley.newRequestQueue(this).add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            public void onResponse(String response) {
                AddProductActivity addStockActivity = AddProductActivity.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(response);
                Toast.makeText(addStockActivity, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                clearTextBoxes();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AddProductActivity addStockActivity = AddProductActivity.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(error);
                Toast.makeText(addStockActivity, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }

        }) {

            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();
                map.put("name", AddProductActivity.this.name);
                map.put("price", AddProductActivity.this.price);
                map.put("amount", AddProductActivity.this.amount);
                map.put("supplier", AddProductActivity.this.supplier);
                return map;
            }
        });
    }

    private void clearTextBoxes() {
        txtName.setText("");
        txtPrice.setText("");
        txtAmount.setText("");
        txtSupplier.setText("");
    }

}