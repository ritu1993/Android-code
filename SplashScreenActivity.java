package com.example.sampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.sampleapp.model.Product;
import com.example.sampleapp.model.ProductItem;
import com.example.sampleapp.utils.Constants;
import com.example.sampleapp.utils.DatabaseHandler;
import com.example.sampleapp.utils.Utils;

public class SplashScreenActivity extends AppCompatActivity {

    Handler handler;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        db = new DatabaseHandler(this);
        launchNewActivity();
    }

    private void launchNewActivity() {
        String firstLaunch = Utils.getFirstLaunch(SplashScreenActivity.this);
        if (firstLaunch!=null && firstLaunch!="" && !firstLaunch.isEmpty()) {
            if (firstLaunch.equals("true")) {
                Log.i("here","here");
            }
        } else {
            addProducts();
            addProductItem();
            Utils.updateFirstLaunch(SplashScreenActivity.this, "true");
        }
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreenActivity.this,MainActivity.class);
                startActivity(intent);
            }
        },3000);

    }

    private void addProductItem() {
        db.addProductItem(new ProductItem("Laser Forms", "imageUrl1", "Available in 1, 2 or 3 copies","100 caD",1));
        db.addProductItem(new ProductItem("Invoices", "imageUrl2", "Our carbonless Manual Invoices are pre-lined with pre-printed headings making them well-organized and easy to read... features your customers are sure to appreciate! Available in 2, 3 or 4 copies","150 caD",1));
        db.addProductItem(new ProductItem("Purchase Order", "imageUrl3", "Available in 3 copies","70 caD",1));
        db.addProductItem(new ProductItem("Shipping Products", "imageUrl4", "Default number will begin at 1001 unless otherwise specified.","10 caD",1));
        
        db.addProductItem(new ProductItem("High Security Cheques", "imageUrl1", "Our High Security Cheques incorporate features to provide a high level of protection against fraudulent activity! All orders go through proprietary screening to prevent unauthorized orders.","300 caD",2));
        db.addProductItem(new ProductItem("Laser Cheques", "imageUrl2", "You need protection in this fraud-fraught world! We offer Security Features, a combination of effective and cost efficient protective measures that will help keep you covered. And, they're on every cheque you order!","110 $",2));
        db.addProductItem(new ProductItem("Manual Cheques", "imageUrl3", "You need protection in this fraud-fraught world! We offer Security Features, a combination of effective and cost efficient protective measures that will help keep you covered. And, they're on every cheque you order!","120 caD",2));
        db.addProductItem(new ProductItem("Personal cheques", "imageUrl4", "Full-featured personal cheques are the perfect size. Personal size, easy to carry in your purse or pocket. Great compliment to computer cheques when you’re on the move. All orders go through proprietary screening to prevent unauthorized orders, and they have tamper-evident packaging. Available in singles and duplicates. includes handy cheque registers to track debits and credits!","30 $",2));

        db.addProductItem(new ProductItem("cheque envelope\n", "imageUrl1", "Confidential Envelopes feature security blockout to prevent see-through","30 caD",3));
        db.addProductItem(new ProductItem("Self Inking Endorsement Stamp", "imageUrl1","Make deposits faster, easier and more accurately","12.27", 1));
        db.addProductItem(new ProductItem("Compact Invoice", "imageUrl2","Make deposits faster, easier and more accurately","1227", 2));
        db.addProductItem(new ProductItem("Self Inking Endorsement Stamp", "imageUrl3","Make deposits faster, easier and more accurately","12.27", 3));
        db.addProductItem(new ProductItem("Solid Tissue Paper, French Vanilla, 20", "imageUrl4","Machine glaze finish","178", 1));
        db.addProductItem(new ProductItem("Solid Tissue Paper, Light Gray, ", "imageUrl5","Solid Tissue Paper","97.02", 2));
        db.addProductItem(new ProductItem("Solid Tissue Paper, Cool Gray", "imageUrl6","Metallic Tissue Paper","103.1", 3));
        db.addProductItem(new ProductItem("Solid Tissue Paper, Midnight Blue", "imageUrl7","Make deposits faster, easier and more accurately","323", 1));
        db.addProductItem(new ProductItem("Solid Tissue Paper, French Vanilla, 20", "imageUrl8","Machine glaze finish","1125", 4));
        db.addProductItem(new ProductItem("Solid Tissue Paper, Light Gray, ", "imageUrl9","Solid Tissue Paper","225", 5));
        db.addProductItem(new ProductItem("Solid Tissue Paper, Cool Gray", "imageUrl1","Metallic Tissue Paper","112", 6));
        db.addProductItem(new ProductItem("Solid Tissue Paper, French Vanilla, 20", "imageUrl2","Machine glaze finish","112", 7));
        db.addProductItem(new ProductItem("Solid Tissue Paper, Light Gray, ", "imageUrl3","Solid Tissue Paper","556", 8));
        db.addProductItem(new ProductItem("Solid Tissue Paper, Cool Gray", "imageUrl4","Metallic Tissue Paper","223", 7));
        db.addProductItem(new ProductItem("Solid Tissue Paper, Midnight Blue", "imageUrl5","Make deposits faster, easier and more accurately","452", 4));
        db.addProductItem(new ProductItem("Self Inking Endorsement Stamp", "imageUrl6","Make deposits faster, easier and more accurately","432", 5));
        db.addProductItem(new ProductItem("Compact Invoice", "imageUrl7","Make deposits faster, easier and more accurately","632", 6));
        db.addProductItem(new ProductItem("Self Inking Endorsement Stamp", "imageUrl8","Make deposits faster, easier and more accurately","123", 7));
        db.addProductItem(new ProductItem("Solid Tissue Paper, French Vanilla, 20", "imageUrl9","Machine glaze finish","324", 6));
        db.addProductItem(new ProductItem("Solid Tissue Paper, Light Gray, ", "imageUrl1","Solid Tissue Paper","221", 5));
        db.addProductItem(new ProductItem("Solid Tissue Paper, Cool Gray", "imageUrl2","Metallic Tissue Paper","156", 4));
        db.addProductItem(new ProductItem("Solid Tissue Paper, Midnight Blue", "imageUrl3","Make deposits faster, easier and more accurately","467", 6));
        db.addProductItem(new ProductItem("Solid Tissue Paper, French Vanilla, 20", "imageUrl4","Machine glaze finish","325", 7));
        db.addProductItem(new ProductItem("Solid Tissue Paper, Light Gray, ", "imageUrl5","Solid Tissue Paper","1235", 8));
        db.addProductItem(new ProductItem("Solid Tissue Paper, Cool Gray", "imageUrl6","Metallic Tissue Paper","2345", 2));
        db.addProductItem(new ProductItem("Solid Tissue Paper, French Vanilla, 20", "imageUrl7","Machine glaze finish","895", 5));
        db.addProductItem(new ProductItem("Solid Tissue Paper, Light Gray, ", "imageUrl8","Solid Tissue Paper","132", 4));
        db.addProductItem(new ProductItem("Solid Tissue Paper, Cool Gray", "imageUrl9","Metallic Tissue Paper","153", 7));
        db.addProductItem(new ProductItem("Solid Tissue Paper, Midnight Blue", "imageUrl1","Make deposits faster, easier and more accurately","170", 6));
        db.addProductItem(new ProductItem("Self Inking Endorsement Stamp", "imageUrl2","Make deposits faster, easier and more accurately","135", 1));

    }

    private void addProducts() {
        db.addProduct(new Product( "Business Cheques"));
        db.addProduct(new Product( "Banking Products"));
        db.addProduct(new Product( "Business Forms"));
        db.addProduct(new Product( "Custom Printing"));
        db.addProduct(new Product( "Logo Design"));
        db.addProduct(new Product( "Marketing Materials"));
        db.addProduct(new Product( "Office Supplies"));
        db.addProduct(new Product( "Signature Packaging"));
    }
}