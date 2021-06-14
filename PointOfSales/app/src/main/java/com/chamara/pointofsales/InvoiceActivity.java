package com.chamara.pointofsales;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InvoiceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Double amtI = Double.valueOf(0.0d);
    Button btnAddProduct;
    Button btnPrint;
    double grandtotal = 0.0d;
    TextView grndTot;
    public ArrayList<String> products;
    public JSONArray result;
    Spinner spinner1;
    int spinnerCount = 0;
    public String spinnerselecteditem;
    EditText t2;
    EditText t3;
    EditText t4;
    public String twamount;
    EditText txtInvDate;
    EditText txtInvDueDate;
    EditText txtInvNo;
    String url1;
    String url2;
    public InvoiceActivity() {
        String str = "";
        this.spinnerselecteditem = str;
        this.twamount = str;
        this.url1 = "https://pointofsaleschamara.000webhostapp.com/invoice.php";
        this.url2 = "https://pointofsaleschamara.000webhostapp.com/updateinvoice.php";
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        defineComponents();
        getInvoiceData();
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InvoiceActivity.this.products.clear();
                InvoiceActivity.this.addRow();
                InvoiceActivity.this.twamount = "";
            }
        });
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InvoiceActivity.this.updateInvoiceTable();
            }
        });
    }

    private void updateInvoiceTable() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(InvoiceActivity.this, response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InvoiceActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            //@org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap();
                parms.put("amt", Double.toString(grandtotal));
                return parms;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void updateStockTable() {
        String url = "https://pointofsaleschamara.000webhostapp.com/updatestock.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(InvoiceActivity.this, response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InvoiceActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            //@org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap();
                parms.put("name", spinnerselecteditem);
                parms.put("amount", twamount);
                return parms;
            }
        };
        requestQueue.add(stringRequest);

    }

    public void addRow() {
        TableLayout tbl = (TableLayout) findViewById(R.id.tableLayout6);
        TableRow tr = new TableRow(this);
        this.spinner1 = new Spinner(this);
        this.spinner1.setOnItemSelectedListener(this);
        try {
            getData();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), 0).show();
        }
        this.t2 = new EditText(this);
        String str = "#e2f4e6";
        this.t2.setTextColor(Color.parseColor(str));
        this.t2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        this.t2.setInputType(2);
        this.t3 = new EditText(this);
        this.t3.setTextColor(Color.parseColor(str));
        this.t3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        this.t4 = new EditText(this);
        this.t4.setTextColor(Color.parseColor(str));
        this.t4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        this.t4.setInputType(2);
        tbl.addView(tr);
        tr.addView(this.spinner1);
        tr.addView(this.t2);
        tr.addView(this.t3);
        tr.addView(this.t4);
        try {
            this.t2.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String amt = s.toString();
                    String up = InvoiceActivity.this.t3.getText().toString();
                    InvoiceActivity.this.amtI = Double.valueOf(Double.parseDouble(amt));
                    Double total = Double.valueOf(InvoiceActivity.this.amtI.doubleValue() * Double.valueOf(Double.parseDouble(up)).doubleValue());
                    InvoiceActivity.this.t4.setText(Double.toString(total.doubleValue()));
                    InvoiceActivity invoicingActivity = InvoiceActivity.this;
                    invoicingActivity.grandtotal += total.doubleValue();
                    InvoiceActivity.this.grndTot.setText(Double.toString(InvoiceActivity.this.grandtotal));
                }

                @Override
                public void afterTextChanged(Editable s) {
                    StringBuilder stringBuilder = new StringBuilder();
                    InvoiceActivity invoicingActivity = InvoiceActivity.this;
                    stringBuilder.append(invoicingActivity.twamount);
                    stringBuilder.append(s.toString());
                    invoicingActivity.twamount = stringBuilder.toString();
                    InvoiceActivity.this.updateStockTable();
                }
            });
        } catch (Exception e2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(e2);
            Toast.makeText(this, stringBuilder.toString(), 0).show();
        }
    }


    private void getInvoiceData() {
        Volley.newRequestQueue(this).add(new JsonObjectRequest(0, this.url1, null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("invoice");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject invoice = jsonArray.getJSONObject(i);
                        String invno = invoice.getString("inv_no");
                        String invdate = invoice.getString("inv_date");
                        String invduedate = invoice.getString("due_date");
                        InvoiceActivity.this.txtInvNo.setText(invno);
                        InvoiceActivity.this.txtInvDate.setText(invdate);
                        InvoiceActivity.this.txtInvDueDate.setText(invduedate);
                    }
                } catch (Exception e) {
                    InvoiceActivity invoicingActivity = InvoiceActivity.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Exeption : ");
                    stringBuilder.append(e);
                    Toast.makeText(invoicingActivity, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                InvoiceActivity invoicingActivity = InvoiceActivity.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(error);
                Toast.makeText(invoicingActivity, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }
        }));
    }
    private void getData() {
        this.spinnerCount++;
        Volley.newRequestQueue(this).add(new StringRequest("https://pointofsaleschamara.000webhostapp.com/stock.php", new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject j = new JSONObject(response);
                    InvoiceActivity.this.result = j.getJSONArray("products");
                    InvoiceActivity.this.getProducts(InvoiceActivity.this.result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        }));
    }
    @SuppressLint("ResourceType")
    private void getProducts(JSONArray j) {
        for (int i = 0; i < j.length(); i++) {
            try {
                this.products.add(j.getJSONObject(i).getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.spinner1.setAdapter(new ArrayAdapter(this, 17367049, this.products));
    }
    private void defineComponents() {
        this.grndTot = (TextView) findViewById(R.id.txtGrandTotal);
        this.btnAddProduct = (Button) findViewById(R.id.btnAddProduct);
        this.btnPrint = (Button) findViewById(R.id.btnPrint);
        this.txtInvNo = (EditText) findViewById(R.id.editText4);
        this.txtInvDate = (EditText) findViewById(R.id.editText5);
        this.txtInvDueDate = (EditText) findViewById(R.id.editText6);
        this.products = new ArrayList();
    }
    private String getUnitPrice(int position) {
        String course = "";
        try {
            return this.result.getJSONObject(position).getString("unitprice");
        } catch (JSONException e) {
            e.printStackTrace();
            return course;
        }
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        this.t3.setText(getUnitPrice(position));
        this.spinnerselecteditem = this.spinner1.getSelectedItem().toString();
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
        String str = "";
        this.t2.setText(str);
        this.t3.setText(str);
        this.t4.setText(str);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        
    }
}