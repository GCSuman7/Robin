package com.example.robin;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import java.io.File;
import java.util.Calendar;

public class Register extends AppCompatActivity {
    EditText Fname,Lname,email,phone,password;
    TextView tvDOB;
    private ImageView imgProfile;
    String imagePath;
    Button btn_reg;
    Button btn_exit;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Fname=(EditText)findViewById(R.id.Fname);
        Lname=(EditText)findViewById(R.id.Lname);
        email=(EditText)findViewById(R.id.email);
        phone=(EditText)findViewById(R.id.phone);
        password=(EditText)findViewById(R.id.password);
        btn_reg=(Button)findViewById(R.id.btn_reg);
        btn_exit=(Button)findViewById(R.id.btn_exit);
        builder = new AlertDialog.Builder(this);
        tvDOB = findViewById(R.id.tvDOB);
        imgProfile = findViewById(R.id.imgProfile);

        tvDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCalendar();
            }
        });

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();

                // TODO Auto-generated method stub
                if(Fname.getText().toString().trim().length()==0){
                    Fname.setError("First Name is not entered");
                    Fname.requestFocus();
                }
                if(Lname.getText().toString().trim().length()==0){
                    Lname.setError("Last Name is not entered");
                    Lname.requestFocus();
                }

                if(email.getText().toString().trim().length()==0){
                    email.setError("Email is not entered");
                    email.requestFocus();
                }
                if(phone.getText().toString().trim().length()==0){
                    phone.setError("Phone No is not entered");
                    phone.requestFocus();
                }
                if(password.getText().toString().trim().length()==0){
                    password.setError("Password is not entered");
                    password.requestFocus();
                }
                else{
                    Intent it=new Intent(getApplicationContext(), home1.class);
                    startActivity(it);
                }
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setIcon(R.drawable.ic_exit_to_app_black_24dp);
                builder.setMessage("Do you want to close this Application?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                Toast.makeText(getApplicationContext(),"You clicked Yes",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),"You clicked No",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.setTitle("My Title");
                alert.show();
            }
        });
    }


    private void loadCalendar(){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = "Month/Day/Year :" + month + "/" + dayOfMonth + "/" + year;
                tvDOB.setText(date);
            }
        },year,month,day);
        datePickerDialog.show();
    }

    private void BrowseImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (data == null){
                Toast.makeText(this, "Please Select an image", Toast.LENGTH_SHORT).show();
            }
        }
        Uri uri = data.getData();
        imagePath = getRealPathFromUri(uri);
        previewImage(imagePath);
    }
    private String getRealPathFromUri(Uri uri){
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection, null,
                null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }
    private void previewImage(String imagePath){

        File imgFile = new File(imagePath);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imgProfile.setImageBitmap(myBitmap);
        }
    }

    }

