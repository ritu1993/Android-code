package com.example.sampleapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.sampleapp.adaptar.ProductAdaptar;
import com.example.sampleapp.model.Product;
import com.example.sampleapp.utils.DatabaseHandler;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Product> productList;
    DatabaseHandler db;
    RecyclerView productView;
    ProductAdaptar productAdaptar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHandler(this);
        productList = db.getAllProducts();
        productView = findViewById(R.id.productView);
        productAdaptar();
    }

    private void productAdaptar() {

        productAdaptar = new ProductAdaptar(productList, MainActivity.this, new ProductAdaptar.OrderCountListener() {
            @Override
            public void orderType(View v, int position) {
                Intent intent = new Intent(MainActivity.this, ProductsSubActivity.class);
                intent.putExtra("product_id", String.valueOf(productList.get(position).getProduct_id()));
                intent.putExtra("product_name", productList.get(position).getProduct_name());
                startActivity(intent);
            }
        });
        productView.setHasFixedSize(true);
        productView.setLayoutManager(new LinearLayoutManager(this));
        productView.setAdapter(productAdaptar);
    }
}