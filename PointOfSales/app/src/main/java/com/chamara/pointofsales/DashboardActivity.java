package com.chamara.pointofsales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void invoiceIntent(View view) {
        Intent intent = new Intent(DashboardActivity.this,InvoiceActivity.class);
        startActivity(intent);
    }

    public void storeIntent(View view) {
        Intent intent = new Intent(DashboardActivity.this,StoreActivity.class);
        startActivity(intent);
    }

    public void addProductIntent(View view) {
        Intent intent = new Intent(DashboardActivity.this,AddProductActivity.class);
        startActivity(intent);
    }

    public void suppliersIntent(View view) {
        Intent intent = new Intent(DashboardActivity.this,SuppliersActivity.class);
        startActivity(intent);
    }
}