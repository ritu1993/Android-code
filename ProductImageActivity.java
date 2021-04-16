package com.example.sampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductImageActivity extends AppCompatActivity {

    ImageView imageProdView;
    TextView prodDesc, prodPrice;
    String item_name, description, product_code, price, product_id, path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_image);
        imageProdView = findViewById(R.id.imageProdView);
        item_name = getIntent().getStringExtra("item_name");
        description = getIntent().getStringExtra("description");
        product_code = getIntent().getStringExtra("product_code");
        price = getIntent().getStringExtra("price");
        product_id = getIntent().getStringExtra("product_id");
        path = getIntent().getStringExtra("image_path");
        prodDesc = findViewById(R.id.prodDesc);
        prodPrice =findViewById(R.id.prodPrice);
        if (path.equals("imageUrl1")) {
            imageProdView.setImageResource(R.drawable.bank);
        } else if (path.equals("imageUrl2")) {
            imageProdView.setImageResource(R.drawable.checksvoucher);
        } else if (path.equals("imageUrl3")) {
            imageProdView.setImageResource(R.drawable.depositslips);
        } else if (path.equals("imageUrl4")) {
            imageProdView.setImageResource(R.drawable.giftcertificate);
        }else if (path.equals("imageUrl5")) {
            imageProdView.setImageResource(R.drawable.highsecurity);
        }else if (path.equals("imageUrl6")) {
            imageProdView.setImageResource(R.drawable.lasercheques);
        }else if (path.equals("imageUrl7")) {
            imageProdView.setImageResource(R.drawable.manualcheques);
        }else if (path.equals("imageUrl8")) {
            imageProdView.setImageResource(R.drawable.personalcheques);
        } else {
            imageProdView.setImageResource(R.drawable.purchaseorder);
        }
        prodDesc.setText(description);
        prodPrice.setText(price);
//        imageProdView.setOnClickListener(v-> {productDetailActivity();});
    }

    private void productDetailActivity() {
        Intent intent = new Intent(ProductImageActivity.this, ProductDetailActivity.class);
        intent.putExtra("item_name", item_name);
        intent.putExtra("description", description);
        intent.putExtra("product_code", product_code);
        intent.putExtra("price", price);
        intent.putExtra("product_id", product_id);
        intent.putExtra("image_path", path);
        startActivity(intent);
    }
}