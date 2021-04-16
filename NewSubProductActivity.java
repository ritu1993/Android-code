package com.example.sampleapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sampleapp.model.ProductItem;
import com.example.sampleapp.utils.CheckPermissions;
import com.example.sampleapp.utils.DatabaseHandler;
import com.example.sampleapp.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.Manifest.permission.CAMERA;

public class NewSubProductActivity extends AppCompatActivity {
    LinearLayout llUploadPresc;
    File photoFile = null;
    ImageView imageView;
    Button back, submit, cancel;
    EditText prodPriceEd, prodDescEd;
    DatabaseHandler db;
    String productId, productName;
    Uri selectedImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sub_product);
        db = new DatabaseHandler(this);
        imageView = findViewById(R.id.imageView);
        llUploadPresc = findViewById(R.id.llUploadPresc);
        llUploadPresc.setOnClickListener(v->{ selectImage(); });
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> {goBack();});
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(v -> {cancel();});
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(v -> {submit();});
        prodDescEd = findViewById(R.id.prodDescEd);
        prodPriceEd = findViewById(R.id.prodPriceEd);
        productId = getIntent().getStringExtra("product_id");
        productName = getIntent().getStringExtra("product_name");
    }

    private void submit() {
        if (photoFile() && validate(prodDescEd, "photo description is required")
                && validate(prodPriceEd, "photo description is required")) {
            String item_name=productName;
            String image_url=selectedImage.toString();
            String description=prodDescEd.getText().toString();
            String price = prodPriceEd.getText().toString();
            int sub_product_id=Integer.valueOf(productId);
            db.addProductItem(new ProductItem(item_name, image_url, description,  price, sub_product_id));
            Log.i("data","data inserted successfully");
            Intent intent = new Intent(NewSubProductActivity.this, ProductsSubActivity.class);
            intent.putExtra("product_id", productId);
            intent.putExtra("product_name", productName);
            startActivity(intent);
        }
    }

    private boolean photoFile() {
        if (photoFile==null) {
            return false;
        }
        return true;
    }

    private boolean validate(EditText fieldName, String fieldValue) {

        if (fieldName.getText().toString()==null || fieldName.getText().toString()==""
                || fieldName.getText().toString().isEmpty()) {
            fieldName.setError(fieldValue);
            Toast.makeText(this, fieldValue, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void cancel() {
        photoFile=null;
        selectedImage = null;
        imageView.setImageBitmap(null);
        prodPriceEd.setText("");
        prodDescEd.setText("");
    }

    private void goBack() {
        super.onBackPressed();
        this.finish();
    }


    private void selectImage() {
        final CharSequence[] options = {"Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(NewSubProductActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                try {
                    boolean resultCamera = CheckPermissions.checkCameraPermission(NewSubProductActivity.this);
                    boolean resultStorage = CheckPermissions.checkReadExternalStoragePermission(NewSubProductActivity.this);
                    boolean resultWriteStorage = CheckPermissions.checkWriteExternalStoragePermission(NewSubProductActivity.this);

                    if (options[item].equals("Take Photo")) {
                        Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePicture, 0);//zero can be replaced with any action code (called requestCode)

                    } else if (options[item].equals("Choose from Gallery")) {
                        //   if (resultStorage) {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 1);

                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.show();
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
                    photoFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                    FileOutputStream fo;
                    try {
                        photoFile.createNewFile();
                        if (photo != null) {
                            //   ivPrescription.setVisibility(View.VISIBLE);
                            imageView.setImageBitmap(photo);
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
                    selectedImage = data.getData();
                    photoFile = new File(selectedImage.getPath());

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
                            imageView.setImageBitmap(bitmap);
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


}