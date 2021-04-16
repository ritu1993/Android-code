package com.example.sampleapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.sampleapp.adaptar.ProductAdaptar;
import com.example.sampleapp.adaptar.ProductItemAdaptar;
import com.example.sampleapp.model.Product;
import com.example.sampleapp.model.ProductItem;
import com.example.sampleapp.utils.DatabaseHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProductsSubActivity extends AppCompatActivity {

    String productId, productName;
 //   Button addSubMenu;
    DatabaseHandler db;
    List<ProductItem> productList;
    RecyclerView productItemView;
    ProductItemAdaptar productItemAdaptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_sub);
        db = new DatabaseHandler(this);
        productId = getIntent().getStringExtra("product_id");
        productName = getIntent().getStringExtra("product_name");
        productItemView = findViewById(R.id.productItemView);
 //       addSubMenu = findViewById(R.id.addSubMenu);
 //       addSubMenu.setOnClickListener(v->{addNewMenu();});
        productList=db.getAllProductItem(Integer.valueOf(productId));
        productItemAdaptar();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    // ivPrescription.setVisibility(View.VISIBLE);
                    // ivPrescription.setImageBitmap(photo);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    File photoFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                    FileOutputStream fo;
                    try {
                        photoFile.createNewFile();
                        if (photo != null) {
                            //   ivPrescription.setVisibility(View.VISIBLE);
                      //      imageView.setImageBitmap(photo);
                        }
                        fo = new FileOutputStream(photoFile);
                        fo.write(bytes.toByteArray());
                        fo.close();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                   Uri selectedImage = data.getData();
                   File  photoFile = new File(selectedImage.getPath());

                    try {
                        Uri imageUri = data.getData();
                        String path = getRealPathFromURI(imageUri);
                        //   file = new File(getPathFromURI(imageUri));
                        //Create a file object using file path
                        photoFile = new File(path);
                        // Create a request body with file and image media type
                        InputStream imageStream = this.getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                        if (bitmap != null) {
                            //   ivPrescription.setVisibility(View.VISIBLE);
                        //    imageView.setImageBitmap(bitmap);
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = this.managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    private void productItemAdaptar() {
        productItemAdaptar = new ProductItemAdaptar(productList, ProductsSubActivity.this, new ProductItemAdaptar.OrderCountListener() {
            @Override
            public void orderType(View v, int position) {
//                String path = productList.get(position).getImage_url();
//                if (path.equals("imageUrl1")) {
//                    holder.imageView.setImageResource(R.drawable.bank);
//                } else if (path.equals("imageUrl2")) {
//                    holder.imageView.setImageResource(R.drawable.checksvoucher);
//                } else if (path.equals("imageUrl3")) {
//                    holder.imageView.setImageResource(R.drawable.depositslips);
//                } else if (path.equals("imageUrl4")) {
//                    holder.imageView.setImageResource(R.drawable.giftcertificate);
//                }else if (path.equals("imageUrl5")) {
//                    holder.imageView.setImageResource(R.drawable.highsecurity);
//                }else if (path.equals("imageUrl6")) {
//                    holder.imageView.setImageResource(R.drawable.lasercheques);
//                }else if (path.equals("imageUrl7")) {
//                    holder.imageView.setImageResource(R.drawable.manualcheques);
//                }else if (path.equals("imageUrl8")) {
//                    holder.imageView.setImageResource(R.drawable.personalcheques);
//                } else {
//                    holder.imageView.setImageResource(R.drawable.purchaseorder);
//                }

                Intent intent = new Intent(ProductsSubActivity.this, ProductImageActivity.class);
                intent.putExtra("item_name", productList.get(position).getItem_name());
                intent.putExtra("description", productList.get(position).getDescription());
                intent.putExtra("product_code", String.valueOf(productList.get(position).getSub_product_id()));
                intent.putExtra("price", productList.get(position).getPrice());
                intent.putExtra("product_id", String.valueOf(productList.get(position).getProduct_item_id()));
                intent.putExtra("image_path", productList.get(position).getImage_url());
                startActivity(intent);
            }
        });
        productItemView.setHasFixedSize(true);
        productItemView.setLayoutManager(new LinearLayoutManager(this));
        productItemView.setAdapter(productItemAdaptar);

    }

    private void addNewMenu() {
        Intent intent = new Intent(ProductsSubActivity.this, NewSubProductActivity.class);
        intent.putExtra("product_id", productId);
        intent.putExtra("product_name", productName);
        startActivity(intent);
    }
}